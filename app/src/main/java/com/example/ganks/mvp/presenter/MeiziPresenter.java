package com.example.ganks.mvp.presenter;

import android.text.TextUtils;

import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.MeiziContract;
import com.xforg.gank_core.entity.Meizi;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class MeiziPresenter extends BasePresenter<MeiziContract.Model,MeiziContract.View> {

    public MeiziPresenter(MeiziContract.Model model, MeiziContract.View rootView){
        super(model, rootView);
    }

    public void requestData(int page){
        mModel.getMeizi(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Meizi>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Meizi meizi) {
                        List<String> urls = new ArrayList<>();
                        for (Meizi.ResultsBean resultsBean:meizi.results) {
                            if (!TextUtils.isEmpty(resultsBean.url))
                                urls.add(resultsBean.url);
                            mRootView.setNewData(urls);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class MeiziObserver extends DefaultObserver<List<Meizi>>{

        @Override
        public void onNext(List<Meizi> meizis) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

}
