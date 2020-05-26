package hbb.example.test.util;

import android.content.Context;

import com.example.myapplication.Utils.GsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import hbb.example.test.R;
import hbb.example.test.view.MainActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * @author HuangJiaHeng
 * @date 2020/4/2.
 */
@RunWith(MockitoJUnitRunner.class)
public class GsonUtilTestj {
    @Mock
    Context mMockContext;
    @Test
    public void  stringToJsonObject() {
        //模拟方法调用的返回值，隔离对Android系统的依赖
        when(mMockContext.getString(R.string.app_name)).thenReturn("test");
        assertThat(mMockContext.getString(R.string.app_name), is("test"));

        when(mMockContext.getPackageName()).thenReturn("com.jdqm.androidunittest");
        System.out.println(mMockContext.getPackageName());
    }
}
