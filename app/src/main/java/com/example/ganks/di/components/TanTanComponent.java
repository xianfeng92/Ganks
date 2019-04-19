package com.example.ganks.di.components;

import com.example.ganks.di.Scope.PerFragment;
import com.example.ganks.di.modules.TanTanModule;
import com.example.ganks.mvp.ui.fragment.TanTanFragment;
import com.example.ganks.mvp.view.TanTanView;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerFragment
@Subcomponent(modules = TanTanModule.class)
public interface TanTanComponent {
    void inject(TanTanFragment tanTanFragment);

    @Subcomponent.Builder
    interface Builder{
        TanTanComponent build();

        @BindsInstance
        TanTanComponent.Builder setTanTanView(TanTanView view);
    }
}
