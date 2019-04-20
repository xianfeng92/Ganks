package com.example.data.net;

import com.example.domain.MeiziList;
import java.util.List;
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
    Observable<List<MeiziList.Meizi>> getMeiziList(@Path("page") int page);

    @GET("api/data/福利/10/{page}")
    Observable<MeiziList.Meizi> getMeizi(@Path("page") int page);

    @GET("api/random/data/福利/{number}")
    Observable<MeiziList> getRandomBeauties(@Path("number") int number);
}
