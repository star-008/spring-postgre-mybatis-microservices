package com.tianwen.springcloud.microservice.base.constant;

/**
 * 
 * 系统常量类
 * 
 * @author jwp
 * @version [版本号, 2011-10-10]
 */
public interface IBaseMicroConstants
{
    /*************** 对应t_e_user_logininfo.status begin *************************/
    /**
     * 用户帐号状态-1:正常
     */
    final String USER_ACCOUNT_STATUS_NORMAL = "1";
    
    /**
     * 用户帐号状态-2:待审核
     */
    final String USER_ACCOUNT_STATUS_TO_AUDIT = "2";
    
    /**
     * 用户帐号状态-4:审核不通过
     */
    final String USER_ACCOUNT_STATUS_NO_PASS = "4";
    
    /**
     * 用户帐号状态-9:删除
     */
    final String USER_ACCOUNT_STATUS_DELETE = "9";
    
    /*************** 对应t_e_user_logininfo.status end *************************/

    /*************** 对应t_e_user_logininfo.roleid. begin ***********************/

    /**
     * 角色ID-1000000000:删除
     */
    final String USER_ROLE_ID_VISITOR = "1000000000";

    /**
     * 角色ID-1000000001:
     */
    final String USER_ROLE_ID_MANAGER = "1000000001";


    /**
     * 角色ID-1000000002:删除
     */
    final String USER_ROLE_ID_TEACHER = "1000000002";

    /**
     * 角色ID-1000000003:删除
     */
    final String USER_ROLE_ID_STUDENT = "1000000003";

    /**
     * 角色ID-1000000004:删除
     */
    final String USER_ROLE_ID_PARENT = "1000000004";

    String USER_ROLE_ID_TIANWEN_MANAGER = "1000000005";

    String USER_ROLE_ID_REGION_MANAGER = "1000000006";

    String USER_ROLE_ID_SCHOOL_MANAGER = "1000000007";

    String USER_ROLE_ID_DEVELOPER = "1000000008";

    String USER_ROLE_ID_PRINCIPAL = "1000000009";

    String USER_ROLE_ID_EDU_MANAGER = "10000000010";

    String USER_ROLE_ID_SCORE_MANAGER = "10000000011";

    /*************** 对应t_e_user_logininfo.roleid end *************************/

    /******************* t_e_sys_dict_item.lang begin *************************/

    final String en_US = "en_US";

    final String zh_CN = "zh_CN";

    /********************* t_e_sys_dict_item.lang end *************************/


    /*************** 对应t_e_book.type start *************************/


    final String BOOK_TYPE_NORMAL = "1";


    final String BOOK_TYPE_ASSIST = "0";
    /*************** 对应t_e_book.type end *************************/

    /*************** 对应t_e_score_rule.bussinesstype start*************************/

    /**
     *上传资源
     */
    final String BUSSINESS_TYPE_UPLOAD = "0";

    /**
     *参与活动
     */
    final String BUSSINESS_TYPE_JOIN_ACTIVITY = "1";

    /**
     *评星
     */
    final String BUSSINESS_TYPE_RATING = "2";

    /**
     *点赞
     */
    final String BUSSINESS_TYPE_VOTE = "3";

    /**
     *挑错
     */
    final String BUSSINESS_TYPE_FAVOURIT = "4";

    /**
     *收藏
     */
    final String BUSSINESS_TYPE_REPORT = "5";

    /**
     *完善个人信息
     */
    final String BUSSINESS_TYPE_USER_INFOMATION = "6";

    /**
     *登录
     */
    final String BUSSINESS_TYPE_LOGIN = "7";

    /**
     * 题库积分
     */
    final String BUSSINESS_TYPE_ = "8";

    /*************** 对应t_e_score_rule.bussinesstype end *************************/

    /**
     * 服务名称
     */
    final String SERVICE_NAME = "base-service";
}