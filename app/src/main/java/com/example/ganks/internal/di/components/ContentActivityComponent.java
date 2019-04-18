package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.modules.ContentActivityModule;
import com.example.ganks.mvp.ui.activitys.ContentActivity;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-18
 * github: https://github.com/xianfeng92
 */
@Subcomponent(modules = ContentActivityModule.class)
public interface ContentActivityComponent {
    void inject(ContentActivity contentActivity);

    @Subcomponent.Builder
    interface Builder{
        ContentActivityComponent build();
    }

    HomeComponent.Builder homeComponent();

    LoveMeiziComponent.Builder loveMeiziComponent();

    MeiziComponent.Builder meiziComponent();

    TanTanComponent.Builder tantanComponent();

    CategoryComponent.Builder categoryComponent();
}
