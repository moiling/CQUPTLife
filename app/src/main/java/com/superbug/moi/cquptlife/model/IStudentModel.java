package com.superbug.moi.cquptlife.model;

import com.superbug.moi.cquptlife.model.bean.Student;

import java.util.ArrayList;

/**
 * StudentModel接口
 * Created by moi on 2015/7/11.
 */
public interface IStudentModel {

    void loadStudents(String studentInfo, OnStudentListener listener);

    /**
     * 请求学生信息的回调
     * Created by moi on 2015/7/12.
     */
    interface OnStudentListener {

        void onSuccess(ArrayList<Student> students);

        void onError(String err);

    }
}
