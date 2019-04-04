package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.internal.di.modules.TanTanModule;
import com.example.ganks.mvp.ui.fragment.TanTanFragment;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Component(modules = TanTanModule.class,dependencies = ApplicationComponent.class)
public interface TanTanComponent {
    void inject(TanTanFragment tanTanFragment);
}
