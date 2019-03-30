package com.example.ganks.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.xforg.gank_core.recycler.MultipleFields;
import com.xforg.gank_core.recycler.MultipleItemEntity;

import java.util.List;


public class ArticleAdapter extends BaseItemDraggableAdapter<MultipleItemEntity, BaseViewHolder> {

    public ArticleAdapter(int layoutResId, List<MultipleItemEntity> datas){
        super(layoutResId,datas);
    }


    @Override
    protected void convert(BaseViewHolder helper, MultipleItemEntity item) {
        helper.setText(R.id.tvDesc, (Integer) item.getField(MultipleFields.TITLE));
        helper.setImageResource(R.id.ivImage,R.mipmap.android);
    }

}
