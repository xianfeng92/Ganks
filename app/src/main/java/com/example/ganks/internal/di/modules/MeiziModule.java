package com.example.ganks.internal.di.modules;

import com.example.ganks.mvp.view.MeiziView;
import dagger.Module;
import dagger.Provides;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Module
public class MeiziModule {

    private MeiziView view;

    /**
     * 构建 MeiziModule 时,将View的实现类传进来,这样就可以提供 View 的实现类给 presenter
     * @param view
     */
    public MeiziModule(MeiziView view){
        this.view = view;
    }

    @Provides
    MeiziView provideMeiziView(){
        return view;
    }
}
