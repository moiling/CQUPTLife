package com.superbug.moi.cquptlife.ui.vu;

/**
 * StudentVu接口……
 * Created by moi on 2015/7/11.
 */
public interface ILifeVu {

    String getLifeInfo();

    void setLives();

    void showList();

    void hideList();

    void showEmptyView(String str);

    void hideEmptyView();

    void showLoading();

    void hideLoading();

}
