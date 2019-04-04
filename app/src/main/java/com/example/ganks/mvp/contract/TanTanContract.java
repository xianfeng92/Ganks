package com.example.ganks.mvp.contract;

import com.example.ganks.mvp.base.IModel;
import com.example.ganks.mvp.base.IView;
import com.xforg.gank_core.entity.Meizi;
import java.util.List;
import io.reactivex.Observable;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public interface TanTanContract {

    interface View extends IView{
        void setNewData(List<Meizi.ResultsBean> mData);
    }

    interface Model extends IModel{
        Observable<Meizi> getMeizi(int page);
    }
}
