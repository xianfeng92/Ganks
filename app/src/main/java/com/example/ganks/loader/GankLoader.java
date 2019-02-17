package com.example.ganks.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.ganks.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;
import com.wang.avi.indicators.BallClipRotateMultipleIndicator;

import java.util.ArrayList;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public class GankLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final Indicator DEFAULT_LOADER = new BallClipRotateMultipleIndicator();

    private static AppCompatDialog createDialog(
            Context context, AVLoadingIndicatorView avLoadingIndicatorView) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        int deviceWidth = ScreenUtils.getScreenWidth();
        int deviceHeight = ScreenUtils.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();
        dialog.setContentView(avLoadingIndicatorView);

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        return dialog;
    }

    public static void showLoading(Context context, Enum<LoadStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        avLoadingIndicatorView.setIndicator(type);
        createDialog(context, avLoadingIndicatorView).show();
    }

    public static void showLoading(Context context, Indicator indicator) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        avLoadingIndicatorView.setIndicator(indicator);
        createDialog(context, avLoadingIndicatorView).show();
    }


    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
        LOADERS.clear();
    }

}
