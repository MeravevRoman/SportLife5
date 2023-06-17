package com.example.myapplication.utils;

import android.content.Context;

import java.util.List;

public class GraphUtils {

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getMax(List<Integer> values) {
        int max = 0;
        for (Integer i : values) {
            if (i > max) max = i;
        }
        // получаем ближайшее большее число кратное 10^n (10, 100, 1000,...)
        int nDigits = String.valueOf(max).length() - 1;
        double multiplier = Math.pow(10, nDigits);
        int upMax = (int) (Math.ceil(max / multiplier) * multiplier);

        return upMax;
    }

}
