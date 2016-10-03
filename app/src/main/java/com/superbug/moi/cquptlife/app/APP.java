package com.superbug.moi.cquptlife.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.superbug.moi.cquptlife.BuildConfig;
import com.superbug.moi.cquptlife.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class APP extends Application {

    private static List<Activity> activityList = new ArrayList<>();// 存放所有开启的activity
    private static APP instance;// Application的实例
    private static Context context;// Application的上下文

    public APP() {
        instance = this;
    }

    /**
     * 获得app的实例
     * @return app实例
     */
    public static APP getInstance() {
        return instance;
    }

    /**
     * 全局获取context
     * @return app的context
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /* 初始化工具 */
        Utils.initialize(this);
        Utils.setDebug(BuildConfig.DEBUG, "CQUPTLife");
        context = getApplicationContext();// 为全局获取context制造条件
    }

    /**
     * 将开启的activity放在自己的activity数组中
     * @param activity 开启的activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 将关闭的activity从自己的activity数组中移除
     * @param activity 要关闭的activity
     */
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 将activity数组中所有activity关闭
     * app随时随地完全退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
