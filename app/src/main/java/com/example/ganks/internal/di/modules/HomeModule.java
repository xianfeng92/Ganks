package com.example.ganks.internal.di.modules;
import com.example.ganks.mvp.view.HomeView;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class HomeModule {
    private HomeView view;

    public HomeModule(HomeView view){
        this.view = view;
    }

    @Provides
    HomeView provideHomeContractView(){
        return this.view;
    }
}
