package com.superbug.moi.cquptlife.model.impl;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 重新写一遍拉取学生信息的model
 * Created by moi on 15/11/18.
 */
public class StudentModel implements IStudentModel {

    // 回调
    private OnStudentListener listener;
    // 查询学生的依据
    private String studentInfo;

    /**
     * 为了去除html返回的烦人的 &nbsp;
     */
    private static String cutNbsp(String html) {
        char[] cut;
        String temp = "";
        cut = html.toCharArray();
        for (char aCut : cut) {
            temp += aCut;
            if (aCut == ';') {
                temp = "";
            }
        }
        return temp;
    }

    @Override
    public void loadStudents(String studentInfo, OnStudentListener listener) {
        this.listener = listener;
        this.studentInfo = studentInfo;

        // 尝试用rx解决异步的问题
        Observable.create(new Observable.OnSubscribe<String>() {
                              @Override
                              public void call(Subscriber<? super String> sub) {
                                  // 下载学生信息
                                  downloadStudents(sub);
                                  Utils.Log("被观察者召唤学生");
                              }
                          }
        ).observeOn(AndroidSchedulers.mainThread()) // 天哪！之前居然忘了切回主线程！
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Utils.Log("观察者收到，准备开始解析");
                        // 解析html
                        analysisStudents(s);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(APP.getContext().getResources().getString(R.string.network_error));
                        e.printStackTrace();
                    }
                });
    }

    // 用于网络拉取学生数据
    private void downloadStudents(Subscriber<? super String> sub) {
        try {
            studentInfo = URLEncoder.encode(studentInfo, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (studentInfo.isEmpty()) {
            return;
        }
        final String finalSearched = studentInfo;

        new Thread(() -> {
            Utils.sendHttpRequest(API.URL.studentId + finalSearched, new Utils.OnHttpEndListener() {
                @Override
                public void onFinish(String response) {
                    Utils.Log("召唤成功，准备返回给观察者");
                    sub.onNext(response);
                    sub.onCompleted();
                }

                @Override
                public void onError(Exception e) {
                    sub.onError(e);
                }
            });
        }).start();
    }

    /**
     * 0：学号 1：姓名 2：性别 3：班级 4：专业 5：院系 6：年级
     */
    // 解析拉取下来的html
    private void analysisStudents(String response) {
        Utils.Log("解析者准备就绪");
        ArrayList<Student> studentList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Elements trs = document.select("tr");
        int totalTrs = trs.size();
        studentList.clear();
        if (totalTrs > 2) {
            for (int i = 1; i < totalTrs; i += 2) {
                Elements tds = trs.get(i).select("td");
                int totalTds = tds.size();
                ArrayList<String> studentInfo = new ArrayList<>();
                for (int j = 0; j < totalTds; j++) {
                    studentInfo.add(cutNbsp(tds.get(j).html()));
                }
                Student mStudent = new Student(studentInfo.get(0), studentInfo.get(1), studentInfo.get(3), studentInfo.get(6), studentInfo.get(4), studentInfo.get(2), studentInfo.get(5));
                studentList.add(mStudent);
                Utils.Log("解析者成功解析，MODEL任务完成，交给PRESENTER处理");
                Utils.Log("看看得到了什么数据：" + mStudent.getStudentName());
                listener.onSuccess(studentList);
            }
        } else {
            listener.onError(APP.getContext().getString(R.string.not_fount_student));
        }
    }
}
