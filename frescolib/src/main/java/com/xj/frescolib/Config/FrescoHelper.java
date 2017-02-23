package com.xj.frescolib.Config;

import android.content.Context;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.xj.frescolib.View.CommUtil;

import java.io.File;

/**
 * Created by xujian on 16/3/22.
 * fresco工具管理类
 * 初始化配置管理 ，
 */
public class FrescoHelper {
    /**
     * 初始化fresco
     * 设置默认图和加载失败的图
     *
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

    /**
     * 获取大小
     *
     * @return
     */
    public static long getCaches() {
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().trimToMinimum();
        return Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
    }

    /**
     * 取本地缓存图片的文件
     *
     * @param uri
     * @return
     */
    public static File getFile(String uri) {
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(uri));
        File file = resource.getFile();
        return file;
    }


    public static String showCacheSize() {
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().trimToMinimum();
        long cacheSize = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        if (cacheSize <= 0) {
            return "0.00";
        } else {
            return CommUtil.convertFileSize(cacheSize);
        }
    }
}
