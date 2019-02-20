package com.example.ganks.timer;

import java.util.TimerTask;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
