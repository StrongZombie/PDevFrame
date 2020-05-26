package hbb.example.test.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.DynamicLayout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 为了解决 Spannable 与 android:ellipsize="end" 冲突的问题
 * 当android:maxLines="n" n>=2 时，如果包含 Spannable，行尾没有 "..."
 * 使用 android:singleLine="true" 时，无此问题
 *
 */
@SuppressLint("AppCompatCustomView")
public class EllipsizeTextView extends TextView {
    public EllipsizeTextView(Context context) {
        super(context);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        StaticLayout layout = null;
        Field field = null;
        try {
            Field staticField = DynamicLayout.class.getDeclaredField("sStaticLayout");
            staticField.setAccessible(true);
            layout = (StaticLayout) staticField.get(DynamicLayout.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (layout != null) {
            try {
                field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
                field.setAccessible(true);
                field.setInt(layout, getMaxLines());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (layout != null && field != null) {
            try {
                field.setInt(layout, Integer.MAX_VALUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }}