package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ganks.launcher.LauncherDelegate;
import com.example.ganks.sign.ISignListener;
import com.example.ganks.sign.IUserChecker;
import com.example.ganks.ui.activitys.ContentActivity;
import com.xforg.gank_core.app.Gank;
import com.example.ganks.launcher.ILauncherListener;
import com.example.ganks.launcher.OnLauncherFinishTag;
import com.xforg.gank_core.activitys.ProxyActivity;
import com.xforg.gank_core.delegates.GankDelegate;


public class EnterActivity extends ProxyActivity implements ILauncherListener, ISignListener, IUserChecker {
    private static final String TAG = "EnterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gank.getConfigurator().withActivity(this);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public GankDelegate setRootDelegate() {
        Log.d(TAG, "setRootDelegate: ");
        return new LauncherDelegate();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                startActivity(new Intent(EnterActivity.this, ContentActivity.class));
                finish();
                break;
            case NOT_SIGNED:
                break;
                default:
                    break;
        }
    }

    @Override
    public void onSignIn() {
        startActivity(new Intent(EnterActivity.this, ContentActivity.class));
        finish();
    }

    @Override
    public void onNotSignIn() {

    }

    @Override
    public void onSignInSuccess() {
        startActivity(new Intent(EnterActivity.this, ContentActivity.class));
        finish();
    }

    @Override
    public void onSignUpSuccess() {

    }
}
