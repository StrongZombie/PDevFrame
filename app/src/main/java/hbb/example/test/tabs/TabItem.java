package hbb.example.test.tabs;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.TintTypedArray;

import com.google.android.material.tabs.TabLayout;

/**
 * TabItem is a special 'view' which allows you to declare tab items for a {@link com.google.android.material.tabs.TabLayout} within
 * a layout. This view is not actually added to TabLayout, it is just a dummy which allows setting
 * of a tab items's text, icon and custom layout. See TabLayout for more information on how to use
 * it.
 *
 * @attr ref com.google.android.material.R.styleable#TabItem_android_icon
 * @attr ref com.google.android.material.R.styleable#TabItem_android_text
 * @attr ref com.google.android.material.R.styleable#TabItem_android_layout
 * @see TabLayout
 */
//TODO(b/76413401): make class final after the widget migration
public class TabItem extends View {
    //TODO(b/76413401): make package private after the widget migration
    public final CharSequence text;
    //TODO(b/76413401): make package private after the widget migration
    public final Drawable icon;
    //TODO(b/76413401): make package private after the widget migration
    public final int customLayout;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TintTypedArray a =
                TintTypedArray.obtainStyledAttributes(context, attrs, com.google.android.material.R.styleable.TabItem);
        text = a.getText(com.google.android.material.R.styleable.TabItem_android_text);
        icon = a.getDrawable(com.google.android.material.R.styleable.TabItem_android_icon);
        customLayout = a.getResourceId(com.google.android.material.R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
