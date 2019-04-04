package com.example.ganks.executor;

import com.example.domain.executor.PostExecutionThread;
import javax.inject.Inject;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
public class UIThread implements PostExecutionThread {

    @Inject
    UIThread(){}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
