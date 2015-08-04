package com.superbug.moi.cquptlife.util.listener;

public interface OnHttpEndListener {
    void onFinish(String response);

    void onError(Exception e);
}
