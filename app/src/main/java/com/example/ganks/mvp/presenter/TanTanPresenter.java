package com.example.ganks.mvp.presenter;

import com.example.domain.MeiziList;
import com.example.domain.repository.RepositoryManager;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.TanTanView;
import com.xforg.gank_core.utils.RxUtils;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanPresenter extends BasePresenter<TanTanView> {

    @Inject
    RepositoryManager repositoryManager;

    @Inject
    public TanTanPresenter(TanTanView rootView){
        super(rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void requestData(int page){
        Observable<MeiziList> observable = repositoryManager.meiziList(page);
        observable.compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<MeiziList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziList meiziList) {
                        mRootView.setNewData(meiziList.results);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addToFavorites(MeiziList.Meizi meizi){
    }

}
