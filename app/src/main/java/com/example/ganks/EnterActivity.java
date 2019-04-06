package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;
import com.example.ganks.launcher.LauncherDelegate;
import com.example.ganks.sign.ISignListener;
import com.example.ganks.sign.IUserChecker;
import com.example.ganks.mvp.ui.activitys.ContentActivity;
import com.example.ganks.launcher.ILauncherListener;
import com.example.ganks.launcher.OnLauncherFinishTag;
import com.orhanobut.logger.Logger;
import com.xforg.gank_core.activitys.ProxyActivity;
import com.example.ganks.delegates.GankDelegate;
import org.greenrobot.eventbus.EventBus;


public class EnterActivity extends ProxyActivity implements
        ILauncherListener, ISignListener, IUserChecker, GankDelegate.OnBackToFirstListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MessageEvent<Integer> messageEvent = new MessageEvent<>();
        messageEvent.setMessage(110);
        EventBus.getDefault().post(messageEvent);
        loadRootFragment(R.id.enter,new LauncherDelegate());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        Logger.d("onLauncherFinish %s", tag);
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

    @Override
    public void onBackToFirstFragment() {
        finish();
    }
}
