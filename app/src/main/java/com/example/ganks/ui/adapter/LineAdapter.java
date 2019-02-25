package com.example.ganks.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.squareup.picasso.Picasso;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import java.util.List;


public class LineAdapter extends BaseItemDraggableAdapter<DaoMeiziEntity,BaseViewHolder> {

    public LineAdapter(int layoutResId,List<DaoMeiziEntity> resultsBeanList){
        super(layoutResId,resultsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoMeiziEntity item) {
        Picasso.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.line_item_iv));
    }
}
