package com.example.data.entity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DaoMeiziEntity {

        /**
         * _id : 595ad074421aa90ca3bb6a90
         * createdAt : 2017-07-04T07:17:08.609Z
         * desc : Android 有两套相机 GanksApi，使用起来很麻烦，好在 Foto 开源了他们在 Android 上的 Camera 封装 GanksApi，力荐！
         * images : ["http://img.gank.io/0a15bae7-c513-4feb-bbe2-1273b8b809ce"]
         * publishedAt : 2017-07-04T11:50:36.484Z
         * source : chrome
         * type : Android
         * url : https://github.com/Fotoapparat/Fotoapparat
         * used : true
         * who : 代码家
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String type;
        public String url;
        public String addTime;

        @Generated(hash = 1383321709)
        public DaoMeiziEntity(String _id, String createdAt, String desc, String type, String url,
                              String addTime) {
            this._id = _id;
            this.createdAt = createdAt;
            this.desc = desc;
            this.type = type;
            this.url = url;
            this.addTime = addTime;
        }

        @Generated(hash = 7472386)
        public DaoMeiziEntity() {
        }

        public String get_id() {
            return this._id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAddTime() {
            return this.addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
}
