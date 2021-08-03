package hbb.example.test.glide;

import androidx.annotation.NonNull;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/26
 *   desc  :
 * </pre>
 */
@GlideExtension
public class MyAppExtension {
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;
    private MyAppExtension() { } // utility class


    //你可以为方法任意添加参数，但要保证第一个参数为 RequestOptions。
    @NonNull
    @GlideOption  //（GlideOption修饰静态方法）
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options) {
        return options
                .fitCenter()
                .override(MINI_THUMB_SIZE);
    }
}
