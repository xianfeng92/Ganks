package com.example.ganks.internal.di.modules;


import com.example.ganks.mvp.contract.TanTanContract;
import com.example.ganks.mvp.model.TanTanModel;
import dagger.Module;
import dagger.Provides;


/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class TanTanModule {

    private TanTanContract.View view;
    /**
     * 构建 TanTanModule 时,将View的实现类传进来,这样就可以提供 View 的实现类给 presenter
     * @param view
     */
    public TanTanModule(TanTanContract.View view){
        this.view = view;
    }

    @Provides
    TanTanContract.View provideTanTanView(){
        return view;
    }

    @Provides
    TanTanContract.Model provideTanTanModel(TanTanModel model){
        return model;
    }
}
