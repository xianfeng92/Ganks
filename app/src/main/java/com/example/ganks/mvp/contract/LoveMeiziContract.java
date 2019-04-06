package com.example.ganks.mvp.contract;

import com.example.domain.Meizi;
import com.example.ganks.mvp.base.IModel;
import com.example.ganks.mvp.base.IView;
import java.util.List;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public interface LoveMeiziContract {

    interface View extends IView{
        void setNewData(List<Meizi> mData);
    }

    interface Model extends IModel{
    }
}
