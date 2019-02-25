package com.example.ganks.ui.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.xforg.gank_core.entity.Meizi;

import java.util.List;


public class ArticleAdapter extends BaseItemDraggableAdapter<Meizi.ResultsBean, BaseViewHolder> {

    public ArticleAdapter(int layoutResId, List<Meizi.ResultsBean> datas){
        super(layoutResId,datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, Meizi.ResultsBean item) {
        helper.setText(R.id.tvDesc,item.desc);
        helper.setImageResource(R.id.ivImage,R.mipmap.android);
    }
}
