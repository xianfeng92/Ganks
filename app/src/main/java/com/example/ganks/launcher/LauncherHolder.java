package com.example.ganks.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public class LauncherHolder implements Holder<Integer> {

    private AppCompatTextView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatTextView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
