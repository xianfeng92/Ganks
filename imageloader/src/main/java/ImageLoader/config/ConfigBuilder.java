package ImageLoader.config;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

import java.io.File;

import ImageLoader.utils.ImageUtil;

/**
 * Created By zhongxianfeng on 19-3-14
 * github: https://github.com/xianfeng92
 */
public  class ConfigBuilder {

    protected Context context;
    boolean ignoreCertificateVerify = GlobalConfig.ignoreCertificateVerify;

    /**
     * 图片源
     * 类型	SCHEME	示例
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     * 本地文件	file://	FileInputStream
     * Content provider	content://	ContentResolver
     * asset目录下的资源	asset://	AssetManager
     * res目录下的资源	  res://	Resources.openRawResource
     * Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
     *
     * @param config
     * @return
     */
    protected String url;
    protected float thumbnail;
    protected String filePath;
    protected File file;
    protected int resId;
    protected String rawPath;
    protected String assertspath;
    protected String contentProvider;

    protected ImageConfig.BitmapListener bitmapListener;

    protected int width;
    protected int height;

    protected int oWidth; //选择加载分辨率的宽
    protected int oHeight; //选择加载分辨率的高

    protected boolean isNeedOriginalSize = false;

    //滤镜
    protected boolean isNeedVignette; //是否需要晕映
    protected boolean isNeedSketch; //是否需要素描
    protected float pixelationLevel; // 马赛克级别
    protected boolean isNeedPixelation; //是否需要马赛克
    protected boolean isNeedInvert; //是否需要胶片
    protected float contrastLevel; //是否需要墨画
    protected boolean isNeedContrast = false; //是否需要墨画
    protected boolean isNeedSepia = false; //是否需要墨画
    protected boolean isNeedToon = false; //是否需要油画
    protected boolean isNeedSwirl = false; //是否需要漩涡
    protected boolean isNeedGrayscale = false; //是否需要亮度
    protected boolean isNeedBrightness = false; //是否需要亮度
    protected float brightnessLeve; //亮度等级
    protected boolean needBlur = false;//是否需要模糊
    protected boolean needFilteColor = false;//是否需要滤镜颜色
    protected int blurRadius;
    protected boolean asBitmap = false;
    protected  boolean asGif = false;

    //UI:
    protected int placeHolderResId;
    protected int errorResId;

    protected int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    protected int rectRoundRadius;//圆角矩形时圆角的半径

    protected DiskCacheStrategy diskCacheStrategy;

    protected int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

    protected int priority; //请求优先级

    protected int filteColor; //滤镜颜色

    protected RequestListener requestListener;

