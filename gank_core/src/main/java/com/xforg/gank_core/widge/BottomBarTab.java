package com.xforg.gank_core.widge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xforg.gank_core.R;


/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public class BottomBarTab extends FrameLayout {

    private ImageView mICon;
    private Context mContext;
    private int mTabPostion = -1;

    public BottomBarTab(Context context, @DrawableRes int icon) {
        this(context, null, icon);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon) {
        this(context, attrs, 0, icon);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon) {
        super(context, attrs, defStyleAttr);
        init(context, icon);
    }

    private void init(Context context, int icon){
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        mICon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,27,getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size,size);
        params.gravity = Gravity.CENTER;
        mICon.setImageResource(icon);
        mICon.setLayoutParams(params);
        mICon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        addView(mICon);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected){
            mICon.setColorFilter(ContextCompat.getColor(mContext,R.color.colorPrimary));
        }else {
            mICon.setColorFilter(ContextCompat.getColor(mContext,R.color.tab_unselect));
        }
    }

    public void setTabPostion(int postion){
        mTabPostion = postion;
        if (postion == 0){
            setSelected(true);
        }
    }

    public int getTabPostion(){
        return mTabPostion;
    }
}
