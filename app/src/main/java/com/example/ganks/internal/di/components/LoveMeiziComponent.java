package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.internal.di.modules.LoveMeiziModule;
import com.example.ganks.mvp.ui.fragment.LoveMeiziFragment;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Component(modules = LoveMeiziModule.class,dependencies = ApplicationComponent.class)
public interface LoveMeiziComponent {
    void inject(LoveMeiziFragment loveMeiziFragment);
}
