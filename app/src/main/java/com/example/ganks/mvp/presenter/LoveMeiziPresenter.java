package com.example.ganks.mvp.presenter;

import com.example.domain.MeiziList;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.view.LoveMeiziView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziPresenter extends BasePresenter<LoveMeiziView> {
    private static final String TAG = "LoveMeiziPresenter";
    public List<MeiziList.Meizi> resultsBeanList = new ArrayList<>();
    private Set<String> downLoadUrls = new TreeSet<>();

    @Inject
    public LoveMeiziPresenter(LoveMeiziView view){
        super(view);
    }

    public void loadDataByGreenDao() {
        resultsBeanList.clear();
    }
}
