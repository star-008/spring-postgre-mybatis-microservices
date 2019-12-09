package com.tianwen.springcloud.scoreapi.constant;

public interface IErrorMessageConstants {
    // success code
    public final String OPERATION_SUCCESS = "200";

    //token not correct error code
    public final String ERR_TOKEN_EMPTY = "-99";

    //token not correct error code
    public final String ERR_TOKEN_NOT_CORRECT = "-100";

    //error code
    public final String ERR_TOKEN_VALIDATION_MOVE = "-107";

    //验证 error code
    public final String ERR_TOKEN_TIMEOUT = "-108";

    //parameter invalid code
    public final String ERR_PARAMETER_INVALID = "-109";

    // database save failed
    public final String ERR_DB_SAVE_FAILED = "-111";

    //user score not enough
    public final String ERR_SCORE_NOT_ENOUGH = "-112";

    //not good
    public final String ERR_NOT_GOODS = "-113";

    //user not student
    public final String ERR_CODE_NOT_STUDENT = "-114";

    //file not exist
    public final String ERR_CODE_FILE_NOT_EXIST = "-115";

    //
    public final String ERR_CODE_NOT_JOIN = "-116";

    //error code : vote teacher more one time
    public final String ERR_CODE_VOTE_TEACHER_MORE = "-117";

    //error code : vote teacher more five times on today
    public final String ERR_CODE_VOTE_TEACHER_TODAY = "-118";

    String ERR_CODE_EXAM_NOT_FOUND = "-1101";
    String ERR_CODE_EXAM_DUPLICATED = "-1102";
    String ERR_CODE_EXAM_DATE_DUPLICATED = "-1103";
    String ERR_CODE_EXAM_ID_NO_MATCH = "-1104";

    String ERR_CODE_EXAM_SUBJECT_SCORE= "-1200";
    String ERR_CODE_EXAM_SUBJECT_NOT_FOUND = "-1201";
    String ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_EMPTY = "-1202";
    String ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID = "-1203";
    String ERR_CODE_EXAM_SUBJECT_STUDENT_EXAM_MISSED = "-1204";
    String ERR_CODE_EXAM_SUBJECT_PUBLISH_DENIED = "-1205";
    String ERR_CODE_EXAM_SUBJECT_UNPUBLISH_DENIED = "-1206";
    String ERR_CODE_EXAM_SUBJECT_SYS_ANALYSIS_NOT_READY = "-1207";
    String ERR_CODE_EXAM_SUBJECT_IMPORT_DISABLED = "-1208";
    String ERR_CODE_EXAM_SUBJECT_ALREADY_PUBLISHED = "-1209";
    String ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_NON_ENTERED = "-1210";

    String ERR_CODE_EXAM_PART_SCORE= "-1300";
    String ERR_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH = "-1301";
    String ERR_CODE_EXAM_PART_SCORE_GRADE_NOT_FOUND = "-1302";
    String ERR_CODE_EXAM_PART_SCORE_SUBJECT_NOT_FOUND = "-1303";
    String ERR_CODE_EXAM_PART_SCORE_TOTAL_SCORE_NO_MATCH = "-1304";
    String ERR_CODE_EXAM_PART_SCORE_EMPTY_CELL = "-1305";
    String ERR_CODE_EXAM_PART_SCORE_INVALID_NUMBER = "-1306";
    String ERR_CODE_EXAM_PART_SCORE_TOTAL_SCORE_INVALID = "-1307";
    String ERR_CODE_EXAM_PART_SCORE_SMALLNO_DUPLICATED = "-1308";

    String ERR_CODE_STUDENT_SUBJECT_SCORE= "-1400";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_EXAM_NAME_NO_MATCH = "-1401";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_GRADE_NOT_FOUND = "-1402";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_GRADE_NO_MATCH = "-1403";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NO_MATCH = "-1404";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_TOTAL_SCORE_NO_MATCH = "-1405";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_STUDENT_NOT_FOUND= "-1406";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_ONLY_TEACHER = "-1407";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_DENIED = "-1408";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_ALREADY_IMPORTED = "-1409";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID = "-1410";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID1 = "-1411";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_CLASS_TYPE_INVALID = "-1412";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_EMPTY = "-1413";
    String ERR_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_INVALID = "-1414";

