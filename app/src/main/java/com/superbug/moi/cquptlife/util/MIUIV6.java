package com.superbug.moi.cquptlife.util;

import android.app.Activity;
import android.os.Environment;
import android.view.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MIUIV6 {
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

    /**
     * 只支持MIUI V6
     *
     * @param type 0--只需要状态栏透明 1-状态栏透明且黑色字体 2-清除黑色字体
     */
    public static void setStatusBarTextColor(Activity context, int type) {
        if (!isMiUIV6()) {
            return;
        }
        Window window = context.getWindow();
        Class clazz = window.getClass();
        try {
            int tranceFlag;
            int darkModeFlag;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            @SuppressWarnings("unchecked") Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (type == 0) {
                extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
            } else if (type == 1) {
                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
        } catch (Exception ignored) {

        }
    }

    private static boolean isMiUIV6() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
            return "V6".equals(name);
        } catch (final IOException e) {
            return false;
        }
    }

    public static class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

    }

}

