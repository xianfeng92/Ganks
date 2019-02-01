package com.example.ganks.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ganks.fragment.CategoryArticleFragment;
import com.example.ganks.utils.CategoryType;

import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class MianViewPagerAdapter extends FragmentPagerAdapter {

    private List<CategoryArticleFragment> mFragments;

    public MianViewPagerAdapter(FragmentManager fm,List<CategoryArticleFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return CategoryType.getPageTitleByPosition(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}