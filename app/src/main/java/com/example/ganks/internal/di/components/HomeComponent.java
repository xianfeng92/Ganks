package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.internal.di.modules.HomeModule;
import com.example.ganks.mvp.ui.fragment.HomeFragment;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Component(modules = HomeModule.class,dependencies = ApplicationComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
