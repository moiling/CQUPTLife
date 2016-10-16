package com.superbug.moi.cquptlife.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moi
 */

public class Life implements Serializable {

    public enum Type { STUDENT, TEACHER }
    public enum Sex { MALE, FEMALE, TEACHER } // 老师没有性别……
    private Type type;
    private Sex sex;
    private String id; // 学号或者教工号
    private String name;

    // 不要逼我！
    private String info1 = ""; // 年级 || 职称
    private String info2 = ""; // 班级 || 教研所
    private String info3 = ""; // 专业 || 学院
    private String info4 = ""; // 学院

    public Life(StudentWrapper.Student student) {
        this.type = Type.STUDENT;
        this.sex = student.getXb().equals("男") ? Sex.MALE : Sex.FEMALE;
        this.id = student.getXh();
        this.name = student.getXm();
        this.info1 = student.getNj();
        this.info2 = student.getBj();
        this.info3 = student.getZym();
        this.info4 = student.getYxm();
    }

    public Life(TeacherWrapper.Teacher teacher) {
        this.type = Type.TEACHER;
        this.sex = Sex.TEACHER;
        this.id = teacher.getTeaId();
        this.name = teacher.getTeaName();
        this.info1 = teacher.getZc();
        this.info2 = teacher.getJysm();
        this.info3 = teacher.getYxm();
    }

    public static List<Life> fromStudents(List<StudentWrapper.Student> students) {
        List<Life> lives = new ArrayList<>();
        for (StudentWrapper.Student student : students) {
            lives.add(fromStudent(student));
        }
        return lives;
    }

    public static List<Life> fromTeachers(List<TeacherWrapper.Teacher> teachers) {
        List<Life> lives = new ArrayList<>();
        for (TeacherWrapper.Teacher teacher : teachers) {
            lives.add(fromTeacher(teacher));
        }
        return lives;
    }

    public static Life fromStudent(StudentWrapper.Student student) {
        return new Life(student);
    }

    public static Life fromTeacher(TeacherWrapper.Teacher teacher) {
        return new Life(teacher);
    }

    public Sex getSex() {
        return sex;
    }

    public Type getType() {
        return type;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 年级 || 职称
     */
    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    /**
     * @return 班级 || 教研所
     */
    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    /**
     * @return 专业 || 学院
     */
    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    /**
     * @return 学院
     */
    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
