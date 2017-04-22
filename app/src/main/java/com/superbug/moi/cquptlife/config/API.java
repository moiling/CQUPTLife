package com.superbug.moi.cquptlife.config;

public class API {

    public static class URL {
        // 内网外入后教务在线的查询学生信息接口
        public static final String END = "http://jwzx.host.congm.in:88";
        public static final String searchStudent = "/jwzxtmp/data/json_StudentSearch.php";
        public static final String searchTeacher = "/jwzxtmp/data/json_teacherSearch.php";
        // 废弃
        public static final String studentId = "http://jwzx.cqupt.congm.in/pubBjStu.php?searchKey=";
        // 内网外入后教务在线的查询学生一卡通照片的接口
        public static final String studentPic = "http://jwzx.cqupt.congm.in/showstuPic.php?xh=";
        // 内网外人后教务在线的查询学生四六级照片的接口
        public static final String studentCETPic = "http://172.22.80.212.cqupt.congm.in/PHOTO0906CET/";
        // 查询四六级照片尾缀
        public static final String studentCETPicEnd = ".JPG";
    }

    public static class KEY {

        // 四六级照片密码
        public static String CET_PIC_KEY;
        // 一卡通照片密码
        public static String NORMAL_PIC_KEY;
        // 友盟key
        public static final String UMENG_KEY = "5645d0f367e58e3d88000d08";

        static {
            CET_PIC_KEY = "lingxiaoisthebest";
            NORMAL_PIC_KEY = "lingxiao9635";
        }
    }

    // 友盟事件的id
    public static class UMENG_EVENT_ID {
        // 搜索学生的网络请求时触发
        public static final String SEARCH_STUDENT = "SEARCH_STUDENT";
    }
}