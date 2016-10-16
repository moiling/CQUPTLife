package com.superbug.moi.cquptlife.presenter;

import android.util.Log;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.model.RequestManager;
import com.superbug.moi.cquptlife.model.bean.Life;
import com.superbug.moi.cquptlife.model.bean.StudentWrapper;
import com.superbug.moi.cquptlife.model.bean.TeacherWrapper;
import com.superbug.moi.cquptlife.ui.vu.ILifeVu;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * LifePresenter
 * Created by moi on 2015/7/12.
 */
public class LifePresenter {

    private ILifeVu mLifeVu;
    private List<Life> lives = new ArrayList<>();

    public LifePresenter(ILifeVu vu) {
        mLifeVu = vu;
    }

    public void onRelieveView() {
        mLifeVu = null;
    }

    public List<Life> getLives() {
        return lives;
    }

    public void searchLives(String id, final int page, int type) {
        switch (type) {
            case 0: // all
                searchStudents(id, page, true);
                break;
            case 1: // student
                searchStudents(id, page, false);
                break;
            case 2: // teacher
                searchTeachers(id, page, false);
                break;
            default:
                searchStudents(id, page, true);
        }
    }

    private void searchStudents(final String id, final int page, boolean searchTeacher) {
        mLifeVu.showLoading();
        RequestManager.getInstance().searchStudents(new Subscriber<List<StudentWrapper.Student>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mLifeVu.showEmptyView(e.getMessage());
                mLifeVu.hideList();
                mLifeVu.hideLoading();
            }

            @Override
            public void onNext(List<StudentWrapper.Student> students) {
                lives.clear();
                lives.addAll(Life.fromStudents(students));

                if (searchTeacher)
                    searchTeachers(id, page, true);
                else {
                    if (lives.size() > 0) {
                        mLifeVu.showList();
                        mLifeVu.hideEmptyView();
                        mLifeVu.setLives();
                    } else {
                        mLifeVu.showEmptyView(APP.getContext().getResources().getString(R.string.not_fount_student));
                        mLifeVu.hideList();
                    }
                    mLifeVu.hideLoading();
                }
            }
        }, id, page);
    }

    private void searchTeachers(String id, int page, boolean fromStudent) {
        if (!fromStudent) mLifeVu.showLoading();
        RequestManager.getInstance().searchTeachers(new Subscriber<List<TeacherWrapper.Teacher>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (!fromStudent) {
                    mLifeVu.showEmptyView(e.getMessage());
                    mLifeVu.hideList();
                } else {
                    // 处理一下单个报错？
                }
                mLifeVu.hideLoading();
            }

            @Override
            public void onNext(List<TeacherWrapper.Teacher> teachers) {
                if (!fromStudent) lives.clear();
                Log.d("TAG", "onNext: " + teachers.size());
                lives.addAll(Life.fromTeachers(teachers));
                Log.d("TAG", "lives size: " + lives.size());
                if (lives.size() > 0) {
                    mLifeVu.showList();
                    mLifeVu.hideEmptyView();
                    mLifeVu.setLives();
                } else {
                    mLifeVu.showEmptyView(APP.getContext().getResources().getString(R.string.not_fount_student));
                    mLifeVu.hideList();
                }
                mLifeVu.hideLoading();
            }
        }, id, page);
    }
}
