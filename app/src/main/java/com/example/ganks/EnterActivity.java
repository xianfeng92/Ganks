package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.example.ganks.mvp.ui.activitys.ContentActivity;
import com.orhanobut.logger.Logger;
import com.xforg.gank_core.StatusBar.StatusBarUtil;
import com.example.ganks.delegates.GankDelegate;
import com.xforg.gank_core.utils.timer.BaseTimerTask;
import com.xforg.gank_core.utils.timer.ITimerListener;
import java.text.MessageFormat;
import java.util.Timer;
import butterknife.BindView;
import butterknife.OnClick;

public class EnterActivity extends AppCompatActivity implements GankDelegate.OnBackToFirstListener, ITimerListener {

    private Timer mTimer = null;
    private int mCount = 5;
    private AppCompatTextView mTvTimer = null;

    @BindView(R.id.tv_launcher_timer)
    public AppCompatTextView textView;

    @OnClick(R.id.tv_launcher_timer)
    void onClickTimerView(){
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            enterContentActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        StatusBarUtil.setStatusBarAndNavigationBarTranslucent(this);
        initTimer();
        mTvTimer = findViewById(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTimerView();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void enterContentActivity(){
        startActivity(new Intent(EnterActivity.this, ContentActivity.class));
        finish();
    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask,0,1000);
    }

    @Override
    public void onBackToFirstFragment() {
        finish();
    }

    @Override
    public void onTimer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.d("onTimer");
                if (mTvTimer != null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0){
                        enterContentActivity();
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });
    }
}
