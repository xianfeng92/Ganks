package com.example.ganks.mvp.ui.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.domain.Meizi;
import com.example.ganks.R;
import com.example.ganks.base.BaseApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.internal.di.components.DaggerMeiziComponent;
import com.example.ganks.internal.di.modules.MeiziModule;
import com.example.ganks.mvp.contract.MeiziContract;
import com.example.ganks.mvp.presenter.MeiziPresenter;
import com.example.ganks.mvp.ui.adapter.StaggerAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeiziFragment extends BaseDelegate<MeiziPresenter> implements MeiziContract.View {

    public RecyclerView recyclerView;
    public CoordinatorLayout coordinatorLayout;
    public SwipeRefreshLayout swipeRefreshLayout;
    public StaggerAdapter mAdapter;
    public List<Meizi> meizis = new ArrayList<>();
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int lastVisibleItem;
    private ItemTouchHelper itemTouchHelper;
    private int page = 1;
    private int screenwidth;
    public BaseDelegate.OnBackToFirstListener _mBackToFirstListener;

    @Override
    public Object setLayout() {
        return R.layout.fragment_stagger;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        recyclerView = rootView.findViewById(R.id.stagger_recycleView);
        coordinatorLayout = rootView.findViewById(R.id.stagger_coordinatorLayout);
        swipeRefreshLayout = rootView.findViewById(R.id.stagger_swipe_refresh);
        screenwidth = ScreenUtils.getScreenWidth();
        initializeInjector();
    }

    public static MeiziFragment newInstance(){
        Logger.d("newInstance");
        Bundle args = new Bundle();
        MeiziFragment meiziFragment = new MeiziFragment();
        meiziFragment.setArguments(args);
        return meiziFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnBackToFirstListener){
            _mBackToFirstListener = (OnBackToFirstListener)context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void initData(){
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        swipeRefreshLayout.setProgressViewOffset(false,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.requestData(page);
            }
        });
        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0,swipeFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                    //设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                Collections.swap(meizis,from,to);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                viewHolder.itemView.setAlpha(1- Math.abs(dX)/screenwidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2 >= staggeredGridLayoutManager.getItemCount()) {
                    page++;
                    mPresenter.requestData(page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int[] positions= staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                //根据StaggeredGridLayoutManager设置的列数来定
                lastVisibleItem =Math.max(positions[0],positions[1]);
            }
        });
        mPresenter.requestData(page);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (_mBackToFirstListener != null){
            _mBackToFirstListener.onBackToFirstFragment();
        }
        return true;
    }

    @Override
    public void setNewData(List<Meizi> mData) {
        meizis.addAll(mData);
        updateAdapter(meizis);
    }

    private void updateAdapter(List<Meizi> meizis){
        if (mAdapter == null){
            mAdapter = new StaggerAdapter(R.layout.stagger_meizi_item,meizis);
            recyclerView.setAdapter(mAdapter);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }else {
            mAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initializeInjector() {
        DaggerMeiziComponent.builder()
                .applicationComponent(BaseApplication.getApplicationComponent())
                .meiziModule(new MeiziModule(this))
                .build()
                .inject(this);
    }
}
