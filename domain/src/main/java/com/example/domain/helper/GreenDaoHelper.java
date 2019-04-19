package com.example.domain.helper;

import com.example.domain.MeiziList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-4-19
 * github: https://github.com/xianfeng92
 */
public interface GreenDaoHelper {

    List<MeiziList.Meizi> getAllMeiziEntity();

    void addToFavorite(MeiziList.Meizi meizi);
}
