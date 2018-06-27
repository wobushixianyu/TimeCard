package com.david.timecard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;
import com.david.timecard.utils.TCUtils;

/**
 * 流式布局的RadioGroup
 */
public class FlowRadioGroup extends RadioGroup {
    private Context mContext;

    public FlowRadioGroup(Context context) {
        super(context);
        this.mContext = context;
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight() + TCUtils.dip2px(mContext, 5);
                x += width + TCUtils.dip2px(mContext, 15);
                y = row * height + height;
                if (x > maxWidth) {
                    x = width + TCUtils.dip2px(mContext, 15);
                    row++;
                    y = row * height + height;
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y + TCUtils.dip2px(mContext, 5));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight() + TCUtils.dip2px(mContext, 5);//设置每个radiobutton的上下间隔5dp
                x += width + TCUtils.dip2px(mContext, 15);//设置每个radiobutton的左右间隔15dp
                y = row * height + height;
                if (x > maxWidth) {
                    x = width + TCUtils.dip2px(mContext, 15);//设置每个radiobutton的左右间隔15dp
                    row++;
                    y = row * height + height;
                }
                //设置每个radiobutton的位置
                child.layout(x - width - TCUtils.dip2px(mContext, 15),
                        y - height + TCUtils.dip2px(mContext, 5),
                        x - TCUtils.dip2px(mContext, 15),
                        y);
            }
        }
    }
}
