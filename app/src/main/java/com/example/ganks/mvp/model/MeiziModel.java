package com.example.ganks.mvp.model;


import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.mvp.contract.MeiziContract;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
@PerActivity
public class MeiziModel implements MeiziContract.Model {

    @Inject
     MeiziModel(){}

    @Override
    public void onDestroy() {}
}
