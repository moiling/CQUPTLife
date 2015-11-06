package com.superbug.moi.cquptlife.config;

public class API {


    public static class URL {
        public static final String studentId = "https://jwzx.cqupt.congm.in/pubBjStu.php?searchKey=";
        public static final String studentPic = "https://jwzx.cqupt.congm.in/showstuPic.php?xh=";
        public static final String studentCETPic = "http://172.22.80.212.cqupt.congm.in/PHOTO0906CET/";
        public static final String studentCETPicEnd = ".JPG";
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
