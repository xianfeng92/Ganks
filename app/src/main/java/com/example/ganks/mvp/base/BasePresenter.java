package com.example.ganks.mvp.base;


/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class BasePresenter<V extends IView> implements IPresenter{

    protected V mRootView;

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
        this.mRootView = null;
    }

}
