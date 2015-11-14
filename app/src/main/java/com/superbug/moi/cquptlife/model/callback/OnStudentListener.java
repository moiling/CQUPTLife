package com.superbug.moi.cquptlife.model.callback;

import com.superbug.moi.cquptlife.model.bean.Student;

import java.util.ArrayList;

/**
 * 请求学生信息的回调
 * Created by moi on 2015/7/12.
 */
public interface OnStudentListener {

    void onSuccess(ArrayList<Student> students);

    void onError(String err);

}
