package com.superbug.moi.cquptlife.model;

import com.superbug.moi.cquptlife.model.bean.Student;

import java.util.ArrayList;

/**
 * StudentModel接口
 * Created by moi on 2015/7/11.
 */
public interface IStudentModel {

    /**
     * 请求学生信息
     * @param studentInfo 请求数据的依据（学号\姓名）
     * @param listener 回调
     */
    void loadStudents(String studentInfo, OnStudentListener listener);

    // 请求学生信息的回调
    interface OnStudentListener {
        /**
         * 请求成功
         * @param students 请求成功的学生数组
         */
        void onSuccess(ArrayList<Student> students);

        /**
         * 请求失败
         * @param err 请求失败的错误信息
         */
        void onError(String err);

    }
}
