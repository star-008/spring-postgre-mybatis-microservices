package com.tianwen.springcloud.scoreapi.constant;

import java.util.Arrays;

public interface ICommonConstants {
    /*********************** action type start *************************/

    /*
    action type 0 :
     */
    public final int ACTION_UPLOAD = 1;

    /*
    action type 0 :
     */
    public final int ACTION_COLLECT = 2;

    /*
    action type 0 :
     */
    public final int ACTION_RATING = 3;

    /*
    action type 0 :
     */
    public final int ACTION_VOTE = 4;

    /*
    action type 0 :
     */
    public final int ACTION_ADD_FAVOURIT = 5;

    /*
    action type 0 :
     */
    public final int ACTION_REPORT = 6;

    /*
    action type 0 :
     */
    public final int ACTION_DOWNLOAD = 7;

    /*
    action type 0 :
     */
    public final int ACTION_REWARD = 8;

    /*
    action type 0 :
     */
    public final int ACTION_USER_INFO_COMPLETE = 9;

    /*
    action type 0 :
     */
    public final int ACTION_FIRST_LOGIN = 10;

    /*********************** action type end *************************/

    /*********************** user role type start *************************/
    /**
     * user login type: 0 visitor
     */
    public final int USER_VISITOR =  0;

    /**
     * user login type: 1 teacher
     */
    public final int USER_TEACHER = 1;

    /**
     * user login type: 2 student
     */
    public final int USER_STUDENT = 2;

    /**
     * user login type: 3 parent
     */
    public final int USER_MANAGER = 3;
    /*********************** user role type end *************************/

    /*********************** optiontype begin *************************/

    final int OPTION_OPTIONTYPE_DOWNLOAD = 1;

    final int OPTION_OPTIONTYPE_PURCHASE = 2;

    final int OPTION_OPTIONTYPE_MODIFY = 3;

    final int OPTION_OPTIONTYPE_ADD = 4;

    final int OPTION_OPTIONTYPE_DELETE = 5;

    final int OPTION_OPTIONTYPE_AUDIT_ALLOW = 6;

    final int OPTION_OPTIONTYPE_FEEDBACKERROR = 7;

    final int OPTION_OPTIONTYPE_FEEDBACK = 8;

    final int OPTION_OPTIONTYPE_COLLECTION_NEW = 9;

    final int OPTION_OPTIONTYPE_COLLECTION_IN = 10;

    final int OPTION_OPTIONTYPE_REWARD_NEW = 11;

    final int OPTION_OPTIONTYPE_REWARD_IN = 12;

    final int OPTION_OPTIONTYPE_UPLOAD = 13;

    final int OPTION_OPTIONTYPE_FAVORITE_ADD = 14;

    final int OPTION_OPTIONTYPE_FAVORITE_DELETE = 15;

    final int OPTION_OPTIONTYPE_CHARGE = 16;

    final int OPTION_OPTIONTYPE_BATCHIMPORT = 17;

    final int OPTION_OPTIONTYPE_PACKAGE_ADD = 18;

    final int OPTION_OPTIONTYPE_PRICESET = 19;

    final int OPTION_OPTIONTYPE_ALLOWSELL = 20;

    final int OPTION_OPTIONTYPE_DENYSELL = 21;

    final int OPTION_OPTIONTYPE_PAY = 22;

    final int OPTION_OPTIONTYPE_PRICEMODIFY = 23;

    final int OPTION_OPTIONTYPE_CATALOG_ADD = 24;

    final int OPTION_OPTIONTYPE_CATALOG_DELETE = 25;

    final int OPTION_OPTIONTYPE_CATALOG_MODIFY = 26;

    final int OPTION_OPTIONTYPE_CATALOG_MOVE = 27;

    final int OPTION_OPTIONTYPE_SUBNAVI_ADD = 28;

    final int OPTION_OPTIONTYPE_SUBNAVI_DELETE = 29;

    final int OPTION_OPTIONTYPE_SUBNAVI_MODIFY = 30;

    final int OPTION_OPTIONTYPE_SCHOOLNAMED = 31;

    final int OPTION_OPTIONTYPE_SCHOOLNORMAL = 32;

    final int OPTION_OPTIONTYPE_PACKAGE_MODIFY = 33;

    final int OPTION_OPTIONTYPE_PACKAGE_DELETE = 34;

