package com.example.ganks.di.components;

import com.example.ganks.base.GanksApplication;
import com.example.ganks.di.modules.HttpModule;
import com.example.ganks.di.modules.ApplicationModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    void inject(GanksApplication application);

    ContentActivityComponent.Builder contentActivityComponent();
}
