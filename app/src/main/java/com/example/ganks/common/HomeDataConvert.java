package com.example.ganks.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xforg.gank_core.recycler.DataConverter;
import com.xforg.gank_core.recycler.MultipleFields;
import com.xforg.gank_core.recycler.MultipleItemEntity;
import java.util.ArrayList;

/**
 * Created By apple on 2019/3/2
 * github: https://github.com/xianfeng92
 */
public class HomeDataConvert extends DataConverter {
    /**
     * HomeData（http://gank.io/api/data/Android/10/1）
     * 数据每个result的格式：
     *{"_id":"5c789ee19d212233d4b3da89",
     * "createdAt":"2019-03-01T02:54:25.522Z",
     * "desc":"Android\u6d88\u606f\u603b\u7ebf\u7684\u6f14\u8fdb\u4e4b\u8def\uff1a\u7528LiveDataBus\u66ff\u4ee3RxBus\u3001EventBus",
     * "images":["http://img.gank.io/48d7129b-ee08-4a5d-8eb8-bd626254eeae","http://img.gank.io/85c21024-8f3b-4899-91b6-95451a0f3b07"],
     * "publishedAt":"2019-03-01T03:13:05.882Z",
     * "source":"web","type":"Android",
     * "url":"https://tech.meituan.com/2018/07/26/android-livedatabus.html",
     * "used":true,
     * "who":"\u6f47\u6e58\u5251\u96e8"}
     */

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("results");
        final int size = dataArray.size();
        for (int i = 0; i<size;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final String desc = data.getString("desc");
            final String id = data.getString("_id");
            final String url = data.getString("url");
            final String createdAt = data.getString("createdAt");
            int type = 0;
            // 此处模拟有三种itemType
            if (i%3 == 0){
                type = 1;
            }else if(i%3 ==1){
                type = 2;
            }else {
                type = 3;
            }
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,desc)
                    .setField(MultipleFields.URL,url)
                    .setField(MultipleFields.TITLE,createdAt)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
