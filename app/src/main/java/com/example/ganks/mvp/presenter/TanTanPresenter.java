package com.example.ganks.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.TanTanContract;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import com.xforg.gank_core.entity.Meizi;
import com.xforg.gank_core.utils.GreenDaoHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanPresenter extends BasePresenter<TanTanContract.Model,TanTanContract.View> {
    private static final String TAG = "TanTanPresenter";
    private DaoMeiziEntity daoGankEntity;

    public TanTanPresenter(TanTanContract.Model model, TanTanContract.View rootView){
        super(model,rootView);
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
                        List<Meizi.ResultsBean> resultsBeans = new ArrayList<>();
                        for (Meizi.ResultsBean resultsBean:meizi.results) {
                            if (!TextUtils.isEmpty(resultsBean.url))
                                resultsBeans.add(resultsBean);
                            mRootView.setNewData(resultsBeans);
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

    private DaoMeiziEntity entityToDao(Meizi.ResultsBean entity){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        if (daoGankEntity == null){
            daoGankEntity = new DaoMeiziEntity();
        }
        daoGankEntity._id = entity._id;
        daoGankEntity.createdAt = entity.createdAt;
        daoGankEntity.desc = entity.desc;
        daoGankEntity.publishedAt = entity.publishedAt;
        daoGankEntity.source = entity.source;
        daoGankEntity.type = entity.type;
        daoGankEntity.url = entity.url;
        daoGankEntity.used = entity.used;
        daoGankEntity.who = entity.who;
        daoGankEntity.addTime =str;
        return daoGankEntity;
    }

    public void addToFavorites(Object o){
        Meizi.ResultsBean resultsBean = (Meizi.ResultsBean)o;
        DaoMeiziEntity daoMeiziEntity = entityToDao(resultsBean);
        if (!GreenDaoHelper.isDaoContainMeizi(daoMeiziEntity._id)){
            GreenDaoHelper.insert(daoMeiziEntity);
        }else {
            Log.d(TAG, "you already love It!!");
        }
    }


    public void removeByid(Meizi.ResultsBean entity) {
        DaoMeiziEntity daoGankEntity = entityToDao(entity);
        GreenDaoHelper.removeById(daoGankEntity._id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
