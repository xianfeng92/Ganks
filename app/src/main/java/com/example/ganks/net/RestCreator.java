package com.example.ganks.net;

import com.example.ganks.global.ConfigKeys;
import com.example.ganks.global.Gank;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.net.sip.SipErrorCode.TIME_OUT;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public final class RestCreator {

    /**
     * 构建OkHttp
     */
    public static final class OkHttpHolder {
        public static final int TIMEOUT = 60;
        public static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final OkHttpClient OK_HTTP_CLIENT = BUILDER
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

    }

        /**
         * 构建全局Retrofit客户端
         */
        private static final class RetrofitHolder {
            private static final String BASE_URL = Gank.getConfiguration(ConfigKeys.WEB_API_HOST.name());
            private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpHolder.OK_HTTP_CLIENT)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

    /**
     * Service接口
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
}
