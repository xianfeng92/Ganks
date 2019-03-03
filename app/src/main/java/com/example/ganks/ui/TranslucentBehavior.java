package com.example.ganks.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;


import com.example.ganks.R;
import com.xforg.gank_core.app.Gank;
import com.xforg.gank_core.recycler.RgbValue;

/**
 * Created By apple on 2019/3/3
 * github: https://github.com/xianfeng92
 */
@SuppressWarnings("unused")
public final class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //注意这个变量一定要定义成类变量
    private int mOffset = 0;
    //延长滑动过程
    private static final int MORE = 100;

    //顶部距离
    private int mDistanceY = 0;

    // 定义颜色的变化速度
    private static final int SHOW_SPEED = 3;

    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255,124,2);


    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, android.support.v7.widget.Toolbar child, View dependency) {
        return dependency.getId() == R.id.meizi_love_recycler;
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout
            , @NonNull android.support.v7.widget.Toolbar child
            , @NonNull View directTargetChild
            , @NonNull View target
            , int axes
            , int type) {
        return true;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        // 增加滑动距离
        mDistanceY +=dy;
        // ToolBar高度
        final int targetHeight = child.getBottom();

        if(mDistanceY > 0 && mDistanceY <= targetHeight){
            final float scale = mDistanceY / targetHeight;
            final float alpha = scale*255;
            child.setBackgroundColor(Color.argb((int) alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }else if( mDistanceY > targetHeight){
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }
    }

    @Override
    public void onNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout
            , @NonNull android.support.v7.widget.Toolbar toolbar
            , @NonNull View target
            , int dxConsumed
            , int dyConsumed
            , int dxUnconsumed
            , int dyUnconsumed
            , int type) {
        final int startOffset = 0;
        final Context context = Gank.getApplicationContext();
        final int endOffset = 80 + MORE;
        mOffset += dyConsumed;
        if (mOffset <= startOffset) {
            toolbar.getBackground().setAlpha(0);
        } else if (mOffset > startOffset && mOffset < endOffset) {
            final float percent = (float) (mOffset - startOffset) / endOffset;
            final int alpha = Math.round(percent * 255);
            toolbar.getBackground().setAlpha(alpha);
        } else if (mOffset >= endOffset) {
            toolbar.getBackground().setAlpha(255);
        }
    }
}
