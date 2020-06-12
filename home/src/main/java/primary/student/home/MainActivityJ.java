package primary.student.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.home.R;


/**
 * @author HuangJiaHeng
 * @date 2020/6/12.
 */
@Route(path = "/home/main")
public class MainActivityJ extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ARouter.getInstance().inject(this);
    }
}
