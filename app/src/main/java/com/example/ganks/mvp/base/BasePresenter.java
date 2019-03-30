package com.example.ganks.mvp.base;


/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter{

    protected M mModel;
    protected V mRootView;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        this.onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        this.onStart();
    }

    public BasePresenter() {
        this.onStart();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (this.mModel != null) {
            this.mModel.onDestroy();
        }

        this.mModel = null;
        this.mRootView = null;
    }

}
