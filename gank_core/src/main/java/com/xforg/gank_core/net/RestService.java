package com.xforg.gank_core.net;

import com.xforg.gank_core.entity.Meizi;

import java.util.WeakHashMap;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public interface RestService {

    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<Meizi> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);

    @GET("api/data/福利/10/{page}")
    Observable<Meizi> getMeizi(@Path("page") int page);

    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @GET("{picture}")
    @Streaming
    Observable<ResponseBody> downloadWithRajava(@Path("picture") String picture);

    @GET("{picture}")
    @Streaming
    Call<ResponseBody> downloadWithoutRajava(@Path("picture") String picture);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}

