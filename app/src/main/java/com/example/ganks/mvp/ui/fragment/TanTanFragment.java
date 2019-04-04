package com.example.ganks.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;
import com.example.domain.Meizi;
import com.example.ganks.R;
import com.example.ganks.base.BaseApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.internal.di.components.DaggerTanTanComponent;
import com.example.ganks.internal.di.modules.TanTanModule;
import com.example.ganks.mvp.contract.TanTanContract;
import com.example.ganks.mvp.presenter.TanTanPresenter;
import com.example.ganks.mvp.ui.adapter.TanTanAdapter;
import com.example.tantancardswipe.CardConfig;
import com.example.tantancardswipe.OnSwipeListener;
import com.example.tantancardswipe.CardItemTouchHelperCallback;
import com.example.tantancardswipe.CardLayoutManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class TanTanFragment extends BaseDelegate<TanTanPresenter> implements TanTanContract.View {

    private static final String TAG = "TanTanFragment";
    private TanTanAdapter tanTanAdapter;
    private CardItemTouchHelperCallback cardItemTouchHelperCallback;
    private CardLayoutManager cardLayoutManager;
    private RecyclerView recyclerView;
    private List<Meizi> resultsBeanList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> urls = new ArrayList<>();
    private int page = 1;

    public OnBackToFirstListener _mBackToFirstListener;

    public static TanTanFragment newInstance(){
        TanTanFragment tanTanFragment = new TanTanFragment();
        return tanTanFragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.layout_refresh_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        initializeInjector();
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
        tanTanAdapter = new TanTanAdapter(R.layout.tantan_item,resultsBeanList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tanTanAdapter);
        cardItemTouchHelperCallback = new CardItemTouchHelperCallback(tanTanAdapter,resultsBeanList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cardItemTouchHelperCallback);
        cardLayoutManager = new CardLayoutManager(recyclerView,itemTouchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        cardItemTouchHelperCallback.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                View toastView = getLayoutInflater().inflate(R.layout.mytoast, null);
                Toast toast = new Toast(getContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                if (direction == CardConfig.SWIPED_LEFT){
                    toastView.setBackgroundResource(R.mipmap.img_dislike);
                }else {
                    toastView.setBackgroundResource(R.mipmap.img_like);
//                    mPresenter.addToFavorites(o);
                }
                toast.setView(toastView);
                toast.show();
            }

            @Override
            public void onSwipedClear() {
                mPresenter.requestData(page);
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
        if (resultsBeanList != null){
            resultsBeanList.addAll(mData);
        }
    }

    private void initializeInjector(){
        DaggerTanTanComponent.builder()
                .applicationComponent(BaseApplication.getApplicationComponent())
                .tanTanModule(new TanTanModule(this))
                .build()
                .inject(this);
    }
}
