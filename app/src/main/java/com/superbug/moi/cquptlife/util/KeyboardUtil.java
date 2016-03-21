package com.superbug.moi.cquptlife.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘工具类
 * @author MOILING
 */
public class KeyboardUtil {

    private KeyboardUtil() {
        /* 作为一个工具类，应该有工具类的自觉，不应该被实例化嘛 */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 显示键盘
     * @param v 作为焦点的View
     */
    public static void showInput(View v) {
        v.requestFocus(); // 让这个view得到焦点先
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏键盘
     * @param v 作为焦点的View
     */
    public static void hideInput(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 在触碰非EditText情况下自动隐藏键盘
     * @param v 触摸的view，用来判断是否触摸的是EditText
     * @param event 触摸事件
     */
    public static void autoHideInput(View v, MotionEvent event) {
        if (isShouldHideInput(v, event)) hideInput(v);
    }

    /*
     *  判断触碰的位置是否在EditText上，是否应该隐藏键盘
     *  @param v 触摸的view，用来判断是否触摸的是EditText
     *  @param event 触摸事件
     *  @return 是否应该隐藏键盘
     */
    private static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            /* 得到editText的位置 */
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            /* 超过EditText范围就返回true，表示你摸了除了EditText的别人！键盘不理你了，应该自动隐藏 */
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