    String ERR_CODE_STUDENT_PART_SCORE= "-1500";
    String ERR_CODE_STUDENT_PART_SCORE_SCORE_INVALID = "-1505";
    String ERR_CODE_STUDENT_PART_SCORE_TOTAL_SCORE_NO_MATCH = "-1506";
    String ERR_CODE_STUDENT_PART_SCORE_SCORE_INVALID1 = "-1507";

    /**
     *
     */
    public final String ERR_MSG_NOT_JOIN = "不能参与活动！";

    /**
     * user score not enough
     */
    public final String ERR_MESSAGE_SCORE_NOT_ENOUGH = "积分不够!";

    /**
     * not goods
     */
    public final String ERR_MESSAGE_NOT_GOODS="还没生成商品!";

    /**
     * user not exist
     */
    public final String ERR_MESSAGE_USER_NOT_EXIST="此用户不存在！";

    /**
     * Captcha is empty
     */
    public final String ERR_MESSAGE_CAPTCHA_REQUIRED="请输入登验证码。";

    /**
     * Captcha is invalid
     */
    public final String ERR_MESSAGE_CAPTCHA_INVALID="验证码错误";

    /**
     * role incorrect
     */
    public final String ERR_MESSAGE_USER_ROLE_INCORRECT="请使用正确类型登录！";

    /*
    * resource not exist
    * */
    public final String ERR_MESSAGE_RESOURCE_NOT_EXIST = "资源不存在!";

    /*
    * resource file not exist
    * */
    public final String ERR_MESSAGE_FILE_NOT_EXIST = "文件不存在!";

    /*
    * navigation not exist
    * */
    public final String ERR_MESSAGE_SUBNAVI_NOT_EXIST = "没有找到该学科导航！";

    /*
    * catalog not exist
    * */
    public final String ERR_MESSAGE_GOODS_NOT_EXIST = "商品不存在！";

    /**
     * content type is not correct
     */
    public final String ERR_MESSAGE_CONTENT_TYPE_NOT_CORRECT="资源类型错";

    /*
    * schoolsection not exist
    * */
    public final String ERR_MESSAGE_SCHOOLSECTION_NOT_EXIST = "学段名错";

    /*
    * catalog not exist
    * */
    public final String ERR_MESSAGE_SUBJECT_NOT_EXIST = "学科名错";


    /*
    * catalog not exist
    * */
    public final String ERR_MESSAGE_GRADE_NOT_EXIST = "学年名错";


    /*
    * catalog not exist
    * */
    public final String ERR_MESSAGE_EDITIONTYPE_NOT_EXIST = "版本名错";

    /*
    * bookmodel not exist
    * */
    public final String ERR_MESSAGE_BOOKMODEL_NOT_EXIST = "册别名错";

    /*
    * onelabel not exist
    * */
    public final String ERR_MESSAGE_ONELABEL_NOT_EXIST = "一级标签名错";

    /*
    * twolabel not exist
    * */
    public final String ERR_MESSAGE_TWOLABEL_NOT_EXIST = "二级标签名错";


    /*
    * catalog not exist
    * */
    public final String ERR_MESSAGE_CATALOG_NOT_EXIST = "章节名错";

    /*
    token is empty
     */
    public final String ERR_MESSAGE_TOKEN_EMPTY = "令牌为空！";

    /*
    token not correct
     */
    public final String ERR_MESSAGE_TOKEN_NOT_CORRECT = "令牌不正确！";

    /*
    token timeout
     */
    public final String ERR_MESSAGE_TOKEN_TIMEOUT = "您的会话超时，请重新登录！";

    /*
    didn't log in
     */
    public final String ERR_MESSAGE_NOT_LOGIN = "您需要登录。";

    /**
     * permission error
     */
    public final String ERR_MESSAGE_PERMISSION = "您没有权限！";

    /**
     * parameter invalide
     */
    public final String ERR_MESSAGE_PARAMETER_INVALID = "参数不正确！";

    /**
     * parameter activityid is empty
     */
    public final String ERR_MESSAGE_PARAMETER_ACTIVITY_ID = "活动ID参数为空！";


    /*
    save failed
     */
    public final String ERR_MESSAGE_NOT_SAVED = "不能保存！";

    /*
    resource name duplicated
     */
    public final String ERR_MESSAGE_DUPLICATE_NAME= "登录名重复";

    /*
    resource name duplicated
     */
    public final String ERR_MESSAGE_RESOURCE_ALREADY_EXIST= "登录文件重复!";

