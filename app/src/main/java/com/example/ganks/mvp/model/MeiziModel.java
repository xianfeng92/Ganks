package com.example.ganks.mvp.model;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.mvp.contract.MeiziContract;
import com.xforg.gank_core.entity.Meizi;
import com.xforg.gank_core.net.RestCreator;
import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
@PerActivity
public class MeiziModel implements MeiziContract.Model {

    @Inject
     MeiziModel(){}

    @Override
    public Observable<Meizi> getMeizi(int page) {
        Observable<Meizi> meizi = RestCreator.getRxRestService().getMeizi(page);
        return meizi ;
    }

    @Override
    public void onDestroy() {}
}
