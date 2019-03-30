package com.example.ganks.mvp.presenter;

import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.CategoryContract;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class CategoryPresenter extends BasePresenter<CategoryContract.Model,CategoryContract.View> {

    public CategoryPresenter(CategoryContract.Model model,CategoryContract.View view){
        super(model,view);
    }
}
