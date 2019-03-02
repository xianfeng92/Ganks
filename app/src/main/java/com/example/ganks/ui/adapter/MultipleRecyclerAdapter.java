package com.example.ganks.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ganks.R;
import com.xforg.gank_core.recycler.ItemType;
import com.xforg.gank_core.recycler.MultipleFields;
import com.xforg.gank_core.recycler.MultipleItemEntity;
import java.util.List;

/**
 * Created By apple on 2019/3/2
 * github: https://github.com/xianfeng92
 */
public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItemEntity item) {
        final String text;
        switch (helper.getItemViewType()){
            case ItemType.TEXT:
                text = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single,text);
                break;
            case ItemType.IMAGE:
                helper.setImageResource(R.id.img_single_1,R.mipmap.banner_1);
                helper.setImageResource(R.id.img_single_2,R.mipmap.banner_2);
                break;
            case ItemType.TEXT_IMAGE:
                text = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.tv_multiple,text);
                helper.setImageResource(R.id.img_multiple,R.mipmap.banner_3);
                break;
            default:
                break;
        }
    }

    private void init(){
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE,R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE,R.layout.item_multiple_image_text);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }
}
