package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;

import com.example.ganks.global.Gank;
import com.example.ganks.launcher.ILauncherListener;
import com.example.ganks.launcher.OnLauncherFinishTag;

public class EnterActivity extends ProxyActivity implements ILauncherListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gank.getConfigurator().withActivity(this);
    }

    @Override
    public LauncherDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                startActivity(new Intent(EnterActivity.this,MainActivity.class));
                break;
            case NOT_SIGNED:
                break;
                default:
                    break;
        }
    }
}
