package com.example.ganks.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ganks.R;
import com.example.ganks.adapter.ArticleAdapter;
import com.example.ganks.net.GankApi;
import com.example.ganks.bean.GankEntity;
import com.example.ganks.net.RestService;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class CategoryArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.ItemClickListener {

    private static FragmentManager fManager;
    private ArticleAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<GankEntity.ResultsBean> datas = new ArrayList<>();
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_refresh_list,container,false);
        articleService = GankApi.buildServiceForGank();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        type = getArguments().getString("type");
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getDatas();
    }

    @Override
    public void onRefresh() {
        page++;
        getDatas();
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(getContext(),"OnItemClick"+postion,Toast.LENGTH_SHORT).show();
        GankEntity.ResultsBean bean = (GankEntity.ResultsBean) datas.get(postion);
        Bundle bundle = new Bundle();
        bundle.putString("URL",bean.url);
        FragmentTransaction tx = fManager.beginTransaction();
        ArticleContentFragment articleContentFragment = ArticleContentFragment.newInstance();
        articleContentFragment.setArguments(bundle);
        //加上Fragment替换动画
        tx.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        tx.replace(R.id.ly_content, articleContentFragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    private void getDatas(){
        articleService.gank(type,pageSize,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GankEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GankEntity gankEntity) {
                for (GankEntity.ResultsBean bean:gankEntity.results){
                    datas.add(bean);
                }
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleAdapter(getContext(),datas);
        mAdapter.setItemClickListener(this);
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

    @Override
    public void setData(Object data) {

    }
}
