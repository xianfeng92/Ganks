package com.example.data.net;

import com.example.data.entity.MeiziList;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class RestApiImpl implements RestApi {

    private RestApiImpl(){}

    public static RestApiImpl getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final RestApiImpl INSTANCE = new RestApiImpl();
    }

    @Override
    public Observable<MeiziList> meiziList(int page) {
        return RestCreator.getRxRestService().getMeiziList(page);
    }

}
