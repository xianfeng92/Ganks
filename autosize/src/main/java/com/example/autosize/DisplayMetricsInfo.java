package com.example.autosize;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public class DisplayMetricsInfo implements Parcelable {

    private float density;
    private int densityDpi;
    private float scaledDensity;

    public DisplayMetricsInfo(float density, int densityDpi, float scaledDensity) {
        this.density = density;
        this.densityDpi = densityDpi;
        this.scaledDensity = scaledDensity;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public float getScaledDensity() {
        return scaledDensity;
    }

    public void setScaledDensity(float scaledDensity) {
        this.scaledDensity = scaledDensity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.density);
        dest.writeInt(this.densityDpi);
        dest.writeFloat(this.scaledDensity);
    }

    protected DisplayMetricsInfo(Parcel in) {
        this.density = in.readFloat();
        this.densityDpi = in.readInt();
        this.scaledDensity = in.readFloat();
    }

    public static final Creator<DisplayMetricsInfo> CREATOR = new Creator<DisplayMetricsInfo>() {
        @Override
        public DisplayMetricsInfo createFromParcel(Parcel source) {
            return new DisplayMetricsInfo(source);
        }

        @Override
        public DisplayMetricsInfo[] newArray(int size) {
            return new DisplayMetricsInfo[size];
        }
    };

    @Override
    public String toString() {
        return "DisplayMetricsInfo{" +
                "density=" + density +
                ", densityDpi=" + densityDpi +
                ", scaledDensity=" + scaledDensity +
                '}';
    }
}
