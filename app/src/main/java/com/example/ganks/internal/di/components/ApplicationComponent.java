package com.example.ganks.internal.di.components;

import android.content.Context;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.MeiziRepository;
import com.example.ganks.internal.di.modules.ApplicationModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread PostExecutionThread();
    MeiziRepository meiziRepository();
}
