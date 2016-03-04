package com.superbug.moi.cquptlife.app;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.superbug.moi.cquptlife.R;
import com.umeng.analytics.MobclickAgent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    private SystemBarTintManager tintManager;// 换status bar颜色的
    private MaterialDialog dialog;// 一个显示进度条的dialog

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);// 友盟的统计呀
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);// 友盟的统计呀
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP.getInstance().addActivity(this);// 当activity启动的时候，把他加到activity数组中
        /* 状态栏透明 */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }
        tintManager = new SystemBarTintManager(this);// create our manager instance after the content view is set
        tintManager.setStatusBarTintEnabled(true);// enable status bar tint
        setBarTintColor(getResources().getColor(R.color.primary_dark_color));// status bar颜色
        /* 字体 */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_fangzheng_light.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APP.getInstance().removeActivity(this);// activity要关闭的时候把它从数组中移除
    }

    /* 字体 */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * 修改status bar color
     * 为了让子类可以自己修改status bar color，把方法提出来……
     * @param color status bar color
     */
    protected void setBarTintColor(int color) {
        tintManager.setTintColor(color);
    }

    /**
     * 显示带进度条的dialog
     * @param title 标题
     */
    public void showProgress(String title) {
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(this.getResources().getColor(R.color.primary_color))
                .backgroundColor(this.getResources().getColor(R.color.white))
                .positiveColor(this.getResources().getColor(R.color.primary_color))
                .content(APP.getContext().getResources().getString(R.string.please_wait))
                .theme(Theme.LIGHT)
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    /* 让进度条的dialog消失 */
    public void dismissProgress() {
        if (dialog.isShowing()) dialog.dismiss();
    }

    /* EditText 之外的触碰让键盘消失 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev)/* 不能阻断别的控件的Touch呀 */ || onTouchEvent(ev);
    }

    /* 判断触碰的位置是否在EditText上，是否应该隐藏键盘 */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            /* 得到editText的位置 */
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);// 超过就返回true
        }
        return false;
    }
}
