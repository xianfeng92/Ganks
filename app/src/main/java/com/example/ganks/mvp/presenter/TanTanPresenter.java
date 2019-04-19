package com.example.ganks.mvp.presenter;

import com.example.domain.MeiziList;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.TanTanView;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class TanTanPresenter extends BasePresenter<TanTanView> {
//    private GetMeiziList getMeiziList;

    @Inject
    public TanTanPresenter(TanTanView rootView){
        super(rootView);
//        this.getMeiziList = getMeiziList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void requestData(int page){
//        this.getMeiziList.execute(new TanTanObserver(),page);
    }

    public void addToFavorites(MeiziList.Meizi meizi){
//        this.getMeiziList.addToFavorite(meizi);
    }

}
