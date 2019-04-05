package com.example.ganks.mvp.presenter;


import com.example.domain.Meizi;
import com.example.domain.interactor.DefaultObserver;
import com.example.domain.interactor.GetMeiziList;
import com.example.ganks.internal.di.PerActivity;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.MeiziContract;
import java.util.List;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
@PerActivity
public class MeiziPresenter extends BasePresenter<MeiziContract.Model,MeiziContract.View> {

    GetMeiziList getMeiziList;

    @Inject
    public MeiziPresenter(MeiziContract.Model model, MeiziContract.View rootView,GetMeiziList getMeiziList){
        super(model, rootView);
        this.getMeiziList = getMeiziList;
    }

    public void requestData(int page){
        this.getMeiziList.execute(new MeiziObserver(),page);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final class MeiziObserver extends DefaultObserver<List<Meizi>> {
        @Override
        public void onNext(List<Meizi> meizis) {
                    mRootView.setNewData(meizis);
            }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
