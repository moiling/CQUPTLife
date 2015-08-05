package com.superbug.moi.cquptlife.presenter;

import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.util.listener.OnStudentListener;
import com.superbug.moi.cquptlife.model.impl.StudentModel;
import com.superbug.moi.cquptlife.ui.vu.IStudentVu;

import java.util.ArrayList;

/**
 * Created by moi on 2015/7/12.
 */
public class StudentPresenter {

    private IStudentVu mStudentView;
    private IStudentModel mStudentModel;
    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentPresenter(IStudentVu view) {
        mStudentView = view;
        mStudentModel = new StudentModel();
    }

    public void onRelieveView() {
        mStudentView = null;
    }

    public ArrayList<Student> getStudent() {
        return studentList;
    }

    public void searchStudent(String studentInfo) {
        mStudentModel.loadStudents(studentInfo, new OnStudentListener() {
            @Override
            public void onSuccess(ArrayList<Student> students) {
                studentList.clear();
                studentList.addAll(students);
                mStudentView.setStudents();
            }

            @Override
            public void onError() {

            }
        });
    }
}
