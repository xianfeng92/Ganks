package com.example.ganks.Common;

import com.example.ganks.base.GanksApplication;
import java.io.File;

/**
 * Created By zhongxianfeng on 19-4-18
 * github: https://github.com/xianfeng92
 */
public class Constants {
    /**
     * Path
     */
    public static final String PATH_DATA = GanksApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
}
