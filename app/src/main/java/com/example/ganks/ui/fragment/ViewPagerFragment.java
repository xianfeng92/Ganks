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
 * Created By apple on 2019/3/10
 * github: https://github.com/xianfeng92
 */
public class ViewPagerFragment extends GankDelegate {

    private List<GankDelegate> mFragments;
    TabLayout tabs;
    ViewPager mainPager;

    public static ViewPagerFragment newInstance(){
        Bundle args = new Bundle();
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        viewPagerFragment.setArguments(args);
        return viewPagerFragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFragments == null){
            mFragments = new ArrayList<>();
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.ANDROID_STR,getActivity().getSupportFragmentManager()));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.IOS_STR,getActivity().getSupportFragmentManager()));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.FRONT_STR,getActivity().getSupportFragmentManager()));
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initView(rootView);
    }

    private void initView(View view){
        tabs = view.findViewById(R.id.tabs);
        mainPager = view.findViewById(R.id.mainPager);
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new MianViewPagerAdapter(getChildFragmentManager(),mFragments));
        tabs.setupWithViewPager(mainPager);
    }

}
