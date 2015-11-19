package com.superbug.moi.cquptlife.model.callback;

public interface OnHttpEndListener {

    void onFinish(String response);

    void onError(Exception e);
}
