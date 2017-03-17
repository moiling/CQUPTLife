package com.superbug.moi.cquptlife.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author moi
 */

public class TeacherWrapper {

    /**
     * teaId : 010237
     * teaName : 王永(通)
     * jys : 0105
     * jysm : 通信工程系
     * yxm : 通信与信息工程学院
     * zc : 讲师（高校）
     * totalCount : 105
     */

    @SerializedName("returnData")
    private List<Teacher> rows;

    public List<Teacher> getRows() {
        return rows;
    }

    public void setRows(List<Teacher> rows) {
        this.rows = rows;
    }

    public static class Teacher implements Serializable {
        private String teaId;
        private String teaName;
        private String jys;
        private String jysm;
        private String yxm;
        private String zc;

        public String getTeaId() {
            teaId = teaId.replaceAll(" ", "");
            return teaId;
        }

        public void setTeaId(String teaId) {
            this.teaId = teaId;
        }

        public String getTeaName() {
            teaName = teaName.replaceAll(" ", "");
            return teaName;
        }

        public void setTeaName(String teaName) {
            this.teaName = teaName;
        }

        public String getJys() {
            jys = jys.replaceAll(" ", "");
            return jys;
        }

        public void setJys(String jys) {
            this.jys = jys;
        }

        public String getJysm() {
            jysm = jysm.replaceAll(" ", "");
            return jysm;
        }

        public void setJysm(String jysm) {
            this.jysm = jysm;
        }

        public String getYxm() {
            yxm = yxm.replaceAll(" ", "");
            return yxm;
        }

        public void setYxm(String yxm) {
            this.yxm = yxm;
        }

        public String getZc() {
            if (zc != null) {
                zc = zc.replaceAll(" ", "").replaceAll("（", "(").replaceAll("）", ")");
            } else {
                zc = "教师";
            }
            return zc;
        }

        public void setZc(String zc) {
            this.zc = zc;
        }

    }
}
