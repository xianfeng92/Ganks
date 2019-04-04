package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.contract.MeiziContract;
import com.example.ganks.mvp.model.MeiziModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class MeiziModule {

    private MeiziContract.View view;

    /**
     * 构建 MeiziModule 时,将View的实现类传进来,这样就可以提供 View 的实现类给 presenter
     * @param view
     */
    public MeiziModule(MeiziContract.View view){
        this.view = view;
    }

    @Provides
    MeiziContract.View provideMeiziView(){
        return view;
    }

    @Provides
    MeiziContract.Model provideMeiziModel(MeiziModel model){
        return model;
    }
}
