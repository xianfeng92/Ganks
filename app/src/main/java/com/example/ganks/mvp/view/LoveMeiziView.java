package com.example.ganks.mvp.view;

import com.example.domain.MeiziList;
import com.example.ganks.mvp.base.IView;
import java.util.List;

/**
 * Created By apple on 2019/4/6
 * github: https://github.com/xianfeng92
 */
public interface LoveMeiziView extends IView {
    void setNewData(List<MeiziList.Meizi> mData);

}
