package ImageLoader.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

import java.io.File;

import ImageLoader.utils.ImageUtil;

/**
 * Created By zhongxianfeng on 19-3-14
 * github: https://github.com/xianfeng92
 */
public class ImageConfig {

    private Context context;
    private boolean ignoreCertificateVerify; //https是否忽略校验
    private String url;
    private float thumbnail; //缩略图缩放倍数
    private String filePath; //文件路径
    private File file; //文件路径
    private int resId;  //资源id
    private String rawPath;  //raw路径
    private String assertspath;  //asserts路径
    private String contentProvider; //内容提供者
    private View target;
    private int width;
    private int height;
    private int oWidth;
    private int oHeight;
    private boolean asBitMap;
    private boolean asGif;
    private boolean isNeedOriginalSize;

    //滤镜
    private boolean isNeedVignette; //是否需要晕映
    private boolean isNeedSketch; //是否需要素描
    private float pixelationLevel; //是否需要马赛克
    private boolean isNeedPixelation; //是否需要马赛克
    private boolean isNeedInvert; //是否需要胶片
    private float contrastLevel;  //锐化等级
    private boolean isNeedContrast; //是否需要锐化
    private boolean isNeedSepia; //是否需要墨画
    private boolean isNeedToon; //是否需要油画
    private boolean isNeedSwirl;  // 是否需要漩涡
    private boolean isNeedGrayscale; //是否需要黑色
    private boolean isNeedBrightness; //是否需要亮度
    private float brightnessLeve; //是否需要亮度
    private boolean needBlur;//是否需要模糊
    private boolean needFilteColor;//是否需要模糊
    private int filteColor;
    private int priority;

    private int blurRadius;
    private int placeHolderResId;
    private int errorResId;

    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径
    private DiskCacheStrategy diskCacheStrategy;//是否跳过磁盘存储
    private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

    private BitmapListener bitmapListener;
    private RequestListener requestListener;

    public ImageConfig(ConfigBuilder builder) {
        this.isNeedOriginalSize = builder.isNeedOriginalSize;
        this.asBitMap = builder.asBitmap;
        this.asGif = builder.asGif;
        this.url = builder.url;
        this.thumbnail = builder.thumbnail;
        this.filePath = builder.filePath;
        this.file = builder.file;
        this.resId = builder.resId;
        this.rawPath = builder.rawPath;
        this.assertspath = builder.assertspath;
        this.contentProvider = builder.contentProvider;

        this.ignoreCertificateVerify = builder.ignoreCertificateVerify;

        this.width = builder.width;
        this.height = builder.height;

        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;

        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.scaleMode = builder.scaleMode;

        this.diskCacheStrategy = builder.diskCacheStrategy;

        this.priority = builder.priority;
        //滤镜
        this.isNeedVignette = builder.isNeedVignette; //是否需要晕映
        this.isNeedSketch = builder.isNeedSketch; //是否需要素描
        this.pixelationLevel = builder.pixelationLevel; //是否需要马赛克
        this.isNeedPixelation = builder.isNeedPixelation; //是否需要马赛克
        this.isNeedInvert = builder.isNeedInvert; //是否需要胶片
        this.contrastLevel = builder.contrastLevel; //锐化等级
        this.isNeedContrast = builder.isNeedContrast; //是否需要锐化
        this.isNeedSepia = builder.isNeedSepia; //是否需要亮度
        this.isNeedToon = builder.isNeedToon; //是否需要亮度
        this.isNeedSwirl = builder.isNeedSwirl; //是否需要亮度
        this.isNeedGrayscale = builder.isNeedGrayscale; //是否需要黑色
        this.isNeedBrightness = builder.isNeedBrightness; //是否需要亮度
        this.brightnessLeve = builder.brightnessLeve; //是否需要亮度
        this.filteColor = builder.filteColor;
        this.needBlur = builder.needBlur;
        this.needFilteColor = builder.needFilteColor;
        this.placeHolderResId = builder.placeHolderResId;
        this.blurRadius = builder.blurRadius;
        this.errorResId = builder.errorResId;
        this.requestListener = builder.requestListener;
    }


    public Context getContext() {
        if (context == null) {
            context = GlobalConfig.context;
        }
        return context;
    }

    public RequestListener getRequestListener(){
        return requestListener;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public String getContentProvider() {
        return contentProvider;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isAsBitMap(){
        return asBitMap;
    }

    public boolean isAsGif(){
        return asGif;
    }

    public File getFile() {
        return file;
    }

    public boolean isNeedBlur() {
        return needBlur;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    public int getResId() {
        return resId;
    }

    public String getRawPath() {
        return rawPath;
    }

    public String getAssertspath() {
        return assertspath;
    }

    public int getScaleMode() {
        return scaleMode;
    }

    public int getShapeMode() {
        return shapeMode;
    }

    public View getTarget() {
        return target;
    }

    public String getUrl() {
        return url;
    }

    public int getHeight() {
        if (height <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                height = target.getMeasuredWidth();
            }
            if (height <= 0) {
                height = GlobalConfig.getWinHeight();
            }
        }
        return height;
    }

    public int getWidth() {
        if (width <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                width = target.getMeasuredWidth();
            }
            if (width <= 0) {
                width = GlobalConfig.getWinWidth();
            }
        }

        return width;
    }

    public int getoWidth() {
        return oWidth;
    }

    public int getoHeight() {
        return oHeight;
    }

    public int getPriority() {
        return priority;
    }

    public int getFilteColor() {
        return filteColor;
    }

    public float getContrastLevel() {
        return contrastLevel;
    }

    public boolean isNeedFilteColor() {
        return needFilteColor;
    }

    public float getBrightnessLeve() {
        return brightnessLeve;
    }

    public boolean isNeedBrightness() {
        return isNeedBrightness;
    }

    public boolean isIgnoreCertificateVerify() {
        return ignoreCertificateVerify;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public void setBitmapListener(BitmapListener bitmapListener) {
        this.bitmapListener = ImageUtil.getBitmapListenerProxy(bitmapListener);
    }

    public void show() {
        GlobalConfig.getLoader().apply(this);
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public boolean isNeedGrayscale() {
        return isNeedGrayscale;
    }

    public boolean isNeedSwirl() {
        return isNeedSwirl;
    }

    public boolean isNeedToon() {
        return isNeedToon;
    }

    public boolean isNeedSepia() {
        return isNeedSepia;
    }

    public boolean isNeedContrast() {
        return isNeedContrast;
    }

    public boolean isNeedInvert() {
        return isNeedInvert;
    }

    public boolean isNeedPixelation() {
        return isNeedPixelation;
    }

    public float getPixelationLevel() {
        return pixelationLevel;
    }

    public boolean isNeedSketch() {
        return isNeedSketch;
    }

    public boolean isNeedVignette() {
        return isNeedVignette;
    }

    public boolean isNeedOriginalSize(){
        return isNeedOriginalSize;
    }

    public BitmapListener getBitmapListener() {

        return bitmapListener;
    }

    public interface BitmapListener {
        void onSuccess(Bitmap bitmap);
        void onFail();
    }
}
