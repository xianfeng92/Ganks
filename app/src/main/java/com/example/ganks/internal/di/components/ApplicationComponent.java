package com.example.ganks.internal.di.components;

import com.example.ganks.base.GanksApplication;
import com.example.ganks.internal.di.modules.ApplicationModule;
import com.example.ganks.internal.di.modules.HttpModule;
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
