package com.example.domain;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class Meizi {
        /**
         * _id : 595ad074421aa90ca3bb6a90
         * createdAt : 2017-07-04T07:17:08.609Z
         * desc : Android 有两套相机 Api，使用起来很麻烦，好在 Foto 开源了他们在 Android 上的 Camera 封装 Api，力荐！
         * images : ["http://img.gank.io/0a15bae7-c513-4feb-bbe2-1273b8b809ce"]
         * publishedAt : 2017-07-04T11:50:36.484Z
         * source : chrome
         * type : Android
         * url : https://github.com/Fotoapparat/Fotoapparat
         * used : true
         * who : 代码家
         */

        private String id;
        private String createdAt;
        private String desc;
        private String type;
        private String url;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
