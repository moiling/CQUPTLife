package com.superbug.moi.cquptlife.presenter;

import com.superbug.moi.cquptlife.model.bean.Student;

import java.util.ArrayList;

/**
 * Created by moi on 2015/7/12.
 */
public interface OnStudentListener {

    void onSuccess(ArrayList<Student> students);

    void onError();

}
