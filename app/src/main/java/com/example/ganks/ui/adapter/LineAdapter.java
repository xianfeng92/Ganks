package com.example.ganks.ui.adapter;

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
import com.xforg.gank_core.entity.DaoMeiziEntity;

import java.util.List;


public class LineAdapter extends RecyclerView.Adapter<LineAdapter.MyViewHolder> implements View.OnClickListener {

    private static final String TAG = "LineAdapter";

    private Context context;
    private List<DaoMeiziEntity> resultsBeanList;
    private onRecycleViewItemClickListener listener;
    private onItemClickListener onItemClickListener;


    public LineAdapter(Context context, List<DaoMeiziEntity> resultsBeanList){
        this.context = context;
        this.resultsBeanList = resultsBeanList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(context).inflate(R.layout.line_meizi_item,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: "+resultsBeanList.get(i).url);
        Picasso.with(context).load(resultsBeanList.get(i).url).into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return resultsBeanList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onItemClick(v);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.line_item_iv);
        }
    }

    public interface onRecycleViewItemClickListener{
        void onItemClick(View view);
    }

    public void setRecycleViewItemClickListener(onRecycleViewItemClickListener listener){
        this.listener = listener;
    }


//    public void addItem(Meizi.ResultsBean resultsBean, int position) {
//        resultsBeanList.add(position, resultsBean);
//        notifyItemInserted(position);
//    }

//    public void removeItem(final int position) {
//        final Meizi.ResultsBean removed=resultsBeanList.get(position);
//        resultsBeanList.remove(removed);
//        notifyItemRemoved(position);
//    }

    public interface onItemClickListener{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
