package com.superbug.moi.cquptlife.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Created by moi on 11/1/2015.
 */
public class SearchWidgetProvider extends AppWidgetProvider {
    // 每接收一次广播消息就调用一次，使用频繁
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    // 每次更新都调用一次该方法，使用频繁
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    // 每删除一个就调用一次
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    // 当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    // 当最后一个该Widget删除是调用该方法，注意是最后一个
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
