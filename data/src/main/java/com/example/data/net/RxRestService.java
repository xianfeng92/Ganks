package com.example.data.net;

import com.example.data.entity.MeiziList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created By apple on 2019/3/2
 * github: https://github.com/xianfeng92
 */
public interface RxRestService {

    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<MeiziList> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);

    @GET("api/data/福利/10/{page}")
    Observable<MeiziList> getMeiziList(@Path("page") int page);
}
