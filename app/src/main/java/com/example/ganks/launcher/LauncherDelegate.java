package com.example.ganks.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.ganks.R;
import com.example.ganks.delegate.BaseDelegate;
import com.example.ganks.timer.BaseTimerTask;
import com.example.ganks.timer.ITimerListener;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
public class LauncherDelegate extends BaseDelegate implements ITimerListener {

    private Timer mTimer = null;
    private int mCount = 5;

    @BindView(R.id.tv_launcher_timer)
    public AppCompatTextView textView;

    @OnClick(R.id.tv_launcher_timer)
    void OnClickTimerView(){

    }

    public void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask,0,5000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void OnBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(mCount);
                mCount--;
                if (mCount < 0){
                    if (mTimer != null){
                        mTimer.cancel();
                        mTimer = null;
                    }
                }
            }
        });
    }
}
