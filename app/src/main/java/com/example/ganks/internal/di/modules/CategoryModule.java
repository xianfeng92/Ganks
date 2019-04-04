package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.contract.CategoryContract;
import com.example.ganks.mvp.model.CategoryModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class CategoryModule {
    private CategoryContract.View view;

    public CategoryModule(CategoryContract.View view){
        this.view = view;
    }

    @Provides
    CategoryContract.View provideCategoryContractView(){
        return this.view;
    }

    @Provides
    CategoryContract.Model proviceCategoryContractModel(CategoryModel model){
        return model;
    }
}
