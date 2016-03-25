//package Fresco.Utils;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.imagepipeline.core.ImagePipeline;
//import com.facebook.imagepipeline.core.ImagePipelineConfig;
//import com.willprojeck.okhttp.okhttp_text.R;
//
///**
// * Created by xujian on 16/3/22.
// * fresco工具管理类
// * 初始化配置管理 ，
// */
//public class FrescoHelper {
//    /**
//     * 初始化fresco
//     *
//     * @param context .
//     */
//    public static void frescoInit(Context context) {
//        init(context.getResources());//配置占位图
//        ImagePipelineConfig config = FrescoConfigConstants.getImagePipelineConfig(context);
//        Fresco.initialize(context, config);
//    }
//
//    /**
//     * 设置默认图
//     * @param resources
//     */
//    @SuppressWarnings("deprecation")
//    public static void init(final Resources resources) {
//        if (FrescoConfigConstants.sPlaceholderDrawable == null) {
//            FrescoConfigConstants.sPlaceholderDrawable = resources.getDrawable(R.color.placeholder);
//        }
//        if (FrescoConfigConstants.sErrorDrawable == null) {
//            FrescoConfigConstants.sErrorDrawable = resources.getDrawable(R.color.error);
//        }
//    }
//
//    /**
//     * 清理所有缓存
//     */
//    public static void clean() {
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        imagePipeline.clearCaches();
//    }
//}
