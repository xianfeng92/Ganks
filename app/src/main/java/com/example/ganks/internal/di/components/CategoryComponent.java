package com.example.ganks.internal.di.components;

import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.internal.di.modules.CategoryModule;
import com.example.ganks.mvp.ui.fragment.article.CategoryArticleFragment;

import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@PerActivity
@Component(modules = CategoryModule.class,dependencies = ApplicationComponent.class)
public interface CategoryComponent {
    void inject(CategoryArticleFragment categoryArticleFragment);
}
