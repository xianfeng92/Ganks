package com.example.ganks.di.components;

import com.example.ganks.di.Scope.PerFragment;
import com.example.ganks.di.modules.HomeModule;
import com.example.ganks.mvp.ui.fragment.HomeFragment;
import com.example.ganks.mvp.view.HomeView;
import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerFragment
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);

    @Subcomponent.Builder
    interface Builder{
        HomeComponent build();

        @BindsInstance
        HomeComponent.Builder setHomeView(HomeView view);
    }
}
