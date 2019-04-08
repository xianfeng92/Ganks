package com.example.ganks.mvp.ui.fragment.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.ganks.DataConvert.HomeDataConvert;
import com.example.ganks.R;
import com.example.ganks.base.BaseApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.internal.di.components.DaggerCategoryComponent;
import com.example.ganks.internal.di.modules.CategoryModule;
import com.example.ganks.mvp.presenter.CategoryPresenter;
import com.example.ganks.mvp.ui.adapter.MultipleRecyclerAdapter;
import com.example.ganks.mvp.view.CategoryView;
import com.orhanobut.logger.Logger;
import com.xforg.gank_core.net.RestClient;
import com.xforg.gank_core.net.RestCreator;
import com.xforg.gank_core.net.RestService;
import com.xforg.gank_core.net.callbacks.IError;
import com.xforg.gank_core.net.callbacks.ISuccess;
import com.xforg.gank_core.recycler.MultipleFields;
import com.xforg.gank_core.recycler.MultipleItemEntity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class CategoryArticleFragment extends BaseDelegate<CategoryPresenter> implements SwipeRefreshLayout.OnRefreshListener, CategoryView {

    private static FragmentManager fManager;
    private MultipleRecyclerAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<MultipleItemEntity> datas = new ArrayList<>();
    private RestService articleService;
    private int page = 1;
    int pageSize = 10;
    private int lastVisibleItem;

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public String type;

    public static CategoryArticleFragment newInstance(String type, FragmentManager fragmentManager){
        fManager = fragmentManager;
        CategoryArticleFragment categoryArticleFragment = new CategoryArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        categoryArticleFragment.setArguments(bundle);
        return categoryArticleFragment;
    }


    @Override
    public Object setLayout() {
        return R.layout.layout_refresh_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        articleService = RestCreator.getRestService();
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initializeInjector();
        type = getArguments().getString("type");
    }

    @Override
    public void onRefresh() {
        Logger.d("onRefresh");
        page++;
        getDatas();
    }

    @Override
    public void initData() {
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MultipleRecyclerAdapter(datas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItemEntity bean = (MultipleItemEntity) datas.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("URL", (String) bean.getField(MultipleFields.URL));
                FragmentTransaction tx = fManager.beginTransaction();
                ArticleContentFragment articleContentFragment = ArticleContentFragment.newInstance();
                articleContentFragment.setArguments(bundle);
                //  这里是父Fragment启动(HomeFragment),要注意 栈层级
                getParentDelegate().start(articleContentFragment);
            }
        });
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 0：当前屏幕停止滚动；
                // 1：屏幕在滚动且用户仍在触碰或手指还在屏幕上
                // 2：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +1 >= layoutManager.getItemCount()) {
                    page++;
                    getDatas();
                }
            }
        });
        getDatas();
    }


    /***************************************************private Methods***********************************/
    private void getDatas() {
        final SkeletonScreen skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(120)
                .count(10)
                .load(R.layout.item_skeleton_news)
                .show(); //default count is 10
        RestClient.builder()
                .url("https://gank.io/api/data")
                .addParams(type)
                .addParams(pageSize)
                .addParams(page)
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Logger.e("onError %s",msg);
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // 数据解析
                        final HomeDataConvert homeDataConvert = new HomeDataConvert();
                        homeDataConvert.serJsonData(response);
                        final ArrayList<MultipleItemEntity> list = homeDataConvert.convert();
                        if (list != null) {
                            datas.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            skeletonScreen.hide();
                        } else {
                            throw new RuntimeException("Can not get Data from Service");
                        }
                    }
                })
                .build()
                .get();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initializeInjector(){
        DaggerCategoryComponent.builder()
                .applicationComponent(BaseApplication.getApplicationComponent())
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }
}
