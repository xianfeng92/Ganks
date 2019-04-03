package com.example.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
