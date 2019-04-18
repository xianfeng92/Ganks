package com.example.ganks.internal.di.modules;

import android.content.Context;
import com.example.data.executor.JobExecutor;
import com.example.data.repository.MeiziDataRepository;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.MeiziRepository;
import com.example.ganks.base.GanksApplication;
import com.example.ganks.executor.UIThread;
import com.example.ganks.internal.di.components.ContentActivityComponent;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module(subcomponents = {ContentActivityComponent.class})
public class ApplicationModule {

    private final GanksApplication application;

    public ApplicationModule(GanksApplication application){
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
