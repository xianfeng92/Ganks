package com.example.ganks.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.ganks.R;
import java.util.List;

import ImageLoader.ImageLoader;
import ImageLoader.config.ConfigBuilder;
import ImageLoader.config.ImageConfig;


public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private Context context;
    private List<String> urls;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public GridAdapter(Context context, List<String> urls){
        this.context = context;
        this.urls = urls;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.grid_meizi_item,viewGroup,false);
            MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImageConfig config = new ConfigBuilder(context)
                .url(urls.get(i))
                .placeHolder(R.mipmap.default_bg)
                .error(R.mipmap.fail_load)
                .build();
        ImageLoader.getActualLoader().apply(config).into(((MyViewHolder) viewHolder).iv);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageButton iv;

        MyViewHolder(View view)
        {
            super(view);
            iv = view.findViewById(R.id.iv);
        }
    }
}
