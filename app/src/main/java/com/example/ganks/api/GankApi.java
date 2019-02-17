package com.example.ganks.api;

import com.example.ganks.api.service.CommonService;
import com.example.ganks.global.ConfigKeys;
import com.example.ganks.global.Gank;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class GankApi {

    // 采用静态内部类来实现单例
    public static final class RetrofitHolder{

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl((String) Gank.getConfiguration(ConfigKeys.BASE_URL.name()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static final class ServiceHolder{

        private static final CommonService COMMON_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(CommonService.class);

    }

    public static CommonService buildServiceForGank(){
        return ServiceHolder.COMMON_SERVICE;
    }

}
