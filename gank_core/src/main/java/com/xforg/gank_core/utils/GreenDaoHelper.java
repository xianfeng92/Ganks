package com.xforg.gank_core.utils;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.ganks.greendao.DaoMaster;
import com.example.ganks.greendao.DaoMeiziEntityDao;
import com.example.ganks.greendao.DaoSession;
import com.xforg.gank_core.entity.DaoMeiziEntity;
import java.util.List;

/**
 * Created By apple on 2019/2/24
 * github: https://github.com/xianfeng92
 */
public class GreenDaoHelper {

    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static SQLiteDatabase sq;

    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     * @param application
     */
    public static void initDataBase(Application application){
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        devOpenHelper = new DaoMaster.DevOpenHelper(application,"meizi");
        sq = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(sq);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    public static SQLiteDatabase getSq(){
        return sq;
    }

    public static void insert(DaoMeiziEntity daoMeiziEntity){
        daoSession.getDaoMeiziEntityDao().insert(daoMeiziEntity);
    }

    public static void removeById(String id){
        daoSession.getDaoMeiziEntityDao()
                .queryBuilder()
                .where(DaoMeiziEntityDao.Properties._id.eq(id))
                .buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<DaoMeiziEntity> queryById(String id){
        List<DaoMeiziEntity> daoMeiziEntityList = daoSession.getDaoMeiziEntityDao()
                .queryBuilder()
                .where(DaoMeiziEntityDao.Properties._id.eq(id))
                .build().list();
        return daoMeiziEntityList;
    }

    public static Boolean isDaoContainMeizi(String id){
        List<DaoMeiziEntity> daoMeiziEntities = queryById(id);
        if (daoMeiziEntities.size() == 0){
            return false;
        }else {
            return true;
        }
    }

    public static List<DaoMeiziEntity> getAllMeiziEntity(){
        return daoSession.getDaoMeiziEntityDao().queryBuilder().list();
    }

}