    //评星 error message
    public final String ERR_MESSAGE_RATING = "只能评星一次!";

    //点赞 error message
    public final String ERR_MESSAGE_VOTE = "只能点赞一次!";

    //点赞 error message
    public final String ERR_MESSAGE_OVER_VOTE_TODAY = "每天只能点赞五次!";

    // success message
    public final String OPERATION_SUCCESS_MESSAGE = "操作成功";

    /**
     * user not have permission
     */
    public final String ERR_MSG_NOT_HAVE_PERMISSION = "您没有权限!";

    // database save failed
    public final String ERR_MESAGE_DB_SAVE_FAILED = "数据库保存失败！";

    //other user logined as that user
    public final String ERR_MESSAGE_TOKEN_VALIDATION_MOVE = "对不起，该用户名已经登录";

    String ERR_MESSAGE_CODE_EXAM_NOT_FOUND = "不能找到考试";
    String ERR_MESSAGE_CODE_EXAM_ID_NOT_FOUND = "不能找到考试ID";
    String ERR_MESSAGE_CODE_EXAM_ID_NO_MATCH = "考试ID不配合不能导入。";
    String ERR_MESSAGE_CODE_EXAM_DUPLICATED = "考试名称不能重复";
    String ERR_MESSAGE_CODE_EXAM_DATE_DUPLICATED = "考试日期不能重复";

    String ERR_MESSAGE_CODE_EXAM_SUBJECT_SCORE= "考试学科错误";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_NOT_FOUND = "没有找到考试学科";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_EMPTY = "录入成绩没完成了。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID = "{className}{studentName}学生的{subjectName}总成绩高比满分。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_EXAM_MISSED = "一部分学生缺考。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_PUBLISH_DENIED = "登录的用户没有权限发布。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_UNPUBLISH_DENIED = "登录的用户没有权限取消发布。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_SYS_ANALYSIS_NOT_READY = "System analysis parameters are not set yet.";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_IMPORT_DISABLED = "导入小题分析关了。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_ALREADY_PUBLISHED = "已经发布了。";
    String ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_NON_ENTERED = "No one score has been entered。 There should be one score entered at least.";

    String ERR_MESSAGE_CODE_EXAM_PART_SCORE= "题分不对";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH = "成绩模板中的考试和选中的考试名称不一致！";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_GRADE_NOT_FOUND = "年级不对";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_SUBJECT_NOT_FOUND = "学科不对";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_TOTAL_SCORE_INVALID = "总分不是数值。";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_TOTAL_SCORE_NO_MATCH = "总分不对";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_EMPTY_CELL = "导入文件的位置{cellName}的内容为空。";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_INVALID_NUMBER = "导入文件的位置{cellName}的内容不是数值。";
    String ERR_MESSAGE_CODE_EXAM_PART_SCORE_SMALLNO_DUPLICATED = "导入文件的位置{cellName}的小题编号重复。";

    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE= "科目总分不对";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_EXAM_NAME_NO_MATCH = "考试不对";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_GRADE_NOT_FOUND = "没有找到年级";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_GRADE_NO_MATCH = "年级不对";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NO_MATCH = "学科不对";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_TOTAL_SCORE_NO_MATCH = "总分与小题分总和不一样。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_STUDENT_NOT_FOUND = "学生[{studentName}]和科目[{subjectName}]是没有包含在该考试内。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_ONLY_TEACHER = "只有老师可请求修改。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_DENIED = "登录的老师没有权限请求修改。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID = "总成绩不是数值。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID1 = "总成绩高比满分。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_CLASS_TYPE_INVALID = "班级类型不对。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NAME_EMPTY = "科目为空。";
    String ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NAME_INVALID = "科目不对。";

    String ERR_MESSAGE_CODE_STUDENT_PART_SCORE= "题分不对";
    String ERR_MESSAGE_CODE_STUDENT_PART_SCORE_SCORE_INVALID = "导入文件的位置{cellName}的内容不是数值。";
    String ERR_MESSAGE_CODE_STUDENT_PART_SCORE_TOTAL_SCORE_NO_MATCH = "总分不对. (班级ID: {classId}, 学号: {studentNo}, 姓名: {studentName}, 科目: {subjectName})";
    String ERR_MESSAGE_CODE_STUDENT_PART_SCORE_SCORE_INVALID1 = "导入文件的位置{cellName}的内容比小题满分值高。";
}