    final int OPTION_OPTIONTYPE_AUDIT_DENY = 35;

    final int OPTION_OPTIONTYPE_COLLECTION_MODIFY = 36;

    final int OPTION_OPTIONTYPE_COLLECTION_DELETE = 37;

    final int OPTION_OPTIONTYPE_REWARD_MODIFY = 38;

    final int OPTION_OPTIONTYPE_REWARD_DELETE = 39;

    /*********************** optiontype end *************************/

    /*********************** actiontype start *************************/

    final int ACTION_ACTIONTYPE_NAMEDSCHOOL_CONTENT = 0;

    final int ACTION_ACTIONTYPE_SYNCHRO = 1;

    final int ACTION_ACTIONTYPE_PAPER = 2;

    final int ACTION_ACTIONTYPE_SPECIAL = 3;

    final int ACTION_ACTIONTYPE_MICRO = 4;

    final int ACTION_ACTIONTYPE_ENGLISH = 5;

    final int ACTION_ACTIONTYPE_STEM = 6;

    final int ACTION_ACTIONTYPE_BEAUTY = 7;

    final int ACTION_ACTIONTYPE_LOGO = 8;

    final int ACTION_ACTIONTYPE_SUBJECT = 9;

    final int ACTION_ACTIONTYPE_FINESCHOOL = 10;

    final int ACTION_ACTIONTYPE_COLLECTION = 11;

    final int ACTION_ACTIONTYPE_REWARD = 12;

    final int ACTION_ACTIONTYPE_ESTIMATE = 13;

    final int ACTION_ACTIONTYPE_UPLAOD = 14;

    /*********************** actiontype start *************************/

    /*********************** content type start *************************/

    /**
     * excel format
     */
    final String CONTENT_TYPE_EXCEL = "xls";

    /**
     * word format
     */
    final String CONTENT_TYPE_WORD = "docx";

    /**
     * 课件
     */
    final String CONTENT_TYPE_CURRICULUM = "ppt,pptx,pdf";

    /**
     *教学设计
     */
    final String CONTENT_TYPE_TEACHING_PLAN = "doc,docx,pdf";

    /**
     *拓展文本
     */
    final String CONTENT_TYPE_EXTEND_TEXT = "doc,docx,pdf";

    /**
     *试卷
     */
    final String CONTENT_TYPE_PAPER = "doc,docx,pdf";

    /**
     *图片
     */
    final String CONTENT_TYPE_IMAGE = "jpg,png,gif";

    /**
     *视频
     */
    final String CONTENT_TYPE_VIDEO = "mp4,flv,wmv,avi";

    /**
     *音频
     */
    final String CONTENT_TYPE_AUDIO = "mp3,wma";

    /**
     *动画(SWF)
     */
    final String CONTENT_TYPE_CARTOON1 = "flv,swf";

    /**
     *动画(H5)
     */
    final String CONTENT_TYPE_CARTOON2 = "zhtml";

    /**
     *电子书
     */
    final String CONTENT_TYPE_EBOOK = "epub,pdf";

    /**
     *压缩文件
     */
    final String CONTENT_TYPE_ZIP = "zip";

    /**
     *其他
     */
    final String CONTENT_TYPE_OTHER = "*";

    /**
     *试题
     */
    final String CONTENT_TYPE_QUESTION1 = "*";

    /**
     *试题(结构化)
     */
    final String CONTENT_TYPE_QUESTION2 = "*";

    /**
     *教材
     */
    final String CONTENT_TYPE_BOOK = "*";

    /*********************** content type start *************************/


    final String RESPONSE_RESULT_SUCCESS = "200";
    final String ERROR_CODE_USER_NOT_EXIST = "1003";

    /**
     * ECO平台有关
     */
    final String ECO_PLATFORM_AUTH_URL = "/openapi-uc/oauth/token";
    final String ECO_PLATFORM_REGISTER_URL = "/openapi-base/base/register";
    final String ECO_PLATFORM_USERBYTOKEN_URL = "/openapi-uc/uc/getUserByToken";
    final String ECO_PLATFORM_STUDENT_INFO = "/openapi-base/base/getStudentInfo";
    final String ECO_PLATFORM_TEACHER_INFO = "/openapi-base/base/getTeacherInfo";
    final String ECO_PLATFORM_CLASS_INFO = "/openapi-base/base/getClass";
    final String ECO_PLATFORM_SCHOOL_INFO = "/openapi-base/base/getSchool";
    final String ECO_PLATFORM_TEACHING_INFO = "/openapi-base/base/queryTeachs";
    final String ECO_PLATFORM_ORGANIZATION_INFO = "/openapi-uc/org";
    final String ECO_FILE_UPlOAD_URL = "/twasp/fs/fs/file/upload";
    final String ECO_FILE_DOWNLOAD_URL = "/fs/media/";

