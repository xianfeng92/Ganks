package com.example.ganks.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganks.R;
import com.example.ganks.adapter.MianViewPagerAdapter;
import com.example.ganks.utils.CategoryType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    TabLayout tabs;
    ViewPager mainPager;
    private List<CategoryArticleFragment> mFragments;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        tabs = view.findViewById(R.id.tabs);
        mainPager = view.findViewById(R.id.mainPager);
        initData();
        return view;
    }



    private void initData(){
        if (mFragments == null){
            mFragments = new ArrayList<>();
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.ANDROID_STR));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.IOS_STR));
            mFragments.add(CategoryArticleFragment.newInstance(CategoryType.FRONT_STR));
        }
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new MianViewPagerAdapter(getChildFragmentManager(),mFragments));
        tabs.setupWithViewPager(mainPager);
    }

}
