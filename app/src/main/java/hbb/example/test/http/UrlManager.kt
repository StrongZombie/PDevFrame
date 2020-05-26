package hbb.example.test.http

import io.reactivex.Observable
import retrofit2.http.*
import android.R.attr.path



/**
 * @author HuangJiaHeng
 * @date 2020/2/5.
 *
 * 1.@GET、@POST、@PUT（更新）、@DELETE、@HEAD（监听头部，没body数据）分别对应 HTTP中的网络请求方式
 * 2.@HTTP替换@GET、@POST、@PUT、@DELETE、@HEAD注解的作用 及 更多功能拓展具体使用：通过属性method、path、hasBody进行设置
 */
class  UrlManager {
    companion object{
        const val BASE_URL = "https://"
    }


    interface Api{
        /**
         * GET 请求
         * @param name
         * */
        @GET("/url?id=1")
        fun <T> getG(@Field("name")name:String):Observable<T>

        /***
         * GET 请求
         * @param id
         */
        @HTTP(method = "GET", path = "blog/{id}",hasBody = false)
        fun <T> getG1(@Field("id")id:Int):Observable<T>


        /**
         * POST 请求
         * @param @FieldMap 为map集合，@FormUrlEncoded 表单提交
         * */
        @POST("/url")
        @FormUrlEncoded
        fun <T> getP(@FieldMap data:HashMap<String,String>):Observable<T>
    }
}
