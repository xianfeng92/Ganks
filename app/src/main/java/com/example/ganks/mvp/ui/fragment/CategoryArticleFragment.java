package com.example.ganks.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.domain.MeiziList;
import com.example.domain.repository.RepositoryManager;
import com.example.ganks.R;
import com.example.ganks.base.GanksApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.mvp.presenter.CategoryPresenter;
import com.example.ganks.mvp.ui.adapter.LineAdapter;
import com.example.ganks.mvp.view.CategoryView;
import com.orhanobut.logger.Logger;
import com.xforg.gank_core.utils.RxUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class CategoryArticleFragment extends BaseDelegate<CategoryPresenter> implements SwipeRefreshLayout.OnRefreshListener, CategoryView {

    private LineAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<MeiziList.Meizi> datas = new ArrayList<>();
    private int page = 1;
    private int lastVisibleItem;

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public String type;

    @Inject
    RepositoryManager repositoryManager;

    public static CategoryArticleFragment newInstance(String type, FragmentManager fragmentManager){
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
        mAdapter = new LineAdapter(R.layout.line_meizi_item,datas);
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
        repositoryManager.meiziList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<MeiziList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziList meiziList) {
                            datas.addAll(meiziList.results);
                            mAdapter.notifyDataSetChanged();
                            skeletonScreen.hide();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initializeInjector(){
        GanksApplication.getApplicationComponent().contentActivityComponent().build().categoryComponent().setCateGoryComponentView(this).build().inject(this);
    }
}
