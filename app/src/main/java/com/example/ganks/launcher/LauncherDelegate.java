package com.example.ganks.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import com.example.ganks.R;
import com.example.ganks.sign.AccountManager;
import com.example.ganks.sign.IUserChecker;
import com.example.ganks.sign.SignUpDelegate;
import com.xforg.gank_core.delegates.GankDelegate;
import com.xforg.gank_core.utils.timer.BaseTimerTask;
import com.xforg.gank_core.utils.timer.ITimerListener;
import com.xforg.gank_core.utils.storage.GankPreference;
import java.text.MessageFormat;
import java.util.Timer;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
public class LauncherDelegate extends GankDelegate implements ITimerListener {

    private static final String TAG = "LauncherDelegate";

    private Timer mTimer = null;
    private int mCount = 5;
    private ILauncherListener mILauncherListener = null;
    private IUserChecker mIUserChecker = null;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener)context;
        }
        if (context instanceof IUserChecker){
            mIUserChecker = (IUserChecker)context;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Log.d(TAG, "onBindView: ");
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
          if (!AccountManager.isSignIn()){
              getSupportDelegate().start(new SignUpDelegate());
          }else {
              if (mIUserChecker != null){
                  mIUserChecker.onSignIn();
              }
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
