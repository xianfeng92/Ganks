package com.example.ganks.di.modules;

import android.content.Context;
import com.example.data.HelperImpl.DbHelperImpl;
import com.example.data.HelperImpl.GreenDaoHelperImpl;
import com.example.data.HelperImpl.HttpHelperImpl;
import com.example.data.HelperImpl.PreferenceHelperImpl;
import com.example.domain.helper.DbHelper;
import com.example.domain.helper.GreenDaoHelper;
import com.example.domain.helper.HttpHelper;
import com.example.domain.helper.PreferenceHelper;
import com.example.ganks.base.GanksApplication;
import com.example.ganks.di.components.ContentActivityComponent;
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
    HttpHelper provideHttpHelper(HttpHelperImpl helper){
        return helper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbHelperImpl helper){
        return helper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl helper){
        return helper;
    }

    @Provides
    @Singleton
    GreenDaoHelper provideGreenDaoHelper(GreenDaoHelperImpl helper){
        return helper;
    }
}
