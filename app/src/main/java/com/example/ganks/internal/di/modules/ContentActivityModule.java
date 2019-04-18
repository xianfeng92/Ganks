package com.example.ganks.internal.di.modules;

import com.example.ganks.internal.di.components.CategoryComponent;
import com.example.ganks.internal.di.components.HomeComponent;
import com.example.ganks.internal.di.components.LoveMeiziComponent;
import com.example.ganks.internal.di.components.MeiziComponent;
import com.example.ganks.internal.di.components.TanTanComponent;
import dagger.Module;

/**
 * Created By zhongxianfeng on 19-4-18
 * github: https://github.com/xianfeng92
 */
@Module(subcomponents = {HomeComponent.class,
        LoveMeiziComponent.class,
        MeiziComponent.class,
        TanTanComponent.class,
        CategoryComponent.class})
public class ContentActivityModule {

}
