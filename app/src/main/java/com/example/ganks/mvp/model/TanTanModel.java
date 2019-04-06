package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.TanTanContract;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanModel implements TanTanContract.Model{

    @Inject
    TanTanModel(){}

    @Override
    public void onDestroy() {

    }
}
