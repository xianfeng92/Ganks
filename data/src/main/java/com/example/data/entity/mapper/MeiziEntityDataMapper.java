package com.example.data.entity.mapper;

import com.example.data.entity.MeiziList;
import com.example.domain.Meizi;
import java.util.ArrayList;
import java.util.List;

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
