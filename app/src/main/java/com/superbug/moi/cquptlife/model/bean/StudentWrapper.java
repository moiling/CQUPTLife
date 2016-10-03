package com.superbug.moi.cquptlife.model.bean;

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
    private int total;
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
        private int totalCount;

        public String getBy() {
            bj = bj.replaceAll(" ", "");
            return bj;
        }

        public void setBy(String by) {
            by = by.replaceAll(" ", "");
            this.bj = by;
        }

        public String getNj() {
            nj = nj.replaceAll(" ", "");
            return nj;
        }

        public void setNj(String nj) {
            nj = nj.replaceAll(" ", "");
            this.nj = nj;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
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
            yxh = yxh.replaceAll(" ", "");
            return yxh;
        }

        public void setYxh(String yxh) {
            yxh = yxh.replaceAll(" ", "");
            this.yxh = yxh;
        }

        public String getYxm() {
            yxm = yxm.replaceAll(" ", "");
            return yxm;
        }

        public void setYxm(String yxm) {
            yxm = yxm.replaceAll(" ", "");
            this.yxm = yxm;
        }

        public String getZyh() {
            zyh = zyh.replaceAll(" ", "");
            return zyh;
        }

        public void setZyh(String zyh) {
            zyh = zyh.replaceAll(" ", "");
            this.zyh = zyh;
        }

        public String getZym() {
            zym = zym.replaceAll(" ", "");
            return zym;
        }

        public void setZym(String zym) {
            zym = zym.replaceAll(" ", "");
            this.zym = zym;
        }
    }

    public List<StudentWrapper.Student> getRows() {
        return rows;
    }

    public void setRows(List<StudentWrapper.Student> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
