package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.Scope.PerFragment;
import com.example.ganks.internal.di.modules.CategoryModule;
import com.example.ganks.mvp.ui.fragment.article.CategoryArticleFragment;
import com.example.ganks.mvp.view.CategoryView;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerFragment
@Subcomponent(modules = CategoryModule.class)
public interface CategoryComponent {
    void inject(CategoryArticleFragment categoryArticleFragment);

    @Subcomponent.Builder
    interface Builder{
        CategoryComponent build();

        @BindsInstance
        CategoryComponent.Builder setCateGoryComponentView(CategoryView view);
    }
}
