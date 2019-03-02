package com.example.ganks.ui.fragment;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.ganks.R;
import com.example.ganks.ui.adapter.LineAdapter;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import com.xforg.gank_core.net.RestService;
import com.xforg.gank_core.net.callbacks.IRequest;
import com.xforg.gank_core.net.download.DownloadHandler;
import com.xforg.gank_core.net.rx.RxRestService;
import com.xforg.gank_core.utils.File.FileUtil;
import com.xforg.gank_core.utils.GreenDaoHelper;
import com.xforg.gank_core.utils.ToastUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action1;

/**
 * Created By apple on 2019/2/24
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziFragment extends BaseMainFragment {

    public static final String TAG = "LoveMeiziFragment";

    private Retrofit retrofit;
    private View notDataView;
    public RecyclerView recyclerView;
    public CoordinatorLayout coordinatorLayout;
    public LineAdapter mAdapter;
    public LinearLayoutManager mlayoutManager;
    public List<DaoMeiziEntity> resultsBeanList = new ArrayList<>();
    private Set<String> downLoadUrls = new TreeSet<>();

    public static LoveMeiziFragment newInstance(){
        Bundle args = new Bundle();
        LoveMeiziFragment loveMeiziFragment = new LoveMeiziFragment();
        loveMeiziFragment.setArguments(args);
        return loveMeiziFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi_love,container,false);
        recyclerView = view.findViewById(R.id.meizi_love_recycler);
        coordinatorLayout = view.findViewById(R.id.meizi_line_coordinatorLayout);
        mlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("你没有喜欢的妹子哟");
            }
        });
        initItemDargAndSwipe();
        loadDataByGreenDao();
        RxPermissions.getInstance(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    ToastUtils.showShortToastSafe("WRITE STORAGE Permission 获取成功");
                }
            }
        });
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        loadDataByGreenDao();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            updateAdapter(resultsBeanList);
    }

    private void loadDataByGreenDao(){
        resultsBeanList.clear();
        List<DaoMeiziEntity> list = GreenDaoHelper.getAllMeiziEntity();
        Log.d(TAG, "loadDataByGreenDao: "+list.size());
        resultsBeanList.addAll(list);
        // for test empty view
        // resultsBeanList.clear();
        updateAdapter(resultsBeanList);
    }

    private void updateAdapter(List<DaoMeiziEntity> resultsBeanList){
        if (resultsBeanList.size() == 0){
            mAdapter.setEmptyView(notDataView);
        }
        if (mAdapter == null){
            mAdapter = new LineAdapter(R.layout.line_meizi_item,resultsBeanList);
            recyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setNewData(resultsBeanList);
        }
    }

    private void initItemDargAndSwipe(){
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag start");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag end");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }
        };
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
                GreenDaoHelper.removeById(resultsBeanList.get(pos)._id);
                loadDataByGreenDao();
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                //滑动中，可做一些操作
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.bg_gray));
                canvas.drawText("Don't you love me?", 50, 400, paint);
            }
        };

        mAdapter = new LineAdapter(R.layout.line_meizi_item,resultsBeanList);
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        // 设置item可滑动的方向：
        // ItemTouchHelper.START ------> 左滑
        // ItemTouchHelper.END   ------> 右滑
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START);
        mAdapter.enableSwipeItem();
        //监听item的滑动状况
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);
        // 添加列表加载动画
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置动画的执行
        mAdapter.isFirstOnly(false);
        mAdapter.setOnItemDragListener(listener);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShortToast("此处功能待开发，试试长押可以拖动哦！");
                Log.d(TAG, "onItemClick: "+resultsBeanList.get(position).url);
                String url = resultsBeanList.get(position).url;
                // 采用AsyncTask下载资源
                // 需要指定url 、 request 以及 url
                DownloadHandler.builder().url(subString(url))
                        .dir(Environment.getExternalStorageState()+"/image/")
                        .extension("png")
                        .request(new MyRequest())
                        .build().handleDownLoad();
//                downLoad(url);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    // 保存指定url的图片
    public void downLoad(final String url){
        Log.d(TAG, "downLoad: "+url);
        if (downLoadUrls.contains(url)){
            ToastUtils.showShortToastSafe("已保存！");
            return;
        }
         retrofit = new Retrofit.Builder()
                .baseUrl("https://ws1.sinaimg.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
         retrofit.create(RxRestService.class).downloadWithRxjava(subString(url))
                  //在新线程中实现该方法
                 .subscribeOn(Schedulers.newThread()).subscribe(new Observer<ResponseBody>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onNext(ResponseBody responseBody) {
                 byte[] bys = new byte[0];
                 try {
                     // OkHttp请求回调中responseBody.bytes();只能有效调用一次.
                     // 调用responseBody.bytes();的时候数据流已经关闭了，再次调用就会出现错误提示java.lang.IllegalStateException: closed
                     bys = responseBody.bytes();
                     File file = FileUtil.saveBitmap(BitmapFactory.decodeByteArray(bys,0,bys.length),
                             Environment.getExternalStorageState()+"/image/",80);
                     if (file != null){
                         ToastUtils.showShortToastSafe("保存成功！");
                         downLoadUrls.add(url);
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onError(Throwable e) {
                 Log.d(TAG, "onError: "+e.toString());
             }

             @Override
             public void onComplete() {

             }
         });
    }

    // 提取url中baseUrl之后的字符串
    private String subString(String url){
        return url.substring(23);
    }

    class MyRequest implements IRequest{

        @Override
        public void onRequestStart() {
            Log.d(TAG, "onRequestStart: ");
        }

        @Override
        public void onRequestEnd() {
            Log.d(TAG, "onRequestEnd: ");
        }
    }
}
