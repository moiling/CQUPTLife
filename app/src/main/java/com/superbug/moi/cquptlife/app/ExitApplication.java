package com.superbug.moi.cquptlife.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class ExitApplication extends Application {

    private static List<Activity> activityList = new ArrayList<>();
    private static ExitApplication instance;

    private ExitApplication() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
    }

    public static ExitApplication getInstance() {
        if (instance == null) {
            instance = new ExitApplication();
        }
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
