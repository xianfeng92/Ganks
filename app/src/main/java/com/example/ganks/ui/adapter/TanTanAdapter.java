package com.example.ganks.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ganks.R;
import com.squareup.picasso.Picasso;
import com.xforg.gank_core.entity.Meizi;

import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class TanTanAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Meizi.ResultsBean> resultsBeanList;

    public TanTanAdapter(Context context, List<Meizi.ResultsBean> resultsBeanList){
        this.resultsBeanList = resultsBeanList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.tantan_item,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Picasso.with(context).load(resultsBeanList.get(i).url).error(R.mipmap.fail_load).into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return resultsBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
