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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    private MaterialDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP.getInstance().addActivity(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_fangzheng_light.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APP.getInstance().removeActivity(this);
    }

    /* 字体 */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showProgress(String title) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(this)
                    .title(title)
                    .titleColor(ContextCompat.getColor(this, R.color.primary_color))
                    .backgroundColor(ContextCompat.getColor(this, R.color.white))
                    .positiveColor(ContextCompat.getColor(this, R.color.primary_color))
                    .content(APP.getContext().getResources().getString(R.string.please_wait))
                    .theme(Theme.LIGHT)
                    .progress(true, 100)
                    .cancelable(false).build();
        }
        dialog.show();
    }

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
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
}
