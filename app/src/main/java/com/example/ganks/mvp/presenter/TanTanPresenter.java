package com.example.ganks.mvp.presenter;

import com.example.domain.Meizi;
import com.example.domain.interactor.DefaultObserver;
import com.example.domain.interactor.GetMeiziList;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.TanTanContract;
import java.util.List;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanPresenter extends BasePresenter<TanTanContract.Model,TanTanContract.View> {
    private static final String TAG = "TanTanPresenter";
    private GetMeiziList getMeiziList;

    @Inject
    public TanTanPresenter(TanTanContract.Model model, TanTanContract.View rootView, GetMeiziList getMeiziList){
        super(model,rootView);
        this.getMeiziList = getMeiziList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void requestData(int page){
        this.getMeiziList.execute(new TanTanObserver(),page);
    }

    public void addToFavorites(Meizi meizi){
        this.getMeiziList.addToFavorite(meizi);
    }

    private final class TanTanObserver extends DefaultObserver<List<Meizi>> {

        @Override
        public void onNext(List<Meizi> meizis) {
            if (meizis != null && meizis.size() > 0){
                mRootView.setNewData(meizis);
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
