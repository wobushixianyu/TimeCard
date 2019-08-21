package com.david.timecard.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Window;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TCUtils {
    //将dp转换为px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //将px转换为dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Bitmap capture(Activity activity) {
        Window window = activity.getWindow();
        Bitmap bmp = null;
        if (window != null){
            window.getDecorView().setDrawingCacheEnabled(true);
            bmp = window.getDecorView().getDrawingCache();
        }
        return bmp;
    }

    @SuppressLint("SimpleDateFormat")
    public static int getDateSpace(String time)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();

        calst.setTime(sdf.parse(time));
        caled.setTime(sdf.parse(sdf.format(System.currentTimeMillis())));

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数

        return ((int)(caled.getTime().getTime()/1000)-(int)(calst.getTime().getTime()/1000))/3600/24;
    }
}
