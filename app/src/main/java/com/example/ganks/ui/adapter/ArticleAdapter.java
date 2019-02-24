package com.example.ganks.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ganks.R;
import com.xforg.gank_core.entity.Meizi;

import java.util.List;


public class ArticleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Meizi.ResultsBean> datas;
    private ItemClickListener itemClickListener;


    public ArticleAdapter(Context context, List<Meizi.ResultsBean> datas){
        this.context = context;
        this.datas = datas;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int postion);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.article_item,viewGroup,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (datas == null)
                return;
            ((MyViewHolder)viewHolder).tv.setText(datas.get(i).desc);
            ((MyViewHolder)viewHolder).iv.setImageResource(R.mipmap.android);
    }


    @Override
    public int getItemCount() {
        if (datas != null)
        return datas.size();
        else
            return 1;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv;
        private TextView tv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.ivImage);
            tv = itemView.findViewById(R.id.tvDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
