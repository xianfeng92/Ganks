package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.Scope.PerActivity;
import com.example.ganks.internal.di.modules.MeiziModule;
import com.example.ganks.mvp.ui.fragment.MeiziFragment;
import com.example.ganks.mvp.view.MeiziView;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Subcomponent(modules = MeiziModule.class)
public interface MeiziComponent {
    void inject(MeiziFragment meiziFragment);

    @Subcomponent.Builder
    interface Builder{
        MeiziComponent build();

        @BindsInstance
        MeiziComponent.Builder setMeiziView(MeiziView view);
    }
}
