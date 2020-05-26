package hbb.example.test.service;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hbb.example.test.R;

/**
 * @author HuangJiaHeng
 * @date 2020/5/22.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_view);

        try {
            Object obj =  Class.forName("android.os.ServiceManager").newInstance();
//            Servicemanager.addService() 需要下载源码才有
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
