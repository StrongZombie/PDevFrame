package hbb.example.test.util

import android.content.Context
import hbb.example.test.R
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**
 * @author HuangJiaHeng
 * @date 2020/4/2.
 */
@RunWith(MockitoJUnitRunner::class)
class GsonUtilTest {
    @Mock
    lateinit var mMockContext: Context

    var mockData4 = "{\n" +
            "\t\"ret\":\"1\",\n" +
            "\t\"msg\":\"success\",\n" +
            "\t\"content\": []\n" +
            "}"
    var result = "{\"ret\":\"1\",\"msg\":\"success\",\"content\":[]}"
    @Test
    fun stringToJsonObject() {
        //模拟方法调用的返回值，隔离对Android系统的依赖
        Mockito.`when`(mMockContext.getString(R.string.app_name)).thenReturn("test")
        MatcherAssert.assertThat(
            mMockContext.getString(R.string.app_name),
            Is.`is`("test")
        ) //判断是否和这个值一样
    }
}