package com.example.ganks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ganks.R;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
              /*默认情况下，Picasso 内存缓存和磁盘缓存都开启了的，也就是加载图片的时候，内存和磁盘都缓存了
              但是有些时候，我们并不需要缓存，比如说：加载一张大图片的时候，如果再内存中保存一份，很容易造成OOM
              这时候我们只希望有磁盘缓存，而不希望缓存到内存，因此就需要我们设置缓存策略了。Picasso 提供了这样的方法
              memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)//跳过内存缓存
              networkPolicy(NetworkPolicy.NO_CACHE)//跳过磁盘缓存
              Picasso 有内存缓存和磁盘缓存，先从内存获取，没有再去磁盘缓存获取，都没有就从网络加载，网络加载是比较昂贵和耗时的
              Picasso让我们很容易就实现了。只需要调用一个方法setIndicatorsEnabled(boolean)就可以了,它会在图片的左上角出现一个带色块的三角形标示，有3种颜色，绿色表示从内存加载、蓝色表示从磁盘加载、红色表示从网络加载
              Picasso打印一些日志,App在加载图片的过程中，我们就可以从logcat 看到一些关键的日志信息。*/

//            Picasso picasso = Picasso.with(context);
//            picasso.setIndicatorsEnabled(true);
//            picasso.setLoggingEnabled(true);
//            picasso.load(urls.get(i)).placeholder(R.mipmap.default_bg).error(R.mipmap.fail_load).into(((MyViewHolder)viewHolder).imageView);


        /*
          Picasso 扩展
          用Builder 自己构造一个Picasso Instance
          */
        Picasso.Builder builder = new Picasso.Builder(context);

        //配置缓存
        LruCache cache = new LruCache(10*1024*1024);// 设置缓存大小
        builder.memoryCache(cache);

        //配置线程池
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(executorService);

        Picasso picasso = builder.build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(urls.get(i)).placeholder(R.mipmap.default_bg).error(R.mipmap.fail_load).into(((MyViewHolder)viewHolder).imageView);
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
