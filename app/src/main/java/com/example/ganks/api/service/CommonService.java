package com.example.ganks.api.service;


import com.example.ganks.bean.GankEntity;
import com.example.ganks.bean.Meizi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommonService {

    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);

    @GET("api/data/福利/10/{page}")
    Observable<Meizi> getMeizi(@Path("page") int page);
}
