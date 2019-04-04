package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.contract.HomeContract;
import com.example.ganks.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class HomeModule {
    private HomeContract.View view;

    public HomeModule(HomeContract.View view){
        this.view = view;
    }

    @Provides
    HomeContract.View provideHomeContractView(){
        return this.view;
    }

    @Provides
    HomeContract.Model provideHomeContractModel(HomeModel model){
        return model;
    }
}
