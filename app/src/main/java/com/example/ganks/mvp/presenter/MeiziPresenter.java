package com.example.ganks.mvp.presenter;


import com.example.domain.Meizi;
import com.example.domain.interactor.DefaultObserver;
import com.example.domain.interactor.GetMeiziList;
import com.example.ganks.internal.di.Scope.PerActivity;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.MeiziView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
@PerActivity
public class MeiziPresenter extends BasePresenter<MeiziView> {

    GetMeiziList getMeiziList;

    @Inject
    public MeiziPresenter( MeiziView rootView,GetMeiziList getMeiziList){
        super(rootView);
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
