package com.example.ganks.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
