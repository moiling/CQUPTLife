
# 友盟三件套
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

-keep public class com.superbug.moi.cquptlife.R$*{
    public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# retrolambda
-dontwarn java.lang.invoke.*

# picasso
-dontwarn com.squareup.okhttp.**

-keep class butterknife.** {*;}
-dontwarn butterknife.**

-keep class org.apache.http.** {*;}
-dontwarn org.apache.http.**

-keep class sun.** {*;}
-dontwarn sun.**

-keep class javax.** {*;}
-dontwarn javax.**

-keep class com.superbug.moi.cquptlife.ui.activity.** {*;}
-dontwarn com.superbug.moi.cquptlife.ui.activity.**

-keep class org.jsoup.** {*;}
-dontwarn org.jsoup.**

-keep class rx.** {*;}
-dontwarn rx.**


#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment