package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.LoveMeiziContract;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziModel implements LoveMeiziContract.Model {

    @Inject
    LoveMeiziModel(){}

    @Override
    public void onDestroy() {}

}
