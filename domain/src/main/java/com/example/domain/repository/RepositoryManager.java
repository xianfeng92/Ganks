package com.example.domain.repository;

import com.example.domain.MeiziList;
import com.example.domain.helper.DbHelper;
import com.example.domain.helper.GreenDaoHelper;
import com.example.domain.helper.HttpHelper;
import com.example.domain.helper.PreferenceHelper;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class RepositoryManager implements HttpHelper, DbHelper, PreferenceHelper, GreenDaoHelper {

    private HttpHelper httpHelper;
    private DbHelper dbHelper;
    private PreferenceHelper preferenceHelper;
    private GreenDaoHelper greenDaoHelper;

    @Inject
    public RepositoryManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper,GreenDaoHelper greenDaoHelper){
        this.httpHelper = httpHelper;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
        this.greenDaoHelper = greenDaoHelper;
    }

    @Override
    public Observable<List<MeiziList.Meizi>> meiziList(int page) {
        return httpHelper.meiziList(page);
    }

    @Override
    public Observable<MeiziList> getRandomMeizi(int num) {
        return httpHelper.getRandomMeizi(num);
    }

    @Override
    public void addToFavorite(MeiziList.Meizi meizi) {
        greenDaoHelper.addToFavorite(meizi);
    }

    @Override
    public List<MeiziList.Meizi> getAllMeiziEntity() {
        return greenDaoHelper.getAllMeiziEntity();
    }
}
