package com.example.ganks.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.ganks.R;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.mvp.presenter.HomePresenter;
import com.orhanobut.logger.Logger;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class HomeFragment extends BaseDelegate<HomePresenter> {

    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (findChildFragment(ViewPagerFragment.class) == null) {
            loadRootFragment(R.id.fl_home_container, ViewPagerFragment.newInstance());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.d("onLazyInitView");
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
