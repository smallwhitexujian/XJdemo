package com.xujian.frescolib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by xujian on 16/3/22.
 * fresco工具管理类
 * 初始化配置管理 ，
 */
public class FrescoHelper {
    /**
     * 初始化fresco
     *  设置默认图和加载失败的图
     * @param context .
     */
    public static void frescoInit(Context context) {
        init();//配置占位图
        ImagePipelineConfig config = FrescoConfigConstants.getImagePipelineConfig(context);
        Fresco.initialize(context, config);
    }

    /**
     * 设置默认图
     */
    @SuppressWarnings("deprecation")
    public static void init() {
        if (FrescoConfigConstants.sPlaceholderDrawable == null) {
            FrescoConfigConstants.sPlaceholderDrawable = new ColorDrawable(0xFFe1e4eb);
        }
        if (FrescoConfigConstants.sErrorDrawable == null) {
            FrescoConfigConstants.sErrorDrawable = new ColorDrawable(0xFFff9999);
        }
    }

    /**
     * 设置占位符
     *
     * @param context  .
     * @param drawable 图片资源
     */
    public static void setPlaceHolderDrawable(Context context, int drawable) {
        FrescoConfigConstants.sPlaceholderDrawable = context.getResources().getDrawable(drawable, null);
    }

    /**
     * 设置加载失败的图
     *
     * @param context  .
     * @param drawable 图片资源
     */
    public static void setErrorDrawable(Context context, int drawable) {
        FrescoConfigConstants.sErrorDrawable = context.getResources().getDrawable(drawable, null);
    }

    /**
     * 清理所有缓存
     */
    public static void clean() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }
}
