package com.example.data.entity.mapper;

import com.example.data.entity.DaoMeiziEntity;
import com.example.data.entity.MeiziList;
import com.example.domain.Meizi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class MeiziEntityDataMapper {

    private DaoMeiziEntity daoGankEntity;
    private Meizi meizi;

    private MeiziEntityDataMapper(){}

    public static MeiziEntityDataMapper getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final MeiziEntityDataMapper INSTANCE = new MeiziEntityDataMapper();
    }

    /**
     * Transform a List of {@link MeiziList} into a Collection of {@link Meizi}.
     */
    public List<Meizi> transform(MeiziList meiziList) {
        final List<Meizi> userList = new ArrayList<>(20);
        for (MeiziList.ResultsBean resultsBean : meiziList.results) {
            final Meizi meizi = transResult2Meizi(resultsBean);
            if (meizi != null) {
                userList.add(meizi);
            }
        }
        return userList;
    }

    public DaoMeiziEntity transformMeizi2Dao(Meizi meizi){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        if (daoGankEntity == null){
            daoGankEntity = new DaoMeiziEntity();
        }
        daoGankEntity._id = meizi.getId();
        daoGankEntity.createdAt = meizi.getCreatedAt();
        daoGankEntity.desc = meizi.getDesc();
        daoGankEntity.url = meizi.getUrl();
        daoGankEntity.type = meizi.getType();
        daoGankEntity.addTime =str;
        return daoGankEntity;
    }

    public Meizi transformDao2Meizi(DaoMeiziEntity daoMeiziEntity){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        if (meizi == null){
            meizi = new Meizi();
        }
        meizi.setUrl(daoMeiziEntity.url);
        meizi.setType(daoMeiziEntity.type);
        meizi.setDesc(daoMeiziEntity.desc);
        meizi.setId(daoMeiziEntity._id);
        meizi.setCreatedAt(daoMeiziEntity.createdAt);
        return meizi;
    }

    private Meizi transResult2Meizi(MeiziList.ResultsBean resultsBean) {
        Meizi meizi = null;
        if (resultsBean != null) {
            meizi = new Meizi();
            meizi.setId(resultsBean._id);
            meizi.setCreatedAt(resultsBean.createdAt);
            meizi.setDesc(resultsBean.desc);
            meizi.setType(resultsBean.type);
            meizi.setUrl(resultsBean.url);
        }
        return meizi;
    }

}
