package ImageLoader;

import android.graphics.Bitmap;

/**
 * Created By zhongxianfeng on 19-3-14
 * github: https://github.com/xianfeng92
 */
public interface ImageDownLoadCallBack {

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
