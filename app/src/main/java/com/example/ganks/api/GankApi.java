package com.example.ganks.api;

import com.example.ganks.api.service.CommonService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class GankApi {

    public static CommonService buildServiceForGank(){
        return new Retrofit.Builder()
                .baseUrl(Api.APP_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(CommonService.class);
    }

}
