package com.example.data.HelperImpl;

import com.example.data.net.RxRestService;
import com.example.domain.MeiziList;
import com.example.domain.helper.HttpHelper;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 *
 * 对外隐藏进行网络请求的实现细节
 * 通过Dagger来将 HttpHelperImpl 注入到需要的地方
 * HttpHelperImpl 依赖的 RxRestService 是由 HttpModule 的 provideRxRestService 提供
 *
 */
public class HttpHelperImpl implements HttpHelper {

    private RxRestService rxRestService;

    @Inject
    HttpHelperImpl(RxRestService rxRestService){
        this.rxRestService = rxRestService;
    }

    @Override
    public Observable<List<MeiziList.Meizi>> meiziList(int page) {
        return rxRestService.getMeiziList(page);
    }
}
