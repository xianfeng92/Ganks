package com.xforg.gank_core.delegates;


import com.xforg.gank_core.permission.GankPermission;

/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public abstract class GankDelegate extends GankPermission {
    @SuppressWarnings("unchecked")
    public <T extends GankDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
