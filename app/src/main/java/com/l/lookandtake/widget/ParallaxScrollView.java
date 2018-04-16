package com.l.lookandtake.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by L on 2018/4/13.
 * Description:
 */
public class ParallaxScrollView extends ScrollView {
    private OnScrollChangedListener onScrollChangedListener;

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ParallaxScrollView parallaxScrollView, int l, int t, int oldl, int oldt);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

}
