package com.example.ganks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.ganks.base.GanksApplication;

/**
 * ConfigManage
 */
public enum ConfigManage {
    INSTANCE;
    private static final String TAG = "ConfigManage";

    private final String spName = "ganks_config";
    private final String key_banner_url = "keyBannerUrl";
    private final String key_launcher_img_show = "keyLauncherImgShow";
    private final String key_launcher_img_probability_show = "keyLauncherImgProbabilityShow";

    private String bannerURL;
    private boolean isShowLauncherImg;
    private boolean isProbabilityShowLauncherImg;

    public void initConfig(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // Banner URL 用于加载页显示
        bannerURL = sharedPreferences.getString(key_banner_url, "");
        // 启动页是否显示妹子图
        isShowLauncherImg = sharedPreferences.getBoolean(key_launcher_img_show, true);
        // 启动页是否概率出现
        isProbabilityShowLauncherImg = sharedPreferences.getBoolean(key_launcher_img_probability_show, false);
    }


    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        Log.d(TAG, "setBannerURL: "+bannerURL);
        SharedPreferences sharedPreferences = GanksApplication.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_banner_url, bannerURL);
        if (editor.commit()) {
            Log.d(TAG, "setBannerURL: commit");
            this.bannerURL = bannerURL;
        }
    }

    public boolean isShowLauncherImg() {
        return isShowLauncherImg;
    }

    public boolean isProbabilityShowLauncherImg() {
        return isProbabilityShowLauncherImg;
    }
}
