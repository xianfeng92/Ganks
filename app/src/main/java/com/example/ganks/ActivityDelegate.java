package com.example.ganks;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created By apple on 2019/2/3
 * github: https://github.com/xianfeng92
 */
public interface ActivityDelegate extends Parcelable {
    String LAYOUT_LINEARLAYOUT = "LinearLayout";
    String LAYOUT_FRAMELAYOUT = "FrameLayout";
    String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle var1);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle var1);

    void onDestroy();
}
