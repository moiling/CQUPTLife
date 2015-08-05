package com.superbug.moi.cquptlife.model.bean;

/**
 * Created by moi on 2015/8/5.
 */
public class HomeItem {
    private String text;
    private int picId;

    public HomeItem(String text, int picId) {
        this.text = text;
        this.picId = picId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
