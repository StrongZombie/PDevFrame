package hbb.example.test.lrucache

import android.graphics.Bitmap
import android.util.LruCache





/**
 * @author HuangJiaHeng
 * @date 2020/5/12.
 */
class ImageLoader {
    /**
     * mLruCache 为缓存类，一般用内存的1/8，底层实现是LinkeHashMap (记录顺序的hashmap)
     * 每次get的对象会添加到尾部，长期不使用的，使用次数少的，会在内存满时移除
     * */

    lateinit var  mLruCache:LruCache<String,Bitmap>
    constructor(){
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        val cacheSize = maxMemory / 8
        mLruCache = LruCache(cacheSize)
    }

    fun addBitmap(key: String?, bitmap: Bitmap?) {
        if (getBitmap(key) == null) {
            mLruCache.put(key, bitmap)
        }
    }

    /**
     * 从缓存中获取图片
     *
     * @param key
     * @return
     */
    fun getBitmap(key: String?): Bitmap? {
        return mLruCache.get(key)
    }

    /**
     * 从缓存中删除指定的 Bitmap
     *
     * @param key
     */
    fun removeBitmapFromMemory(key: String?) {
        mLruCache.remove(key)
    }
}