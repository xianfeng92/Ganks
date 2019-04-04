package com.example.ganks.internal.di.modules;

import android.content.Context;
import com.example.data.executor.JobExecutor;
import com.example.data.repository.MeiziDataRepository;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.MeiziRepository;
import com.example.ganks.base.BaseApplication;
import com.example.ganks.executor.UIThread;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class ApplicationModule {

    private final BaseApplication application;

    public ApplicationModule(BaseApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor){
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread){
        return uiThread;
    }

    @Provides
    @Singleton
    MeiziRepository provideMeiziRepository(MeiziDataRepository meiziDataRepository){
        return meiziDataRepository;
    }
}
