package com.superbug.moi.cquptlife.config;


public class API {


    public static class URL {
        public static final String studentId = "http://jwzx.cqupt.edu.cn/pubBjStu.php?searchKey=";
        public static final String studentPic = "http://jwzx.cqupt.edu.cn/showstuPic.php?xh=";
    }

    public static class KEY {
        public static final String STATUS = "status";
        public static final String INFO = "info";
        public static final String DATA = "data";
    }

    public static class CODE {
        public static final int SUCCEED = 200;
        public static final int Failure = 0;
        public static final int PERMISSION_DENIED = 213;
    }
}
