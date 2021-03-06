package com.example.ganks.mvp.ui.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.domain.MeiziList;
import com.example.ganks.R;
import com.example.ganks.base.GanksApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.mvp.presenter.LoveMeiziPresenter;
import com.example.ganks.mvp.ui.adapter.LineAdapter;
import com.example.data.HelperImpl.GreenDaoHelperImpl;
import com.example.ganks.mvp.view.LoveMeiziView;
import com.orhanobut.logger.Logger;
import com.xforg.gank_core.utils.ToastUtils;
import java.util.List;

/**
 * Created By apple on 2019/2/24
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziFragment extends BaseDelegate<LoveMeiziPresenter> implements LoveMeiziView {

    public OnBackToFirstListener _mBackToFirstListener;
    private View notDataView;
    public RecyclerView recyclerView;
    public CoordinatorLayout coordinatorLayout;
    public LineAdapter mAdapter;
    public LinearLayoutManager mlayoutManager;

    public static LoveMeiziFragment newInstance() {
        Bundle args = new Bundle();
        LoveMeiziFragment loveMeiziFragment = new LoveMeiziFragment();
        loveMeiziFragment.setArguments(args);
        return loveMeiziFragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_meizi_love;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        recyclerView = rootView.findViewById(R.id.meizi_love_recycler);
        coordinatorLayout = rootView.findViewById(R.id.meizi_line_coordinatorLayout);
        mlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
        initializeInjector();
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("你没有喜欢的妹子哟");
            }
        });
    }

    @Override
    public void initData() {
        initItemDargAndSwipe();
        mPresenter.loadDataByGreenDao();
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
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.loadDataByGreenDao();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.d("onLazyInitView");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateAdapter(mPresenter.resultsBeanList);
    }

    private void initItemDargAndSwipe() {
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("onItemDragStart %d",pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Logger.d("onItemDragMoving");
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("onItemDragEnd");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }
        };
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("onItemSwipeStart %d",pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("clearView %d",pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.d("onItemSwiped %d",pos);
                GreenDaoHelperImpl.removeById(mPresenter.resultsBeanList.get(pos).id);
                mPresenter.loadDataByGreenDao();
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                //滑动中，可做一些操作
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.bg_gray));
                canvas.drawText("Don't you love me?", 50, 400, paint);
            }
        };

        mAdapter = new LineAdapter(R.layout.line_meizi_item, mPresenter.resultsBeanList);
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        // 设置item可滑动的方向：
        // ItemTouchHelper.START ------> 左滑
        // ItemTouchHelper.END   ------> 右滑
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START);
        mAdapter.enableSwipeItem();
        //监听item的滑动状况
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);
        // 添加列表加载动画
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置动画的执行
        mAdapter.isFirstOnly(false);
        mAdapter.setOnItemDragListener(listener);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = mPresenter.resultsBeanList.get(position).url;
//                mPresenter.downLoad(url);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setNewData(List<MeiziList.Meizi> mData) {
        updateAdapter(mData);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (_mBackToFirstListener != null){
            _mBackToFirstListener.onBackToFirstFragment();
        }
        return true;
    }

    private void updateAdapter(List<MeiziList.Meizi> resultsBeanList) {
        if (resultsBeanList.size() == 0) {
            mAdapter.setEmptyView(notDataView);
        }
        if (mAdapter == null) {
            mAdapter = new LineAdapter(R.layout.line_meizi_item, resultsBeanList);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNewData(resultsBeanList);
        }
    }

    private void initializeInjector(){
        GanksApplication.getApplicationComponent().contentActivityComponent().build().loveMeiziComponent().setLoveMeiziView(this).build().inject(this);
    }
}