    final int SERVER_SYSTEM_TYPE = 1;    /* 0 : SCORE, 1: ECO */
    final int OPTION_LIMIT = 5000;

    final int EXAM_DEFAULT_RECENT_COUNT = 3;

    public final String SCORE_NAME = "分数";
    public final String TOTAL_SCORE_NAME = "总分";
    public final String YEAR_TOTAL_NAME = "年级";

    public final String TOTAL_CLASS_ID = "TOTAL";
    public final String TOTAL_SUBJECT_ID = "TOTAL";

    public final String TOTAL_SCORE = "总成绩";
    public final String TOTAL_SCORE_NORMAL = "常规总成绩";
    public final String SCORE = "分";
    public final String RANK = "名次";
    public final String SPAN = "间隔";

    public final String TOTAL = "总分";
    public final String CLASS_AVG_SCORE = "班平均分";
    public final String CLASS_AVG_RANK_IN_GRADE = "班平均分年级排名";
    public final String GRADE_AVG_SCORE = "年级平均分";
    public final String GRADE_TOP = "年级前";
    public final String GRADE_LAST = "年级后";
    public final String GRADE_COUNT = "名人数";
    public final String COUNT = "人数";
    public final String MAX_SCORE = "最高分";
    public final String MIN_SCORE = "最低分";
    public final String AVG_SCORE = "平均分";
    public final String DIFFERENCE = "标准差";
    public final String SUB_TOTAL = "小计";
    public final String TOTAL_AMOUNT = "累计";

    public final String DIFFICULTY = "难度";

    public final String SUBJECT_NAME = "科目";
    public final String CLASS_NAME = "班级";
    public final String TEACHER_NAME = "任课老师";

    public final String EXAM_TOTAL_COUNT = "应考人数";
    public final String EXAM_APPlY_COUNT = "实考人数";

    public final String STUDENT_TOTAL_COUNT = "总人数";
    public final String STUDENT_APPlY_COUNT = "参加人数";

    public final String ALL = "全体";
    public final String TOP_N = "前{count}";
    public final String LAST_N = "后{count}";

    public final String FULL_SCORE = "满分";

    public final String STUDENT_COUNT = "人数";

    public final String PERCENT = "比率";

    public final String AVG_SCORE_RATE = "比均";
    public final String AVG_SCORE_RATE_OVER = "超均";

    public final String BEST_SCORE = "优秀";
    public final String BEST_SCORE_PERCENT = "优秀率";

    public final String BETTER_SCORE = "良好";
    public final String BETTER_SCORE_COUNT = "良好人数";
    public final String BETTER_SCORE_PERCENT = "良好率";

    public final String GOOD_SCORE_COUNT = "及格人数";
    public final String GOOD_SCORE_PERCENT = "及格率";

    public final String PASS_SCORE = "优良";
    public final String PASS_SCORE_COUNT = "优良人数";
    public final String PASS_SCORE_PERCENT = "优良率";

    public final String BAD_SCORE = "低分";
    public final String BAD_SCORE_COUNT = "低分人数";
    public final String BAD_SCORE_PERCENT = "低分率";

    public final String VOLUME = "卷";

    public final String EXAM_MISSED = "缺考";

    public final String CLASS_TYPE_NAME_NORMAL = "行政班";

    public final String CLASS_TYPE_NAME_SPECIAL = "教学班";

    public static String getClassType(String classTypeName) {
        String[] classTypeNames = {CLASS_TYPE_NAME_NORMAL, CLASS_TYPE_NAME_SPECIAL};
        return String.valueOf(Arrays.asList(classTypeNames).indexOf(classTypeName) + 1);
    }

    public static String getClassTypeName(int index) {
        String[] classTypeNames = {CLASS_TYPE_NAME_NORMAL, CLASS_TYPE_NAME_SPECIAL};
        return classTypeNames[index];
    }
}
