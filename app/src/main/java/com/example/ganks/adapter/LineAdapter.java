package com.example.ganks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganks.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class LineAdapter extends RecyclerView.Adapter<LineAdapter.MyViewHolder> implements View.OnClickListener {


    private Context context;
    private List<String> urls;
    private onRecycleViewItemClickListener listener;

    public LineAdapter(Context context, List<String> urls){
        this.context = context;
        this.urls = urls;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.line_meizi_item,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.textView.setText("picture"+i);
        Picasso.with(context).load(urls.get(i)).into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onItemClick(v);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.line_item_iv);
            textView = itemView.findViewById(R.id.line_item_tv);
        }
    }

    public interface onRecycleViewItemClickListener{
        void onItemClick(View view);
    }

    public void setRecycleViewItemClickListener(onRecycleViewItemClickListener listener){
        this.listener = listener;
    }


    public void addItem(String url, int position) {
        urls.add(position, url);
        notifyItemInserted(position);
    }

    public void removeItem(final int position) {
        final String removed=urls.get(position);
        urls.remove(urls.get(position));
        notifyItemRemoved(position);
    }
}
