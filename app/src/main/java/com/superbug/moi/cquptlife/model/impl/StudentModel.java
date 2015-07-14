package com.superbug.moi.cquptlife.model.impl;

import com.superbug.moi.cquptlife.model.IStudentModel;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.presenter.OnStudentListener;
import com.superbug.moi.cquptlife.util.API;
import com.superbug.moi.cquptlife.util.HttpCallbackListener;
import com.superbug.moi.cquptlife.util.HttpUtil;
import com.superbug.moi.cquptlife.util.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by moi on 2015/7/11.
 */
public class StudentModel implements IStudentModel {

    private ArrayList<Student> studentList = new ArrayList<>();

    @Override public void loadStudents(String studentInfo, OnStudentListener listener) {
        searchStudent(studentInfo, listener);
    }

    public void searchStudent(String studentInfo, final OnStudentListener listener) {
        try {
            studentInfo = new String(studentInfo.getBytes("GBK"), "iso8859-1");
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
                HttpUtil.sendHttpRequest(API.idAPI + finalSearched, new HttpCallbackListener() {
                    @Override public void onFinish(String response) {
                        jsoupEvent(response);
                        listener.onSuccess(studentList);
                    }

                    @Override public void onError(Exception e) {
                        //TODO 错误提示
                        listener.onError();
                        LogUtils.d("!!!!!!!", e.toString());
                    }
                });
            }
        }).start();
    }

    /**
     * 0：学号 1：姓名 2：性别 3：班级 4：专业 5：院系 6：年级
     */
    private void jsoupEvent(String response) {
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
                Student mStudent = new Student(studentInfo.get(0), studentInfo.get(1), studentInfo.get(3),
                                               studentInfo.get(6), studentInfo.get(4), studentInfo.get(2), studentInfo.get(5));
                studentList.add(mStudent);
            }
        } else {
            //TODO 错误提示
        }
    }

    /**
     * 为了去除html返回的烦人的 &nbsp;
     */
    private String cutNbsp(String html) {
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
