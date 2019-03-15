package ImageLoader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestBuilder;

import ImageLoader.config.ImageConfig;

/**
 * Created By zhongxianfeng on 19-3-14
 * github: https://github.com/xianfeng92
 */
public interface ILoader {

    void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD);

    RequestBuilder apply(ImageConfig config);

    void pause();

    void resume();

    void clearDiskCache();

    void clearMomoryCache(View view);

    void clearMomory();

    boolean  isCached(String url);

    void trimMemory(int level);

    void clearAllMemoryCaches();

    void saveImageIntoGallery(DownLoadImageService downLoadImageService);
}
