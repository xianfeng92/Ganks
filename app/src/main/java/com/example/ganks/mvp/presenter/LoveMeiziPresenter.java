package com.example.ganks.mvp.presenter;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.domain.Meizi;
import com.example.domain.interactor.GetMeiziList;
import com.example.ganks.mvp.base.BasePresenter;
import com.example.ganks.mvp.contract.LoveMeiziContract;
import com.xforg.gank_core.net.rx.RxRestService;
import com.xforg.gank_core.utils.File.FileUtil;
import com.xforg.gank_core.utils.ToastUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By apple on 2019/3/30
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziPresenter extends BasePresenter<LoveMeiziContract.Model,LoveMeiziContract.View> {
    private static final String TAG = "LoveMeiziPresenter";
    private GetMeiziList getMeiziList;
    public List<Meizi> resultsBeanList = new ArrayList<>();
    private Set<String> downLoadUrls = new TreeSet<>();
    private Retrofit retrofit;

    @Inject
    public LoveMeiziPresenter(LoveMeiziContract.Model model,LoveMeiziContract.View view, GetMeiziList getMeiziList){
        super(model,view);
        this.getMeiziList = getMeiziList;
    }

    public void loadDataByGreenDao() {
        resultsBeanList.clear();
//        List<Meizi> list = mModel.getMeiziFromDao();
//        Log.d(TAG, "loadDataByGreenDao: " + list.size());
//        resultsBeanList.addAll(list);
//        mRootView.setNewData(resultsBeanList);
    }

    // 保存指定url的图片
    public void downLoad(final String url) {
        Log.d(TAG, "downLoad: " + url);
        if (downLoadUrls.contains(url)) {
            ToastUtils.showShortToastSafe("已保存！");
            return;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://ws1.sinaimg.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(RxRestService.class).downloadWithRxjava(subString(url))
                //在新线程中实现该方法
                .subscribeOn(Schedulers.newThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                byte[] bys;
                try {
                    // OkHttp请求回调中responseBody.bytes();只能有效调用一次.
                    // 调用responseBody.bytes();的时候数据流已经关闭了，再次调用就会出现错误提示java.lang.IllegalStateException: closed
                    bys = responseBody.bytes();
                    File file = FileUtil.saveBitmap(BitmapFactory.decodeByteArray(bys, 0, bys.length),
                            Environment.getExternalStorageState() + "/image/", 80);
                    if (file != null) {
                        ToastUtils.showShortToastSafe("保存成功！");
                        downLoadUrls.add(url);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    // 提取url中baseUrl之后的字符串
    private String subString(String url) {
        return url.substring(23);
    }
}
