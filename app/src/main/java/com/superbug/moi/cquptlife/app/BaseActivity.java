package com.superbug.moi.cquptlife.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.superbug.moi.cquptlife.R;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends AppCompatActivity {

    private SystemBarTintManager tintManager;
    private MaterialDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟的统计呀
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟的统计呀
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP.getInstance().addActivity(this);

        //状态栏透明
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        // create our manager instance after the content view is set
        tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        //tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
        setBarTintColor(getResources().getColor(R.color.primary_dark_color));
        // set a custom navigation bar resource
        //tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
        //tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    protected void setBarTintColor(int color) {
        tintManager.setTintColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APP.getInstance().removeActivity(this);
    }

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

    public void dismissProgress() {
        if (dialog.isShowing()) dialog.dismiss();
    }
}
