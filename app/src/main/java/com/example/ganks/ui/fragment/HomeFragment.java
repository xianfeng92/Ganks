package com.example.ganks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.ganks.R;
import com.example.ganks.ui.adapter.MianViewPagerAdapter;
import com.example.ganks.ui.fragment.article.CategoryArticleFragment;
import com.xforg.gank_core.app.CategoryType;
import com.xforg.gank_core.delegates.GankDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class HomeFragment extends GankDelegate {
    private static final String TAG = "HomeFragment";

    TabLayout tabs;
    ViewPager mainPager;
    private List<GankDelegate> mFragments;

    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        tabs = rootView.findViewById(R.id.tabs);
        mainPager = rootView.findViewById(R.id.mainPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData(){
        if (mFragments == null){
            mFragments = new ArrayList<>();
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.ANDROID_STR,getActivity().getSupportFragmentManager()));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.IOS_STR,getActivity().getSupportFragmentManager()));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.FRONT_STR,getActivity().getSupportFragmentManager()));
        }
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new MianViewPagerAdapter(getChildFragmentManager(),mFragments));
        tabs.setupWithViewPager(mainPager);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
