package com.example.ganks.ui.fragment;

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
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.ganks.R;
import com.example.ganks.adapter.StaggerAdapter;
import com.example.ganks.entity.Meizi;
import com.example.ganks.net.RestCreator;
import com.example.ganks.net.RestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MeiziFragment extends BaseMainFragment implements StaggerAdapter.onItemClickListener {

    public RecyclerView recyclerView;
    public CoordinatorLayout coordinatorLayout;
    public SwipeRefreshLayout swipeRefreshLayout;

    public StaggerAdapter mAdapter;
    public List<String> urls = new ArrayList<>();
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int lastVisibleItem;
    private ItemTouchHelper itemTouchHelper;
    private RestService meiziService;
    private int page = 1;
    private int screenwidth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stagger,container,false);
        recyclerView = view.findViewById(R.id.stagger_recycleView);
        coordinatorLayout = view.findViewById(R.id.stagger_coordinatorLayout);
        swipeRefreshLayout = view.findViewById(R.id.stagger_swipe_refresh);
        meiziService = RestCreator.getRestService();
        screenwidth = ScreenUtils.getScreenWidth();
        return view;
    }

    public static MeiziFragment newInstance(){
        Bundle args = new Bundle();
        MeiziFragment meiziFragment = new MeiziFragment();
        meiziFragment.setArguments(args);
        return meiziFragment;
    }

    private void updateAdapter(List<String> urls){
        if (mAdapter == null){
            mAdapter = new StaggerAdapter(getActivity(),urls);
            mAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(mAdapter);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }else {
            mAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(getContext(),"OnItemClick"+postion+" "+"url is "+urls.get(postion),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        getMeizi();
    }

    private void initData(){
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        swipeRefreshLayout.setProgressViewOffset(false,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getMeizi();
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
                Collections.swap(urls,from,to);
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
                    getMeizi();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int[] positions= staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                //根据StaggeredGridLayoutManager设置的列数来定
                lastVisibleItem =Math.max(positions[0],positions[1]);
            }
        });
    }


    private void getMeizi(){
        meiziService.getMeizi(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Meizi>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Meizi meizis) {
                for (Meizi.ResultsBean resultsBean:meizis.results){
                    if (!TextUtils.isEmpty(resultsBean.url))
                        urls.add(resultsBean.url);
                }
                updateAdapter(urls);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
