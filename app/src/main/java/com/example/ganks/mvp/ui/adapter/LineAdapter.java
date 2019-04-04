package com.example.ganks.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.domain.Meizi;
import com.example.ganks.R;

import java.util.List;

import ImageLoader.ImageLoader;
import ImageLoader.config.ConfigBuilder;
import ImageLoader.config.ImageConfig;


public class LineAdapter extends BaseItemDraggableAdapter<Meizi,BaseViewHolder> {

    public LineAdapter(int layoutResId,List<Meizi> resultsBeanList){
        super(layoutResId,resultsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Meizi item) {
        ImageConfig config = new ConfigBuilder(mContext)
                .url(item.getUrl())
                .placeHolder(R.mipmap.default_bg)
                .error(R.mipmap.fail_load)
                .build();
        ImageLoader.getActualLoader().apply(config).into((ImageView) helper.getView(R.id.line_item_iv));
    }
}
