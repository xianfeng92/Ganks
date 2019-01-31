package com.example.ganks.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ganks.R;
import com.example.ganks.adapter.ArticleAdapter;
import com.example.ganks.api.Api;
import com.example.ganks.api.service.CommonService;
import com.example.ganks.bean.GankEntity;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.ItemClickListener {

    private static final String TAG = "ArticleFragment";

    private static FragmentManager fManager;
    private ArticleAdapter mAdapter;
    private List<GankEntity.ResultsBean> datas;
    private GankEntity.ResultsBean intentArticle;
    private Retrofit retrofit;
    private CommonService articleService;
    private int page = 1;
    private int pageSize = 10;

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_refresh_list,container,false);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.APP_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        articleService = retrofit.create(CommonService.class);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        init();
        mAdapter.setItemClickListener(this);
        return view;
    }

    public static ArticleFragment newInstance(FragmentManager fragmentManager){
        fManager = fragmentManager;
        ArticleFragment articleFragment = new ArticleFragment();
        return articleFragment;
    }

    private void init(){
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter(getContext(),datas);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        getArticle();
    }

    private void setListener(){

    }

    @Override
    public void onRefresh() {
        page++;
        getArticle();
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(getContext(),"OnItemClick"+postion,Toast.LENGTH_SHORT).show();
        if (fManager == null){
            fManager = getFragmentManager();
        }
        GankEntity.ResultsBean bean = (GankEntity.ResultsBean) datas.get(postion);
        if (intentArticle == null)
            intentArticle = new GankEntity.ResultsBean();
        intentArticle._id = bean._id;
        intentArticle.createdAt = bean.createdAt;
        intentArticle.desc = bean.desc;
        intentArticle.images = bean.images;
        intentArticle.publishedAt = bean.publishedAt;
        intentArticle.source = bean.source;
        intentArticle.type = bean.type;
        intentArticle.url = bean.url;
        intentArticle.used = bean.used;
        intentArticle.who = bean.who;
        FragmentTransaction tx = fManager.beginTransaction();
        ArticleContentFragment articleContentFragment = ArticleContentFragment.newInstance();
        //加上Fragment替换动画
        tx.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        tx.replace(R.id.ly_content, articleContentFragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    private void getArticle(){
        articleService.gank(pageSize,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GankEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GankEntity gankEntity) {
                if (datas == null){
                    datas = new ArrayList<>();
                }
                for (GankEntity.ResultsBean bean:gankEntity.results){
                    Log.d(TAG, "onNext: "+bean.createdAt);
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

}
