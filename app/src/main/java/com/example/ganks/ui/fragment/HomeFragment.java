package com.example.ganks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganks.R;
import com.example.ganks.ui.adapter.MianViewPagerAdapter;
import com.example.ganks.ui.fragment.article.CategoryArticleFragment;
import com.xforg.gank_core.app.CategoryType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class HomeFragment extends BaseMainFragment {

    TabLayout tabs;
    ViewPager mainPager;
    private List<CategoryArticleFragment> mFragments;

    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        tabs = view.findViewById(R.id.tabs);
        mainPager = view.findViewById(R.id.mainPager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fragment_home, HomeFragment.newInstance());
        }
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
}
