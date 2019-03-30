package com.example.ganks.mvp.contract;

import com.example.ganks.mvp.base.IModel;
import com.example.ganks.mvp.base.IView;
import com.xforg.gank_core.entity.DaoMeiziEntity;

import java.util.List;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public interface LoveMeiziContract {

    interface View extends IView{
        void setNewData(List<DaoMeiziEntity> mData);
    }

    interface Model extends IModel{
        List<DaoMeiziEntity> getMeiziFromDao();
    }
}
