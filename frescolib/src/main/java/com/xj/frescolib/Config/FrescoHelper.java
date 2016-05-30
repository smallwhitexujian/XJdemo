package com.xj.frescolib.Config;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

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
        ImagePipelineConfig config = FrescoConfigConstants.getImagePipelineConfig(context);
        Fresco.initialize(context, config);
    }


    /**
     * 清理所有缓存
     */
    public static void clean() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }
}
