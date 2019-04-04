package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.contract.LoveMeiziContract;
import com.example.ganks.mvp.model.LoveMeiziModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class LoveMeiziModule {

    private LoveMeiziContract.View view;

    /**
     * 构建 LoveMeiziModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
     * @param view
     */
    public LoveMeiziModule(LoveMeiziContract.View view){
        this.view = view;
    }

    @Provides
    LoveMeiziContract.View provideLoveMeiziContractView(){
        return this.view;
    }

    @Provides
    LoveMeiziContract.Model provideLoveMeiziContractModel(LoveMeiziModel model){
        return model;
    }

}
