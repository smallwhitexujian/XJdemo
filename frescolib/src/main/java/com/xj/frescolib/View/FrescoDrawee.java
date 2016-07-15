package com.xj.frescolib.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;

/**
 * Created by xujian on 16/3/23.
 * 支持gif图 高清和低分辨图替换
 * 重载SimpleDrawView
 */
public class FrescoDrawee extends SimpleDraweeView {
    private GenericDraweeHierarchy mhierarchy;
    private Drawable DefutImage;

    public FrescoDrawee(Context context) {
        super(context);
    }

    public FrescoDrawee(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoDrawee(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 显示图片
     *
     * @param url       图片地址高清地址
     * @param lowResUri 图片低分辨率地址
     */
    public String setImageURI(String url, String lowResUri) {
        if (url == null || lowResUri == null) {
            return "url|lowResUrl not null";
        }
        if((url.startsWith("http") || url.startsWith("https")) && (lowResUri.startsWith("http") || lowResUri.startsWith("https"))){
            ImageRequest imageRequest = getImageRequest(url);
            DraweeController draweeController = getDraweeController(imageRequest, lowResUri);
            setController(draweeController);
        }else{
            Uri uri = Uri.fromFile(new File(url));
            setImageURI(uri);
        }
        return "";
    }

    /**
     * 显示图片
     *
     * @param url 图片地址
     */
    public String setImageURI(String url) {
        if (url == null) {
            return "url|lowResUrl not null";
        }
        Uri uri;
        if(url.startsWith("http") || url.startsWith("https")){
            if (!isInEditMode()) {
                ImageRequest imageRequest = getImageRequest(url);
                DraweeController draweeController = getDraweeController(imageRequest);
                setController(draweeController);
            }
        }else{
            uri = Uri.fromFile(new File(url));
            setImageURI(uri);
        }
        return "";
    }

    /**
     * 设置默认图
     *
     * @param defutImage 默认图
     */
    public void setDefutImage(Drawable defutImage) {
        DefutImage = defutImage;
        if (!isInEditMode()) {
            mhierarchy.reset();
            mhierarchy.setPlaceholderImage(defutImage, ScalingUtils.ScaleType.CENTER);
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        if (uri == null) {
            return;
        }
        super.setImageURI(uri);
    }

    /**
     * 设置图片填充模式
     */
    public void setImageImageScaleType(ScalingUtils.ScaleType scaleType) {
        if (!isInEditMode()) {
            mhierarchy.setActualImageScaleType(scaleType);
        }
    }

    //图片解码
    private static ImageDecodeOptions getImageDecodeOptions() {
        return ImageDecodeOptions.newBuilder()
                .setBackgroundColor(Color.TRANSPARENT)//图片的背景颜色
                .setUseLastFrameForPreview(true)//使用最后一帧进行预览
                .build();
    }

    //图片显示
    private ImageRequest getImageRequest(String uri) {
        return ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setAutoRotateEnabled(true)
                .setImageDecodeOptions(getImageDecodeOptions())
                .setImageType(ImageRequest.ImageType.DEFAULT)
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(true)
//                .setResizeOptions(new ResizeOptions(getLayoutParams().width, getLayoutParams().height))//暂不支持
                .build();
    }

    //Drawee控制器
    private DraweeController getDraweeController(ImageRequest imageRequest, String lowResUri) {
        return Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)//自动播放图片动画
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//先下载显示低分辨率的图
                .setOldController(getController())//DraweeController复用
                .setTapToRetryEnabled(true)//点击重新加载图
                .build();
    }


    @Override
    public void setHierarchy(GenericDraweeHierarchy hierarchy) {
        super.setHierarchy(hierarchy);
        if (!isInEditMode()) {
            mhierarchy = hierarchy;
        }
    }

    /**
     * 图片加载成功或者失败，会执行里面的方法，其中图片加载成功时会执行onFinalImageSet方法，
     * 图片加载失败时会执行onFailure方法，
     * 如果图片设置渐进式，onIntermediateImageFailed会被回调
     */
    private ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                return;
            }
            QualityInfo qualityInfo = imageInfo.getQualityInfo();
//            FLog.d("Final image received! " + "Size %d x %d",
//                    "Quality level %d, good enough: %s, full quality: %s",
//                    imageInfo.getWidth(),
//                    imageInfo.getHeight(),
//                    qualityInfo.getQuality(),
//                    qualityInfo.isOfGoodEnoughQuality(),
//                    qualityInfo.isOfFullQuality());
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//            FLog.d(getClass(), ("Intermediate image received"));
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
//            FLog.e(getClass(), throwable, "Error loading %s", id);
        }
    };

    //操作处理bitmap
    public Postprocessor redMeshPostprocessor = new BasePostprocessor() {
        @Override
        public String getName() {
            return "redMeshPostprocessor";
        }

        @Override
        public void process(Bitmap bitmap) {
        }
    };

    private DraweeController getDraweeController(ImageRequest imageRequest) {
        return Fresco.newDraweeControllerBuilder()
//                .reset()//重置
                .setAutoPlayAnimations(true)//自动播放图片动画
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
                .setOldController(getController())//DraweeController复用
                .setTapToRetryEnabled(false)//点击重新加载图
//                .setControllerListener(controllerListener)
                .build();
    }

    //Drawees   DraweeHierarchy  组织
    public GenericDraweeHierarchy getGenericDraweeHierarchy(Context context) {
        return new GenericDraweeHierarchyBuilder(context.getResources())
                .build();
    }
}
