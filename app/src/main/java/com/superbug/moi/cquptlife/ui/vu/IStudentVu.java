package com.superbug.moi.cquptlife.ui.vu;

/**
 * Created by moi on 2015/7/11.
 */
public interface IStudentVu {

    String getStudentInfo();

    void setStudents();

    void showList();

    void hideList();

    void showEmptyView(String str);

    void hideEmptyView();

    void showLoading();

    void hideLoading();

}
