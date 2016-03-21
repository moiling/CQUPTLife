package com.superbug.moi.cquptlife.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.superbug.moi.cquptlife.R;

/**
 * @author MOILING
 */
public class ColorUtils {

    private ColorUtils() {
        /* 作为一个工具类，应该有工具类的自觉，不应该被实例化嘛 */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void changeColor(Context context) {
        String color = (String) SPUtils.get(context, "color", "ORANGE");
        if (color != null && color.equals("ORANGE")) {
            SPUtils.put(context, "color", "BLUE");
        } else {
            SPUtils.put(context, "color", "ORANGE");
        }
    }

    public static int checkColor(Context context) {
        int primaryColor = ContextCompat.getColor(context, R.color.primary_color);
        int bluePrimaryColor = ContextCompat.getColor(context, R.color.blue_primary_color);
        int color = primaryColor;
        String colorName = (String) SPUtils.get(context, "color", "ORANGE");
        if (colorName != null) {
            switch (colorName) {
                case "ORANGE":
                    color = primaryColor;
                    break;
                case "BLUE":
                    color = bluePrimaryColor;
                    break;
            }
        }
        return color;
    }
}
