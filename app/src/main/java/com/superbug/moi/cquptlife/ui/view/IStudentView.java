package com.superbug.moi.cquptlife.ui.view;

import com.superbug.moi.cquptlife.model.bean.Student;

import java.util.ArrayList;

/**
 * Created by moi on 2015/7/11.
 */
public interface IStudentView {

    String getStudentInfo();

    void setStudents(ArrayList<Student> students);

}
