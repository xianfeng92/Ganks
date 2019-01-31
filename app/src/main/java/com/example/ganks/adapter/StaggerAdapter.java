package com.example.ganks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ganks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StaggerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "StaggerAdapter";

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
            Picasso.with(context).load(urls.get(i)).error(R.mipmap.fengjie).into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+urls.size());
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
