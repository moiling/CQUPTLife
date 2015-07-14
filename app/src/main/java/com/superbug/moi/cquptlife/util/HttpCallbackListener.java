package com.superbug.moi.cquptlife.util;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
