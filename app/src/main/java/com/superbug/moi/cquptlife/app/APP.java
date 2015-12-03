package com.superbug.moi.cquptlife.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.superbug.moi.cquptlife.BuildConfig;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.util.Utils;
import com.umeng.analytics.AnalyticsConfig;

import java.util.ArrayList;
import java.util.List;

public class APP extends Application {

    private static List<Activity> activityList = new ArrayList<>();
    private static APP instance;
    private static Context context;

    public APP() {
        instance = this;
    }

    public static APP getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public static Application getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.initialize(this);
        Utils.setDebug(BuildConfig.DEBUG, "CQUPTLife");
        context = getApplicationContext();
        // 友盟统计key
        AnalyticsConfig.setAppkey(context, API.KEY.UMENG_KEY);
        // 友盟频道
        AnalyticsConfig.setChannel("非法途径");
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
