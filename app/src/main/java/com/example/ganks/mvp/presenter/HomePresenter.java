package com.example.ganks.mvp.presenter;

import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.HomeView;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class HomePresenter extends BasePresenter<HomeView> {

    @Inject
    HomePresenter(){};

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
