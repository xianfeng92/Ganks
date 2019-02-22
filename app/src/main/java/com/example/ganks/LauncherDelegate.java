package com.example.ganks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.ganks.delegate.GankDelegate;
import com.example.ganks.launcher.ILauncherListener;
import com.example.ganks.launcher.LauncherScrollDelegate;
import com.example.ganks.launcher.OnLauncherFinishTag;
import com.example.ganks.launcher.ScrollLauncherTag;
import com.example.ganks.timer.BaseTimerTask;
import com.example.ganks.timer.ITimerListener;
import com.example.ganks.utils.GankPreference;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
public class LauncherDelegate extends GankDelegate implements ITimerListener {

    private Timer mTimer = null;
    private int mCount = 5;
    private ILauncherListener mILauncherListener = null;
    private AppCompatTextView mTvTimer = null;

    @BindView(R.id.tv_launcher_timer)
    public AppCompatTextView textView;

    @OnClick(R.id.tv_launcher_timer)
    void onClickTimerView(){
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    public void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask,0,1000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener)activity;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTimer();
        mTvTimer = rootView.findViewById(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTimerView();
            }
        });
    }

    //判断是否显示滑动启动页
    private void checkIsShowScroll(){
        if (!GankPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            getSupportDelegate().start(new LauncherScrollDelegate(),SINGLETASK);
        }else{
            // do something else
            if (mILauncherListener != null){
                mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
            }
        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0){
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
