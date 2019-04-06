package com.xforg.gank_core.app;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class CategoryType {

    public static final String ANDROID_STR = "Android";
    public static final String IOS_STR = "iOS";
    public static final String FRONT_STR = "前端";

    public static String getPageTitleByPosition(int position) {
        if (position == 0){
            return ANDROID_STR;
        } else if (position == 1){
            return IOS_STR;
        } else if (position == 2){
            return FRONT_STR;
        } else {
            return "";
        }
    }
}
