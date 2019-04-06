package com.example.ganks.mvp.presenter;

import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.CategoryView;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class CategoryPresenter extends BasePresenter<CategoryView> {

    @Inject
    public CategoryPresenter(CategoryView view){
        super(view);
    }
}
