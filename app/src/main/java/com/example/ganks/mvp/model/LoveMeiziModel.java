package com.example.ganks.mvp.model;

import com.example.ganks.mvp.contract.LoveMeiziContract;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import com.xforg.gank_core.utils.GreenDaoHelper;
import java.util.List;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziModel implements LoveMeiziContract.Model {

    @Override
    public void onDestroy() {

    }

    @Override
    public List<DaoMeiziEntity> getMeiziFromDao() {
        return GreenDaoHelper.getAllMeiziEntity();
    }
}
