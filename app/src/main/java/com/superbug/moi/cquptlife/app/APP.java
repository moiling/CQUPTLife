package com.superbug.moi.cquptlife.app;

import android.app.Activity;
import android.app.Application;

import com.superbug.moi.cquptlife.BuildConfig;
import com.superbug.moi.cquptlife.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class APP extends Application {

    private static List<Activity> activityList = new ArrayList<>();
    private static APP instance;
    public static APP getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.initialize(this);
        Utils.setDebug(BuildConfig.DEBUG, "CQUPTLife");
    }

    public APP() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
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