    public ConfigBuilder(boolean isNeedOriginalSize, boolean asBitmap, boolean asGif, String url, float thumbnail, String filePath, File file, int resId, String rawPath, String assertspath,
                         String contentProvider, boolean ignoreCertificateVerify, int width, int height, int oWidth,
                         int oHeight, int shapeMode, int scaleMode, DiskCacheStrategy diskCacheStrategy, int priority,
                         boolean isNeedVignette, boolean isNeedSketch, float pixelationLevel, boolean isNeedPixelation,
                         boolean isNeedInvert, float contrastLevel, boolean isNeedContrast, boolean isNeedSepia,
                         boolean isNeedToon, boolean isNeedSwirl, boolean isNeedGrayscale, boolean isNeedBrightness,
                         float brightnessLeve, int filteColor, boolean needBlur, boolean needFilteColor, int placeHolderResId,
                         ImageConfig.BitmapListener bitmapListener, int blurRadius, int errorResId,RequestListener requestListener) {
        this.isNeedOriginalSize = isNeedOriginalSize;
        this.asBitmap = asBitmap;
        this.asGif = asGif;
        this.url = url;
        this.thumbnail = thumbnail;
        this.filePath = filePath;
        this.file = file;
        this.resId = resId;
        this.rawPath = rawPath;
        this.assertspath = assertspath;
        this.contentProvider = contentProvider;
        this.ignoreCertificateVerify = ignoreCertificateVerify;
        this.width = width;
        this.height = height;
        this.oWidth = oWidth;
        this.oHeight = oHeight;
        this.shapeMode = shapeMode;
        this.scaleMode = scaleMode;
        this.diskCacheStrategy = diskCacheStrategy;
        this.priority = priority;
        this.isNeedVignette = isNeedVignette;
        this.isNeedSketch = isNeedSketch;
        this.pixelationLevel = pixelationLevel;
        this.isNeedPixelation = isNeedPixelation;
        this.isNeedInvert = isNeedInvert;
        this.contrastLevel = contrastLevel;
        this.isNeedContrast = isNeedContrast;
        this.isNeedSepia = isNeedSepia;
        this.isNeedToon = isNeedToon;
        this.isNeedSwirl = isNeedSwirl;
        this.isNeedGrayscale = isNeedGrayscale;
        this.isNeedBrightness = isNeedBrightness;
        this.brightnessLeve = brightnessLeve;
        this.filteColor = filteColor;
        this.needBlur  = needBlur;
        this.needFilteColor = needFilteColor;
        this.placeHolderResId = placeHolderResId;
        this.bitmapListener = bitmapListener;
        this.blurRadius = blurRadius;
        this.errorResId = errorResId;
        this.requestListener = requestListener;
    }



    public ConfigBuilder(Context context) {
        this.context = context;
    }

    public ConfigBuilder ignoreCertificateVerify(boolean ignoreCertificateVerify) {
        this.ignoreCertificateVerify = ignoreCertificateVerify;
        return this;
    }

