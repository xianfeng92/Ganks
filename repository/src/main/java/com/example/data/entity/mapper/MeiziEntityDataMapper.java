package com.example.data.entity.mapper;

import com.example.data.entity.DaoMeiziEntity;
import com.example.domain.MeiziList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class MeiziEntityDataMapper {
    private MeiziEntityDataMapper(){}

    public static MeiziEntityDataMapper getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final MeiziEntityDataMapper INSTANCE = new MeiziEntityDataMapper();
    }

    public DaoMeiziEntity transformMeizi2Dao(MeiziList.Meizi meizi){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        DaoMeiziEntity daoGankEntity = new DaoMeiziEntity();
        daoGankEntity.id = meizi.id;
        daoGankEntity.createdAt = meizi.createdAt;
        daoGankEntity.desc = meizi.desc;
        daoGankEntity.url = meizi.url;
        daoGankEntity.type = meizi.type;
        daoGankEntity.addTime =str;
        return daoGankEntity;
    }
}
