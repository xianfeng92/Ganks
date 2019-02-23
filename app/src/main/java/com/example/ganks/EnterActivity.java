package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;

import com.example.ganks.launcher.LauncherDelegate;
import com.example.ganks.ui.activitys.MainActivity;
import com.xforg.gank_core.app.Gank;
import com.example.ganks.launcher.ILauncherListener;
import com.example.ganks.launcher.OnLauncherFinishTag;
import com.xforg.gank_core.activitys.ProxyActivity;
import com.xforg.gank_core.delegates.GankDelegate;

public class EnterActivity extends ProxyActivity implements ILauncherListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gank.getConfigurator().withActivity(this);
    }

    @Override
    public GankDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                startActivity(new Intent(EnterActivity.this, MainActivity.class));
                break;
            case NOT_SIGNED:
                break;
                default:
                    break;
        }
    }
}
