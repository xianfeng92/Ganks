package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.view.CategoryView;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class CategoryModule {
    private CategoryView view;

    public CategoryModule(CategoryView view){
        this.view = view;
    }

    @Provides
    CategoryView provideCategoryContractView(){
        return this.view;
    }
}
