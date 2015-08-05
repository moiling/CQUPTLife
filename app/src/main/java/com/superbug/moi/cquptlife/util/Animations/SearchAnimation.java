package com.superbug.moi.cquptlife.util.Animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.superbug.moi.cquptlife.util.listener.OnAnimationEndListener;

/**
 * Created by moi on 2015/8/4.
 */
public class SearchAnimation {

    public static final int SEARCH_OPEN = 0;
    public static final int SEARCH_CLOSE = 1;

    /**
     * 搜索的动画效果
     *
     * @param view     执行动画的view
     * @param type     类型，分为打开（SEARCH_OPEN）和关闭（SEARCH_CLOSE）
     * @param listener 动画结束后的回调，只有关闭的时候才需要，打开的时候传null就可以了
     */
    public static void start(View view, int type, final OnAnimationEndListener listener) {
        switch (type) {
            case SEARCH_OPEN:
                ValueAnimator showSearchEdit = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1f);
                showSearchEdit.setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator());
                showSearchEdit.start();
                break;
            case SEARCH_CLOSE:
                ValueAnimator removeSearchEdit = ObjectAnimator.ofFloat(view, "ScaleX", 1f, 0f);
                removeSearchEdit.setDuration(500);
                removeSearchEdit.setInterpolator(new AccelerateInterpolator());
                removeSearchEdit.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (listener != null) {
                            listener.onEnd();
                        }
                    }
                });
                removeSearchEdit.start();
                break;
            default:
                break;
        }
    }
}
