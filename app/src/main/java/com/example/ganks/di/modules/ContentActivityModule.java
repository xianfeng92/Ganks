package com.example.ganks.di.modules;

import com.example.ganks.di.components.CategoryComponent;
import com.example.ganks.di.components.HomeComponent;
import com.example.ganks.di.components.TanTanComponent;
import com.example.ganks.di.components.LoveMeiziComponent;
import com.example.ganks.di.components.MeiziComponent;

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
