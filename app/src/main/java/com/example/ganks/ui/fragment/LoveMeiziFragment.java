package com.example.ganks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ganks.R;
import com.example.ganks.ui.adapter.LineAdapter;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import com.xforg.gank_core.utils.GreenDaoHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019/2/24
 * github: https://github.com/xianfeng92
 */
public class LoveMeiziFragment extends BaseMainFragment implements LineAdapter.onRecycleViewItemClickListener {

    public RecyclerView recyclerView;
    public CoordinatorLayout coordinatorLayout;
    public SwipeRefreshLayout swipeRefreshLayout;
    public LineAdapter mAdapter;
    public LinearLayoutManager mlayoutManager;
    public List<DaoMeiziEntity> resultsBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi_love,container,false);
        recyclerView = view.findViewById(R.id.meizi_love_recycler);
        swipeRefreshLayout = view.findViewById(R.id.meizi_love_swipe_refresh);
        coordinatorLayout = view.findViewById(R.id.meizi_line_coordinatorLayout);
        init();
        loadDataByGreenDao();
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        loadDataByGreenDao();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (resultsBeanList.size() > 0){
            updateAdapter(resultsBeanList);
        }
    }

    public static LoveMeiziFragment newInstance(){
        Bundle args = new Bundle();
        LoveMeiziFragment loveMeiziFragment = new LoveMeiziFragment();
        loveMeiziFragment.setArguments(args);
        return loveMeiziFragment;
    }

    private void init(){
        mlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
    }

    private void loadDataByGreenDao(){
        resultsBeanList.clear();
        List<DaoMeiziEntity> list = GreenDaoHelper.getAllMeiziEntity();
        resultsBeanList.addAll(list);
        updateAdapter(resultsBeanList);
    }


    private void updateAdapter(List<DaoMeiziEntity> resultsBeanList){
        if (mAdapter == null){
            mAdapter = new LineAdapter(getActivity(),resultsBeanList);
            mAdapter.setRecycleViewItemClickListener(this);
            recyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onItemClick(View view) {
        Toast.makeText(getContext(),"OnItemClick",Toast.LENGTH_SHORT).show();
    }
}