    /**
     * 缩略图
     *
     * @param thumbnail
     * @return
     */
    public ConfigBuilder thumbnail(float thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public ConfigBuilder asBitmap(){
        this.asBitmap = true;
        return this;
    }


    public ConfigBuilder asGif(){
        this.asGif = true;
        return this;
    }

    /**
     * error图
     *
     * @param errorResId
     * @return
     */
    public ConfigBuilder error(int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    /**
     * 设置网络路径
     *
     * @param url
     * @return
     */
    public ConfigBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 加载SD卡资源
     *
     * @param filePath
     * @return
     */
    public ConfigBuilder file(String filePath) {
        if (filePath.startsWith("file:")) {
            this.filePath = filePath;
            return this;
        }

        if (!new File(filePath).exists()) {
            //throw new RuntimeException("文件不存在");
            Log.d("imageloader", "文件不存在");
            return this;
        }
        this.filePath = filePath;
        return this;
    }

    /**
     * 加载SD卡资源
     *
     * @param file
     * @return
     */
    public ConfigBuilder file(File file) {
        this.file = file;
        return this;
    }

    /**
     * 加载drawable资源
     *
     * @param resId
     * @return
     */
    public ConfigBuilder res(int resId) {
        this.resId = resId;
        return this;
    }

    /**
     * 加载ContentProvider资源
     *
     * @param contentProvider
     * @return
     */
    public ConfigBuilder content(String contentProvider) {
        if (contentProvider.startsWith("content:")) {
            this.contentProvider = contentProvider;
            return this;
        }

        return this;
    }

    /**
     * 加载raw资源
     *
     * @param rawPath
     * @return
     */
    public ConfigBuilder raw(String rawPath) {

        this.rawPath = rawPath;


        return this;
    }

    /**
     * 加载asserts资源
     *
     * @param assertspath
     * @return
     */
    public ConfigBuilder asserts(String assertspath) {
        this.assertspath = assertspath;

        return this;
    }

    public ConfigBuilder into(View targetView) {
        new ImageConfig(this).show();
        return this;
    }

    public ConfigBuilder listener(RequestListener requestListener){
        this.requestListener = requestListener;
        return this;
    }


    /**
     * 加载图片的分辨率
     *
     * @param oWidth
     * @param oHeight
     * @return
     */
    public ConfigBuilder override(int oWidth, int oHeight) {
        this.oWidth = ImageUtil.dip2px(oWidth);
        this.oHeight = ImageUtil.dip2px(oHeight);
        return this;
    }

    /**
     * 占位图
     *
     * @param placeHolderResId
     * @return
     */
    public ConfigBuilder placeHolder(int placeHolderResId) {
        this.placeHolderResId = placeHolderResId;
        return this;
    }


    /**
     * 是否需要高斯模糊
     *
     * @return
     */
    public ConfigBuilder blur(int blurRadius) {
        this.needBlur = true;
        this.blurRadius = blurRadius;
        return this;
    }

    /**
     * 圆角

     * @return
     */
    public ConfigBuilder asCircle() {
        this.shapeMode = ShapeMode.OVAL;
        return this;
    }

    /**
     * 形状为圆角矩形时的圆角半径
     *
     * @param rectRoundRadius
     * @return
     */
    public ConfigBuilder rectRoundCorner(int rectRoundRadius) {
        this.rectRoundRadius = ImageUtil.dip2px(rectRoundRadius);
        this.shapeMode = ShapeMode.RECT_ROUND;
        return this;
    }


    /**
     * 正方形
     *
     * @return
     */
    public ConfigBuilder asSquare() {
        this.shapeMode = ShapeMode.SQUARE;
        return this;
    }


    /**
     * 磁盘缓存
     */
    public ConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    /**
     * 拉伸/裁剪模式
     *
     * @param scaleMode 取值ScaleMode
     * @return
     */
    public ConfigBuilder scale(int scaleMode) {
        this.scaleMode = scaleMode;
        return this;
    }


    public ConfigBuilder priority(int priority) {
        this.priority = priority;

        return this;
    }

    public ConfigBuilder colorFilter(int filteColor) {
        this.filteColor = filteColor;
        this.needFilteColor = true;
        return this;
    }

    public ConfigBuilder brightnessFilter(float level) {
        this.isNeedBrightness = true;
        this.brightnessLeve = level;
        return this;
    }

    public ConfigBuilder grayscaleFilter() {
        this.isNeedGrayscale = true;
        return this;
    }

    public ConfigBuilder swirlFilter() {
        this.isNeedSwirl = true;
        return this;
    }

    public ConfigBuilder toonFilter() {
        this.isNeedToon = true;
        return this;
    }

    public ConfigBuilder sepiaFilter() {
        this.isNeedSepia = true;
        return this;
    }

    public ConfigBuilder contrastFilter(float constrasrLevel) {
        this.contrastLevel = constrasrLevel;
        this.isNeedContrast = true;
        return this;
    }

    public ConfigBuilder invertFilter() {
        this.isNeedInvert = true;
        return this;
    }

    public ConfigBuilder pixelationFilter(float pixelationLevel) {
        this.pixelationLevel = pixelationLevel;
        this.isNeedPixelation = true;
        return this;
    }

    public ConfigBuilder sketchFilter() {
        this.isNeedSketch = true;
        return this;
    }

    public ConfigBuilder vignetteFilter() {
        this.isNeedVignette = true;
        return this;
    }


    public ImageConfig build(){
        return new ImageConfig(new ConfigBuilder(isNeedOriginalSize,asBitmap,asGif,url,thumbnail,filePath,file,resId,rawPath,assertspath,contentProvider
                ,ignoreCertificateVerify,width,height,oWidth,oHeight,shapeMode,scaleMode,diskCacheStrategy,priority
                ,isNeedVignette,isNeedSketch,pixelationLevel,isNeedPixelation,isNeedInvert,contrastLevel,isNeedContrast,isNeedSepia
                ,isNeedToon,isNeedSwirl,isNeedGrayscale,isNeedBrightness,brightnessLeve,filteColor,needBlur,needFilteColor
                ,placeHolderResId,bitmapListener,blurRadius,errorResId,requestListener));
    }
}