package com.example.ganks.ui.fragment.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ganks.R;
import com.example.ganks.ui.adapter.ArticleAdapter;
import com.example.ganks.ui.fragment.BaseMainFragment;
import com.xforg.gank_core.entity.Meizi;
import com.xforg.gank_core.net.RestCreator;
import com.xforg.gank_core.net.RestService;

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
public class CategoryArticleFragment extends BaseMainFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "CategoryArticleFragment";

    private static FragmentManager fManager;
    private ArticleAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<Meizi.ResultsBean> datas = new ArrayList<>();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_refresh_list,container,false);
        articleService = RestCreator.getRestService();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        type = getArguments().getString("type");
        initData();
        getDatas();
        return view;
    }

    @Override
    public void onRefresh() {
        page++;
        getDatas();
    }

    private void getDatas(){
        articleService.gank(type,pageSize,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Meizi>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Meizi gankEntity) {
                for (Meizi.ResultsBean bean:gankEntity.results){
                    datas.add(bean);
                }
                Log.d(TAG, "onNext: "+datas.size());
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initData() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleAdapter(R.layout.article_item,datas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Meizi.ResultsBean bean = (Meizi.ResultsBean) datas.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("URL",bean.url);
                FragmentTransaction tx = fManager.beginTransaction();
                ArticleContentFragment articleContentFragment = ArticleContentFragment.newInstance();
                articleContentFragment.setArguments(bundle);
                //加上Fragment替换动画
                tx.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
                tx.replace(R.id.fl_container, articleContentFragment);
                tx.addToBackStack(null);
                tx.commit();
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
    }
}
