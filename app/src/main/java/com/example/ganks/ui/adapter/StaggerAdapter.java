package com.example.ganks.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ganks.R;
import java.util.List;
import ImageLoader.ImageLoader;
import ImageLoader.config.ConfigBuilder;
import ImageLoader.config.ImageConfig;

public class StaggerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> urls;
    private onItemClickListener onItemClickListener;

    public StaggerAdapter(Context context, List<String> urls){
        this.context = context;
        this.urls = urls;
    }


    public interface onItemClickListener{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(StaggerAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.stagger_meizi_item,viewGroup,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImageConfig config = new ConfigBuilder(context)
                .url(urls.get(i))
                .placeHolder(R.mipmap.default_bg)
                .error(R.mipmap.fail_load)
                .build();
        ImageLoader.getActualLoader().apply(config).into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,getPosition());
            }
        }
    }
 }
