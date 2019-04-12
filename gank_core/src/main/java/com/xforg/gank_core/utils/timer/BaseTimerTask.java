package com.xforg.gank_core.utils.timer;

import java.util.TimerTask;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener;

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
