package com.example.ganks.net;

import com.example.ganks.global.ConfigKeys;
import com.example.ganks.global.Gank;
import com.example.ganks.net.RestService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class GankApi {

    // 采用静态内部类来实现单例
    private static final class RetrofitHolder{

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl((String) Gank.getConfiguration(ConfigKeys.BASE_URL.name()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class ServiceHolder{

        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService buildServiceForGank(){
        return ServiceHolder.REST_SERVICE;
    }

}
