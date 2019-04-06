package ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import ImageLoader.config.GlobalConfig;
import ImageLoader.config.ImageConfig;
import ImageLoader.config.PriorityMode;
import ImageLoader.config.ScaleMode;
import ImageLoader.config.ShapeMode;
import ImageLoader.utils.ImageUtil;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * Created By zhongxianfeng on 19-3-14
 * github: https://github.com/xianfeng92
 */
public class GlideLoader implements ILoader {

    private RequestOptions options = new RequestOptions();

    /**
     * @param context        上下文
     * @param cacheSizeInM   Glide默认磁盘缓存最大容量250MB
     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
     * @param isInternalCD   true 磁盘缓存到应用的内部目录 / false 磁盘缓存到外部存
     */
    @Override
    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        Glide.get(context).setMemoryCategory(memoryCategory); //如果在应用当中想要调整内存缓存的大小，开发者可以通过如下方式：
        GlideBuilder builder = new GlideBuilder();
        if (isInternalCD) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        } else {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public RequestBuilder apply(final ImageConfig config) {
        if (ImageUtil.shouldSetPlaceHolder(config)) {
            options.placeholder(config.getPlaceHolderResId());
        }
        int scaleMode = config.getScaleMode();
        switch (scaleMode) {
            case ScaleMode.CENTER_CROP:
                options.centerCrop();
                break;
            case ScaleMode.FIT_CENTER:
                options.fitCenter();
                break;
            default:
                options.fitCenter();
                break;
        }

        // isNeedOriginalSize 为 true，Glide就不会再去自动压缩图片，而是会去加载图片的原始尺寸
        // 当然，这种写法也会面临着更高的OOM风险
        if (config.isNeedOriginalSize()){
            options.override(Target.SIZE_ORIGINAL);
        }

        //设置图片加载的分辨 sp
        if (config.getoWidth() != 0 && config.getoHeight() != 0) {
            options.override(config.getoWidth(), config.getoHeight());
        }

        //是否跳过磁盘存储
        if (config.getDiskCacheStrategy() != null) {
            options.diskCacheStrategy(config.getDiskCacheStrategy());
        }

        //设置图片加载优先级
        setPriority(config, options);

        if (config.getErrorResId() > 0) {
            options.error(config.getErrorResId());
        }

        RequestBuilder requestBuilder;
        // git 资源加载，默认加载的是图片资源
        if (config.isAsGif()){
            requestBuilder = Glide.with(GlobalConfig.context).asGif();
        }else {
            requestBuilder = Glide.with(GlobalConfig.context).asBitmap();
        }
        //设置缩略图
        if (config.getThumbnail() != 0) {
            requestBuilder = requestBuilder.thumbnail(config.getThumbnail());
        }

        if(config.getRequestListener() != null){
            requestBuilder = requestBuilder.listener(config.getRequestListener());
        }

        if(setShapeModeAndBlur(config,options) != null){
            options = options.transforms(setShapeModeAndBlur(config, options));
        }
        if (config.getUrl() != null){
            return requestBuilder.load(config.getUrl()).apply(options);
        }else if(config.getFile() != null){
            return requestBuilder.load(config.getFile()).apply(options);
        }else if (config.getResId() != 0){
            return requestBuilder.load(config.getResId()).apply(options);
        }else if(config.getRawPath() != null){
            return requestBuilder.load(config.getRawPath()).apply(options);
        }
            return null;
    }

    /**
     * 设置加载优先级
     *
     * @param config
     * @param options
     */
    private void setPriority(ImageConfig config, RequestOptions options) {
        switch (config.getPriority()) {
            case PriorityMode.PRIORITY_LOW:
                options.priority(Priority.LOW);
                break;
            case PriorityMode.PRIORITY_NORMAL:
                options.priority(Priority.NORMAL);
                break;
            case PriorityMode.PRIORITY_HIGH:
                options.priority(Priority.HIGH);
                break;
            case PriorityMode.PRIORITY_IMMEDIATE:
                options.priority(Priority.IMMEDIATE);
                break;
            default:
                options.priority(Priority.IMMEDIATE);
                break;
        }
    }

    /**
     * 设置图片滤镜和形状
     *
     * @param config
     * @param options
     */
    private Transformation[] setShapeModeAndBlur(ImageConfig config, RequestOptions options) {
        int count = 0;
        BitmapTransformation[] transformation = new BitmapTransformation[statisticsCount(config)];

        if (config.isNeedBlur()) {
            transformation[count] =  new BlurTransformation(config.getBlurRadius());
            count++;
        }

        if (config.isNeedBrightness()) {
            transformation[count] =  new BrightnessFilterTransformation(config.getBrightnessLeve()); //亮度
            count++;
        }

        if (config.isNeedGrayscale()) {
            transformation[count] = new GrayscaleTransformation(); //黑白效果
            count++;
        }

        if (config.isNeedFilteColor()) {
            transformation[count] = new ColorFilterTransformation(config.getFilteColor());
            count++;
        }

        if (config.isNeedSwirl()) {
            transformation[count] = new SwirlFilterTransformation( 0.5f, 1.0f, new PointF(0.5f, 0.5f)); //漩涡
            count++;
        }

        if (config.isNeedToon()) {
            transformation[count] =  new ToonFilterTransformation(); //油画
            count++;
        }

        if (config.isNeedSepia()) {
            transformation[count] =  new SepiaFilterTransformation(); //墨画
            count++;
        }

        if (config.isNeedContrast()) {
            transformation[count] = new ContrastFilterTransformation(config.getContrastLevel()); //锐化
            count++;
        }

        if (config.isNeedInvert()) {
            transformation[count] = new InvertFilterTransformation(); //胶片
            count++;
        }

        if (config.isNeedPixelation()) {
            transformation[count] =  new PixelationFilterTransformation(config.getPixelationLevel()); //马赛克
            count++;
        }

        if (config.isNeedSketch()) {
            transformation[count] = new SketchFilterTransformation(); //素描
            count++;
        }

        if (config.isNeedVignette()) {
            transformation[count] = new VignetteFilterTransformation(new PointF(0.5f, 0.5f),
                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);//晕映
            count++;
        }

        switch (config.getShapeMode()) {
            case ShapeMode.RECT:
                break;
            case ShapeMode.RECT_ROUND:
                transformation[count] = new RoundedCornersTransformation
                        (config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
                count++;
                break;
            case ShapeMode.OVAL:
                transformation[count] = new CropCircleTransformation();
                count++;
                break;

            case ShapeMode.SQUARE:
                transformation[count] = new CropSquareTransformation();
                count++;
                break;
        }

        if (transformation.length != 0) {
           return transformation;
        }
        return null;
    }

    private int statisticsCount(ImageConfig config) {
        int count = 0;

        if (config.getShapeMode() == ShapeMode.OVAL || config.getShapeMode() == ShapeMode.RECT_ROUND || config.getShapeMode() == ShapeMode.SQUARE) {
            count++;
        }

        if (config.isNeedBlur()) {
            count++;
        }

        if (config.isNeedFilteColor()) {
            count++;
        }

        if (config.isNeedBrightness()) {
            count++;
        }

        if (config.isNeedGrayscale()) {
            count++;
        }

        if (config.isNeedSwirl()) {
            count++;
        }

        if (config.isNeedToon()) {
            count++;
        }

        if (config.isNeedSepia()) {
            count++;
        }

        if (config.isNeedContrast()) {
            count++;
        }

        if (config.isNeedInvert()) {
            count++;
        }

        if (config.isNeedPixelation()) {
            count++;
        }

        if (config.isNeedSketch()) {
            count++;
        }

        if (config.isNeedVignette()) {
            count++;
        }

        return count;
    }

    @Override
    public void pause() {
        Glide.with(GlobalConfig.context).pauseRequestsRecursive();
    }

    @Override
    public void resume() {
        Glide.with(GlobalConfig.context).resumeRequestsRecursive();
    }

    @Override
    public void clearDiskCache() {
        Glide.get(GlobalConfig.context).clearDiskCache();
    }

    @Override
    public void clearMomoryCache(View view) {
        Glide.with(GlobalConfig.context).clear(view);
    }

    @Override
    public void clearMomory() {
        Glide.get(GlobalConfig.context).clearMemory();
    }

    @Override
    public boolean isCached(String url) {
        return false;
    }

    @Override
    public void trimMemory(int level) {
        Glide.get(GlobalConfig.context).onTrimMemory(level);
    }

    @Override
    public void clearAllMemoryCaches() {
        Glide.get(GlobalConfig.context).onLowMemory();
    }

    @Override
    public void saveImageIntoGallery(DownLoadImageService downLoadImageService) {
        new Thread(downLoadImageService).start();
    }

}
