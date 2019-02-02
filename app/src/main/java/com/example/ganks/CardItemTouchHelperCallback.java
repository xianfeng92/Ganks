package com.example.ganks;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.ganks.listener.OnSwipeListener;

import java.util.List;


/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class CardItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private RecyclerView.Adapter adapter;
    private List<String> dataList;
    private OnSwipeListener onSwipeListener;

    public CardItemTouchHelperCallback(RecyclerView.Adapter adapter, List<String> datas){
        this.adapter = adapter;
        this.dataList = datas;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            itemView.setRotation(ratio * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();
            // 当数据源个数大于最大显示数时
            if (childCount > me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE + Math.abs(ratio) * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE + Math.abs(ratio) * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / me.yuqirong.cardswipelayout.CardConfig.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE + Math.abs(ratio) * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE + Math.abs(ratio) * me.yuqirong.cardswipelayout.CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / me.yuqirong.cardswipelayout.CardConfig.DEFAULT_TRANSLATE_Y);
                }
            }
            if (onSwipeListener != null) {
                if (ratio != 0) {
                    onSwipeListener.onSwiping(viewHolder, ratio, ratio < 0 ? me.yuqirong.cardswipelayout.CardConfig.SWIPING_LEFT : me.yuqirong.cardswipelayout.CardConfig.SWIPING_RIGHT);
                } else {
                    onSwipeListener.onSwiping(viewHolder, ratio, me.yuqirong.cardswipelayout.CardConfig.SWIPING_NONE);
                }
            }
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardLayoutManager) {
            // 对于 swipeFlags 只关心左右两个方向
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    //对于 dragFlags 配置的是 0,onMove直接返回 false
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // 移除之前设置的 onTouchListener, 否则触摸滑动会乱了
        viewHolder.itemView.setOnClickListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();
        String remove = dataList.remove(layoutPosition);
        adapter.notifyDataSetChanged();
        // 卡片滑出后回调 OnSwipeListener 监听器
        if (onSwipeListener != null) {
            onSwipeListener.onSwiped(viewHolder, remove, i == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
        }
        // 当没有数据时回调 OnSwipeListener 监听器
        if (adapter.getItemCount() == 0) {
            if (onSwipeListener != null) {
                onSwipeListener.onSwipedClear();
            }
        }
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

}
