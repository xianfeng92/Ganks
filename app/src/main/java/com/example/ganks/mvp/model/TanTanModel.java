package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.TanTanContract;
import com.xforg.gank_core.entity.Meizi;
import com.xforg.gank_core.net.RestCreator;

import io.reactivex.Observable;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanModel implements TanTanContract.Model{

    @Override
    public Observable<Meizi> getMeizi(int page) {
        Observable<Meizi> meizi = RestCreator.getRxRestService().getMeizi(page);
        return meizi;
    }

    @Override
    public void onDestroy() {

    }
}
