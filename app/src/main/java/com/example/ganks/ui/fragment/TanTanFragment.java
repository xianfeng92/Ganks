package com.example.ganks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ganks.R;
import com.example.ganks.ui.adapter.TanTanAdapter;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import com.xforg.gank_core.entity.Meizi;
import com.xforg.gank_core.net.RestCreator;
import com.example.tantancardswipe.CardConfig;
import com.example.tantancardswipe.OnSwipeListener;
import com.example.tantancardswipe.CardItemTouchHelperCallback;
import com.example.tantancardswipe.CardLayoutManager;
import com.xforg.gank_core.net.rx.RxRestService;
import com.xforg.gank_core.utils.GreenDaoHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class TanTanFragment extends BaseMainFragment {

    private static final String TAG = "TanTanFragment";

    private RxRestService meiziService;
    private TanTanAdapter tanTanAdapter;
    private CardItemTouchHelperCallback cardItemTouchHelperCallback;
    private CardLayoutManager cardLayoutManager;
    private RecyclerView recyclerView;
    private DaoMeiziEntity daoGankEntity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> urls = new ArrayList<>();
    private List<Meizi.ResultsBean> resultsBeanList = new ArrayList<>();
    private int page = 1;


    public static TanTanFragment newInstance(){
        TanTanFragment tanTanFragment = new TanTanFragment();
        return tanTanFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_refresh_list,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        meiziService = RestCreator.getRxRestService();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        getMeizi();
    }

    private void getMeizi(){
        meiziService.getMeizi(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Meizi>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Meizi meizis) {
                for (Meizi.ResultsBean resultsBean:meizis.results){
                    if (!TextUtils.isEmpty(resultsBean.url))
                    resultsBeanList.add(resultsBean);
                }
                tanTanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initData(){
        tanTanAdapter = new TanTanAdapter(R.layout.tantan_item,resultsBeanList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tanTanAdapter);
        cardItemTouchHelperCallback = new CardItemTouchHelperCallback(tanTanAdapter,resultsBeanList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cardItemTouchHelperCallback);
        cardLayoutManager = new CardLayoutManager(recyclerView,itemTouchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        cardItemTouchHelperCallback.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                View toastView = getLayoutInflater().inflate(R.layout.mytoast, null);
                Toast toast = new Toast(getContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                if (direction == CardConfig.SWIPED_LEFT){
                    toastView.setBackgroundResource(R.mipmap.img_dislike);
                }else {
                    toastView.setBackgroundResource(R.mipmap.img_like);
                    addToFavorites(o);
                }
                toast.setView(toastView);
                toast.show();
            }

            @Override
            public void onSwipedClear() {
                getMeizi();
            }
        });
    }

    public void addToFavorites(Object o){
        Meizi.ResultsBean resultsBean = (Meizi.ResultsBean)o;
        DaoMeiziEntity daoMeiziEntity = entityToDao(resultsBean);
        if (!GreenDaoHelper.isDaoContainMeizi(daoMeiziEntity._id)){
            GreenDaoHelper.insert(daoMeiziEntity);
        }else {
            Log.d(TAG, "you already love It!!");
        }
    }

    public void removeByid(Meizi.ResultsBean entity) {
        DaoMeiziEntity daoGankEntity = entityToDao(entity);
        GreenDaoHelper.removeById(daoGankEntity._id);
    }

    private  DaoMeiziEntity entityToDao(Meizi.ResultsBean entity){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        if (daoGankEntity == null){
            daoGankEntity = new DaoMeiziEntity();
        }
        daoGankEntity._id = entity._id;
        daoGankEntity.createdAt = entity.createdAt;
        daoGankEntity.desc = entity.desc;
        daoGankEntity.publishedAt = entity.publishedAt;
        daoGankEntity.source = entity.source;
        daoGankEntity.type = entity.type;
        daoGankEntity.url = entity.url;
        daoGankEntity.used = entity.used;
        daoGankEntity.who = entity.who;
        daoGankEntity.addTime =str;
        return daoGankEntity;
    }
}
