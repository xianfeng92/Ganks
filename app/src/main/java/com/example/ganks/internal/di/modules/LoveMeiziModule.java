package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.view.LoveMeiziView;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class LoveMeiziModule {

    private LoveMeiziView view;

    /**
     * 构建 LoveMeiziModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
     * @param view
     */
    public LoveMeiziModule(LoveMeiziView view){
        this.view = view;
    }

    @Provides
    LoveMeiziView provideLoveMeiziContractView(){
        return this.view;
    }
}
