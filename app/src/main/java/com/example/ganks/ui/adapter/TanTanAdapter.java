package com.example.ganks.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.xforg.gank_core.entity.Meizi;
import java.util.List;
import ImageLoader.ImageLoader;
import ImageLoader.config.ConfigBuilder;
import ImageLoader.config.ImageConfig;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class TanTanAdapter extends BaseQuickAdapter<Meizi.ResultsBean, BaseViewHolder> {


    public TanTanAdapter(int layoutResId, List<Meizi.ResultsBean> resultsBeanList){
        super(layoutResId,resultsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Meizi.ResultsBean item) {
        ImageConfig config = new ConfigBuilder(mContext)
                .url(item.url)
                .build();
        ImageLoader.getActualLoader().apply(config).into((ImageView) helper.getView(R.id.iv_avatar));
    }
}
