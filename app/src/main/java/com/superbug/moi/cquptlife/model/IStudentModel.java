package com.superbug.moi.cquptlife.model;

import com.superbug.moi.cquptlife.model.callback.OnStudentListener;

/**
 * StudentModel接口
 * Created by moi on 2015/7/11.
 */
public interface IStudentModel {

    void loadStudents(String studentInfo, OnStudentListener listener);

}
