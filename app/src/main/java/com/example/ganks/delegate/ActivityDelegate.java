package com.example.ganks.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created By apple on 2019/2/3
 * github: https://github.com/xianfeng92
 */
public interface ActivityDelegate extends Parcelable {

    void onCreate(Bundle var1);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle var1);

    void onDestroy();
}
