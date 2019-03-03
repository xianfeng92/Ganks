package com.xforg.gank_core.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created By apple on 2019/3/3
 * github: https://github.com/xianfeng92
 */
@AutoValue
public abstract class RgbValue {
    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
