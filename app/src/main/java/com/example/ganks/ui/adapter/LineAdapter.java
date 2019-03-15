package com.example.ganks.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import java.util.List;

import ImageLoader.ImageLoader;
import ImageLoader.config.ConfigBuilder;
import ImageLoader.config.ImageConfig;


public class LineAdapter extends BaseItemDraggableAdapter<DaoMeiziEntity,BaseViewHolder> {

    public LineAdapter(int layoutResId,List<DaoMeiziEntity> resultsBeanList){
        super(layoutResId,resultsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoMeiziEntity item) {
        ImageConfig config = new ConfigBuilder(mContext)
                .url(item.url)
                .placeHolder(R.mipmap.default_bg)
                .error(R.mipmap.fail_load)
                .build();
        ImageLoader.getActualLoader().apply(config).into((ImageView) helper.getView(R.id.line_item_iv));
    }
}
