package com.example.ganks.mvp.presenter;


import com.example.domain.MeiziList;
import com.example.domain.repository.RepositoryManager;
import com.example.ganks.di.Scope.PerFragment;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.MeiziView;
import com.xforg.gank_core.utils.RxUtils;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
@PerFragment
public class MeiziPresenter extends BasePresenter<MeiziView> {

    @Inject
    RepositoryManager repositoryManager;

    @Inject
    public MeiziPresenter( MeiziView rootView){
        super(rootView);
    }

    public void requestData(int page){
        Observable<MeiziList> meiziListObservable = repositoryManager.meiziList(page);
        meiziListObservable
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<MeiziList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziList meizis) {
                        mRootView.setNewData(meizis.results);
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
}
