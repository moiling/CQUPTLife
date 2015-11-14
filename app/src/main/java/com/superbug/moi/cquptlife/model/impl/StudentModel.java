package com.superbug.moi.cquptlife.model.impl;

import android.os.Handler;
import android.os.Message;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.model.callback.OnHttpEndListener;
import com.superbug.moi.cquptlife.model.callback.OnStudentListener;
import com.superbug.moi.cquptlife.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * 在这里进行网络和解析操作
 * Created by moi on 2015/7/11.
 */
public class StudentModel implements IStudentModel {

    private static OnStudentListener listener = null;
    private MyHandler myHandler = new MyHandler();

    private static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    jsoupEvent(msg.obj.toString());
                    break;
                case 1:
                    listener.onError(APP.getContext().getResources().getString(R.string.network_error));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void loadStudents(String studentInfo, OnStudentListener listener) {
        StudentModel.listener = listener;
        searchStudent(studentInfo);
    }

    public void searchStudent(String studentInfo) {
        try {
            //studentInfo = new String(studentInfo.getBytes("GBK"), "iso8859-1");
            studentInfo = URLEncoder.encode(studentInfo, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (studentInfo.isEmpty()) {
            return;
        }
        final String finalSearched = studentInfo;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.sendHttpRequest(API.URL.studentId + finalSearched, new OnHttpEndListener() {
                    @Override
                    public void onFinish(String response) {
                        Message message = new Message();
                        message.obj = response;
                        message.what = 0;
                        myHandler.sendMessage(message);
                        Utils.Log(response);
                    }

                    @Override
                    public void onError(Exception e) {
                        Message message = new Message();
                        message.obj = "";
                        message.what = 1;
                        myHandler.sendMessage(message);
                        Utils.Log(e.toString());
                    }
                });
            }
        }).start();
    }

    /**
     * 0：学号 1：姓名 2：性别 3：班级 4：专业 5：院系 6：年级
     */
    private static void jsoupEvent(String response) {
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
                Utils.Log(mStudent.getStudentName());
                listener.onSuccess(studentList);
            }
        } else {
            listener.onError(APP.getContext().getString(R.string.not_fount_student));
        }
    }

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
}
