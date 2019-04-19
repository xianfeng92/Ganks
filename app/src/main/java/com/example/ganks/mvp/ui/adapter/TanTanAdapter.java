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

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class TanTanAdapter extends BaseQuickAdapter<MeiziList.Meizi, BaseViewHolder> {

    public TanTanAdapter(int layoutResId, List<MeiziList.Meizi> resultsBeanList){
        super(layoutResId,resultsBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziList.Meizi item) {
        ImageConfig config = new ConfigBuilder(mContext)
                .url(item.url)
                .build();
        ImageLoader.getActualLoader().apply(config).into((ImageView) helper.getView(R.id.iv_avatar));
    }
}
