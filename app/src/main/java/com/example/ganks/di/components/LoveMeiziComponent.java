package com.example.ganks.di.components;

import com.example.ganks.di.Scope.PerFragment;
import com.example.ganks.di.modules.LoveMeiziModule;
import com.example.ganks.mvp.ui.fragment.LoveMeiziFragment;
import com.example.ganks.mvp.view.LoveMeiziView;
import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerFragment
@Subcomponent(modules = LoveMeiziModule.class)
public interface LoveMeiziComponent {
    void inject(LoveMeiziFragment loveMeiziFragment);

    @Subcomponent.Builder
    interface Builder{
        LoveMeiziComponent build();

        @BindsInstance
        LoveMeiziComponent.Builder setLoveMeiziView(LoveMeiziView view);
    }
}
