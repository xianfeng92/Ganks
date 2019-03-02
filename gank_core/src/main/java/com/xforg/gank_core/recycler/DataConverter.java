package com.xforg.gank_core.recycler;

import java.util.ArrayList;

/**
 * Created By apple on 2019/3/2
 * github: https://github.com/xianfeng92
 */
public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter serJsonData(String mJsonData){
        this.mJsonData = mJsonData;
        return this;
    }

    protected String getJsonData(){
        if (mJsonData == null || mJsonData.isEmpty()){
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITIES.clear();
    }
}
