package com.example.ganks.mvp.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.domain.MeiziList;
import com.example.ganks.R;
import com.xforg.easyimage.ImageLoader;
import com.xforg.easyimage.config.ConfigBuilder;
import com.xforg.easyimage.config.ImageConfig;
import java.util.List;

public class StaggerAdapter extends BaseQuickAdapter<MeiziList.Meizi, BaseViewHolder> {

    public StaggerAdapter(int layoutResId, List<MeiziList.Meizi> meizis){
        super(layoutResId,meizis);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziList.Meizi item) {
        ImageConfig config = new ConfigBuilder(mContext)
                .url(item.url)
                .placeHolder(R.mipmap.default_bg)
                .error(R.mipmap.fail_load)
                .build();
        ImageLoader.getActualLoader().apply(config).into((ImageView) helper.getView(R.id.iv));
    }
 }
