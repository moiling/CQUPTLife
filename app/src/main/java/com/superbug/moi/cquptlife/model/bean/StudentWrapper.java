package com.superbug.moi.cquptlife.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author moi
 */

/*
{
  "total": 1,
  "rows": [
    {
      "xh": "2014213790",
      "xm": "凌霄",
      "xb": "男        ",
      "zyh": "1311",
      "zym": "软件工程类",
      "yxh": "13",
      "yxm": "软件工程学院",
      "nj": "2014      ",
      "bj": "1301401",
      "totalCount": 1
    }
  ]
}
*/

public class StudentWrapper {

    @SerializedName("returnData")
    private List<StudentWrapper.Student> rows;

    public class Student implements Serializable {
        private String xh;
        private String xm;
        private String xb;
        private String zyh;
        private String zym;
        private String yxh;
        private String yxm;
        private String nj;
        private String bj;

        public String getBj() {
            bj = bj.replaceAll(" ", "");
            return bj;
        }

        public void setBj(String bj) {
            bj = bj.replaceAll(" ", "");
            this.bj = bj;
        }

        public String getNj() {
            nj = nj.replaceAll(" ", "");
            return nj;
        }

        public void setNj(String nj) {
            nj = nj.replaceAll(" ", "");
            this.nj = nj;
        }

        public String getXb() {
            xb = xb.replaceAll(" ", "");
            return xb;
        }

        public void setXb(String xb) {
            xb = xb.replaceAll(" ", "");
            this.xb = xb;
        }

        public String getXh() {
            xh = xh.replaceAll(" ", "");
            return xh;
        }

        public void setXh(String xh) {
            xh = xh.replaceAll(" ", "");
            this.xh = xh;
        }

        public String getXm() {
            xm = xm.replaceAll(" ", "");
            return xm;
        }

        public void setXm(String xm) {
            xm = xm.replaceAll(" ", "");
            this.xm = xm;
        }

        public String getYxh() {
            if (yxh != null) yxh = yxh.replaceAll(" ", "");
            return yxh;
        }

        public void setYxh(String yxh) {
            this.yxh = yxh;
        }

        public String getYxm() {
            if (yxm != null) yxm = yxm.replaceAll(" ", "");
            return yxm;
        }

        public void setYxm(String yxm) {
            this.yxm = yxm;
        }

        public String getZyh() {
            if (zyh != null) zyh = zyh.replaceAll(" ", "");
            return zyh;
        }

        public void setZyh(String zyh) {
            this.zyh = zyh;
        }

        public String getZym() {
            if (zym != null) zym = zym.replaceAll(" ", "");
            return zym;
        }

        public void setZym(String zym) {
            this.zym = zym;
        }
    }

    public List<StudentWrapper.Student> getRows() {
        return rows;
    }

    public void setRows(List<StudentWrapper.Student> rows) {
        this.rows = rows;
    }

}
