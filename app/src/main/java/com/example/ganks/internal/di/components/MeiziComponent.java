package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.internal.di.modules.MeiziModule;
import com.example.ganks.mvp.ui.fragment.MeiziFragment;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Component(modules = MeiziModule.class,dependencies = ApplicationComponent.class)
public interface MeiziComponent {
    void inject(MeiziFragment meiziFragment);
}
