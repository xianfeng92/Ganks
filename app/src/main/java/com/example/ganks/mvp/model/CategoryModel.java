package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.CategoryContract;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class CategoryModel implements CategoryContract.Model {

    @Inject
    CategoryModel(){}

    @Override
    public void onDestroy() {

    }
}
