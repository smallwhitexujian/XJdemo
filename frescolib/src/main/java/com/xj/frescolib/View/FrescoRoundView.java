package com.xj.frescolib.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.xj.frescolib.FrescoConfigConstants;
import com.xj.frescolib.RoundBuilder;

/**
 * Created by xujian on 16/3/23.
 * 圆形图片
 * 圆圈 - 设置roundAsCircle为true
 * 圆角 - 设置roundedCornerRadius
 */
public class FrescoRoundView extends SimpleDraweeView {
    private RoundingParams roundingParams;
    private Context mcontext;
    public static int fadeDuration = 300;

    public FrescoRoundView(Context context) {
        super(context);
        init(context);
    }

    public FrescoRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FrescoRoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mcontext = context;
        RoundBuilder roundBuilder = new RoundBuilder();
        roundingParams = roundBuilder
                .setRoundAsCircle(true)//设置是否为圆形
//                .setBorder(Color.BLACK, 2)//设置边框颜色和边框大小
                .setOverlayColor(Color.WHITE)//覆盖颜色，如果不设置覆盖颜色gif图会出现问题
                .setCornersRadius(10)//设置圆角
                .setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR)//设置圆形模式
                .build();
    }

    /**
     * 显示图片
     *
     * @param url 图片地址
     */
    public void setImageURI(String url) {
        setHierarchy(getGenericDraweeHierarchy(mcontext));
        ImageRequest imageRequest = getImageRequest(url);
        DraweeController draweeController = getDraweeController(imageRequest);
        setController(draweeController);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    public void setFadeDuration(int Duration){
        fadeDuration = Duration;
    }

    //图片解码
    private ImageDecodeOptions getImageDecodeOptions() {
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


    //Drawees   DraweeHierarchy  组织
    public GenericDraweeHierarchy getGenericDraweeHierarchy(Context context) {
        return new GenericDraweeHierarchyBuilder(context.getResources())
                .reset()//重置
//                .setFadeDuration(fadeDuration)//fresco:fadeDuration="300"加载图片动画时间
                .setFailureImage(FrescoConfigConstants.sErrorDrawable)//fresco:failureImage="@drawable/error"失败图
                .setPlaceholderImage(FrescoConfigConstants.sPlaceholderDrawable)//fresco:placeholderImage="@color/wait_color"占位图
//                .setProgressBarImage(new ProgressBarDrawable())//进度条fresco:progressBarImage="@drawable/progress_bar"进度条
                .setRoundingParams(roundingParams)//圆形/圆角fresco:roundAsCircle="true"圆形
                .build();
    }

    /**
     * 图片加载成功或者失败，会执行里面的方法，
     * 其中图片加载成功时会执行onFinalImageSet方法，
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
//            FLog.d("Final image received! " +
//                            "Size %d x %d",
//                    "Quality level %d, good enough: %s, full quality: %s",
//                    imageInfo.getWidth(),
//                    imageInfo.getHeight(),
//                    qualityInfo.getQuality(),
//                    qualityInfo.isOfGoodEnoughQuality(),
//                    qualityInfo.isOfFullQuality());
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//            FLog.d(getClass(),("Intermediate image received"));
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
//            FLog.e(getClass(), throwable, "Error loading %s", id);
        }
    };

    private DraweeController getDraweeController(ImageRequest imageRequest) {
        return Fresco.newDraweeControllerBuilder()
                .reset()//重置
                .setAutoPlayAnimations(true)//自动播放图片动画
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
                .setOldController(getController())//DraweeController复用
                .setTapToRetryEnabled(true)//点击重新加载图
                .setControllerListener(controllerListener)
                .build();
    }
}
