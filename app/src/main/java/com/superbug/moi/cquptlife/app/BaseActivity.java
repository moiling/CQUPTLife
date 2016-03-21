package com.superbug.moi.cquptlife.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.util.KeyboardUtil;
import com.umeng.analytics.MobclickAgent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    //private SystemBarTintManager tintManager;// 换status bar颜色的
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
        /*
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }
        tintManager = new SystemBarTintManager(this);// create our manager instance after the content view is set
        tintManager.setStatusBarTintEnabled(true);// enable status bar tint
        setBarTintColor(getResources().getColor(R.color.primary_dark_color));// status bar颜色
        */
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

    /*
     * 修改status bar color
     * 为了让子类可以自己修改status bar color，把方法提出来……
     * @param color status bar color
     */
    /*
    @Deprecated
    protected void setBarTintColor(int color) {
        tintManager.setTintColor(color);
    }
    */

    /**
     * 显示带进度条的dialog
     *
     * @param title 标题
     */
    public void showProgress(String title) {
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(ContextCompat.getColor(this, R.color.primary_color))
                .backgroundColor(ContextCompat.getColor(this, R.color.white))
                .positiveColor(ContextCompat.getColor(this, R.color.primary_color))
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // 触摸EditText以外区域自动让键盘消失
            KeyboardUtil.autoHideInput(v, ev);
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev)/* 不能阻断别的控件的Touch呀 */ || onTouchEvent(ev);
    }
}
