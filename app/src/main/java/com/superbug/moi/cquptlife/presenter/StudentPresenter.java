package com.superbug.moi.cquptlife.presenter;

import android.content.Context;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.model.callback.OnStudentListener;
import com.superbug.moi.cquptlife.model.impl.StudentModel;
import com.superbug.moi.cquptlife.ui.vu.IStudentVu;
import com.superbug.moi.cquptlife.util.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * StudentPresenter
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
        // 友盟记录
        MobclickAgent.onEvent((Context) mStudentView, API.UMENG_EVENT_ID.SEARCH_STUDENT, "查找内容为： " + studentInfo);
        mStudentView.showLoading();
        Utils.Log("PRESENTER发出命令，MODEL开始召唤学生");
        mStudentModel.loadStudents(studentInfo, new OnStudentListener() {
            @Override
            public void onSuccess(ArrayList<Student> students) {
                studentList.clear();
                if (students.size() > 0) {
                    Utils.Log("召唤成功，VIEW请显示:" + students.get(0).getStudentName());
                    Utils.Log("不会是线程的问题吧？！id: " + Thread.currentThread().getId());
                    mStudentView.showList();
                    mStudentView.hideEmptyView();
                    studentList.addAll(students);
                    mStudentView.setStudents();
                } else {
                    mStudentView.showEmptyView(APP.getContext().getResources().getString(R.string.not_fount_student));
                    mStudentView.hideList();
                }
                Utils.Log("召唤成功，VIEW请收起小陀螺");
                mStudentView.hideLoading();
            }

            @Override
            public void onError(String err) {
                mStudentView.showEmptyView(err);
                mStudentView.hideList();
                mStudentView.hideLoading();
            }
        });
    }
}
