package hbb.example.test.dagger2

import android.util.Log
import com.google.android.filament.Entity
import dagger.Component
import dagger.Module
import dagger.Provides
import hbb.example.test.delevopMode.Behavior
import org.intellij.lang.annotations.Flow
import javax.inject.Inject

class Dagger2Guide {
    companion object{
        private var TAG = "Dagger2Guide"
    }

    @Inject
    lateinit var a:A

    fun dagger1(){
//        DaggerDagger2Guide_AComponent.builder().build().inject(this)
        a= DaggerDagger2Guide_AComponent.builder().build().inject()
        a.hellO()

    }
     class A  @Inject constructor(){
       fun hellO(){
           Log.e(TAG, " dddd")
       }

    }
    /**
     * 链接两个inject，注射
     * */
    @Component
    interface AComponent{
//        fun inject(dagger2Guide: Dagger2Guide)
        fun inject():A
    }


//    /**
//     * @Module 标记类，表示Component 注射对象时，通过Module
//     * @Provides 标记方法，返回需要注射的对象
//     * */
//    /**
//     * 获取D对象需要两个参数 C,B
//     * */
//    class D @Inject constructor(b:B,c: C){
//        fun helloC(){
//            Log.e(TAG,"helloC")
//        }
//    }
//    class B {}
//    class C {}
//    @Module
//    class BModule(private var b : B){
//        @Provides
//        fun getB() = b
//    }
//    @Module(includes = [BModule::class])
//    class DModule(private var c:C){
//        @Provides
//        fun getD(b:B):D{
//            return D(b,c)
//        }
//    }
//
//    @Component(modules = [DModule::class])
//    interface DComponent{
//        fun inject(dagger2Guide: Dagger2Guide)
//    }
//
//
//    @Inject
//    lateinit var d:D
//    fun dagger2() {
//        DaggerDagger2Guide_DComponent.builder()
//            .bModule(BModule(B()))
//            .dModule(DModule(C()))
//            .build()
//            .inject(this)
//        d.helloC()
//    }


}



