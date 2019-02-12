package com.example.ganks.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;

/**
 * Created By apple on 2019/2/3
 * github: https://github.com/xianfeng92
 */
public class ActivityDelegateImpl implements ActivityDelegate {

    private Activity mActivity;

    public ActivityDelegateImpl(Activity activity){
        this.mActivity = activity;
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>() {
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };

    @Override
    public void onCreate(Bundle var1) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle var1) {

    }

    @Override
    public void onDestroy() {
        if (mActivity != null){
            this.mActivity = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = (Activity)in.readParcelable(Activity.class.getClassLoader());
    }
}
