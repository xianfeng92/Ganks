package com.xforg.gank_core.net;
import com.xforg.gank_core.app.ConfigKeys;
import com.xforg.gank_core.app.Gank;
import com.xforg.gank_core.net.rx.RxRestService;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public final class RestCreator {

    /**
     * 构建OkHttp
     */
    public static final class OkHttpHolder {
        static final int TIME_OUT = 60;
        static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final OkHttpClient OK_HTTP_CLIENT = BUILDER
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

        /**
         * 构建全局Retrofit客户端
         *
         */
        private static final class RetrofitHolder {
            private static final String BASE_URL = Gank.getConfiguration(ConfigKeys.BASE_URL.name());
            private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpHolder.OK_HTTP_CLIENT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

    /**
     * 构建全局Retrofit客户端
     * Retrofit请求网络时，直接返回String类型
     */
    private static final class StringRetrofitHolder {
        private static final String BASE_URL = Gank.getConfiguration(ConfigKeys.BASE_URL.name());
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
                StringRetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * Service接口
     */
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }

    /**
     * 构建一个用于下载的Service接口
     */
    public static RestService getRestServiceForDownLoad (){
        final String BASE_URL = "https://ws1.sinaimg.cn/";

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RestService.class);
    }
}
