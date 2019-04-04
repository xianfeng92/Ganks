package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.HomeContract;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class HomeModel implements HomeContract.Model {

    @Inject
    HomeModel(){}

    @Override
    public void onDestroy() {}
}
