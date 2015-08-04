package com.superbug.moi.cquptlife.presenter;

import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.util.listener.OnStudentListener;
import com.superbug.moi.cquptlife.model.impl.StudentModel;
import com.superbug.moi.cquptlife.ui.view.IStudentView;

import java.util.ArrayList;

/**
 * Created by moi on 2015/7/12.
 */
public class StudentPresenter {

    private IStudentView mStudentView;
    private IStudentModel mStudentModel;

    public StudentPresenter(IStudentView view) {
        mStudentView = view;
        mStudentModel = new StudentModel();
    }

    public void onRelieveView() {
        mStudentView = null;
    }

    public void searchStudent(String studentInfo) {
        mStudentModel.loadStudents(studentInfo, new OnStudentListener() {
            @Override
            public void onSuccess(ArrayList<Student> students) {
                mStudentView.setStudents(students);
            }

            @Override
            public void onError() {

            }
        });
    }
}
