package com.example.ganks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ganks.R;
import com.example.ganks.adapter.TanTanAdapter;
import com.example.ganks.entity.Meizi;
import com.example.ganks.net.RestCreator;
import com.example.ganks.net.RestService;
import com.example.tantancardswipe.CardConfig;
import com.example.tantancardswipe.OnSwipeListener;
import com.example.tantancardswipe.CardItemTouchHelperCallback;
import com.example.tantancardswipe.CardLayoutManager;

import java.util.ArrayList;
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

    private RestService meiziService;
    private TanTanAdapter tanTanAdapter;
    private CardItemTouchHelperCallback cardItemTouchHelperCallback;
    private CardLayoutManager cardLayoutManager;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> urls = new ArrayList<>();
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
        meiziService = RestCreator.getRestService();
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
                        urls.add(resultsBean.url);
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
        tanTanAdapter = new TanTanAdapter(getContext(),urls);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tanTanAdapter);
        cardItemTouchHelperCallback = new CardItemTouchHelperCallback(tanTanAdapter,urls);
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
}