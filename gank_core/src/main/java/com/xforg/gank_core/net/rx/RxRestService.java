package com.xforg.gank_core.net.rx;

import com.xforg.gank_core.entity.Meizi;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created By apple on 2019/3/2
 * github: https://github.com/xianfeng92
 */
public interface RxRestService {

    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<Meizi> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);

    @GET("api/data/福利/10/{page}")
    Observable<Meizi> getMeizi(@Path("page") int page);

    @GET("{picture}")
    @Streaming
    Observable<ResponseBody> downloadWithRxjava(@Path("picture") String picture);

}
