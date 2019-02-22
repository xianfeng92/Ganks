package com.example.ganks.delegate;


/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public abstract class GankDelegate extends BaseDelegate{
    @SuppressWarnings("unchecked")
    public <T extends GankDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
