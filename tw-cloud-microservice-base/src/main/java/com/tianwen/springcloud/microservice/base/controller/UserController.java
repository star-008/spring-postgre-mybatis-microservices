package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.exception.ParameterException;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryCondition.Operator;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.DateUtil;
import com.tianwen.springcloud.commonapi.utils.ValidatorUtil;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.UserMicroApi;
import com.tianwen.springcloud.microservice.base.constant.IBaseMicroConstants;
import com.tianwen.springcloud.microservice.base.entity.Role;
import com.tianwen.springcloud.microservice.base.entity.UserLoginInfo;
import com.tianwen.springcloud.microservice.base.service.RoleService;
import com.tianwen.springcloud.microservice.base.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController extends AbstractCRUDController<UserLoginInfo> implements UserMicroApi
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    @ApiOperation(value = "根据ID删除用户信息", notes = "根据ID删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @SystemControllerLog(description = "根据用户ID删除用户信息")
    public Response<UserLoginInfo> delete(@PathVariable(value = "id") String id)
    {
        UserLoginInfo entity = new UserLoginInfo();
        entity.setUserId(id);
        entity.setStatus(IBaseMicroConstants.USER_ACCOUNT_STATUS_DELETE);
        int ret = userService.updateNotNull(entity);
        Response<UserLoginInfo> resp = new Response<>(entity);
        resp.getServerResult().setResultCode(Integer.toString(ret));
        return resp;
    }

    @Override
    @ApiOperation(value = "根据实体对象删除用户信息", notes = "根据实体对象删除用户信息")
    @ApiImplicitParam(name = "entity", value = "用户信息实体", required = true, dataType = "UserLoginInfo", paramType = "body")
    @SystemControllerLog(description = "根据实体对象删除用户信息")
    public Response<UserLoginInfo> deleteByEntity(@RequestBody UserLoginInfo entity)
    {
        UserLoginInfo entity1 = new UserLoginInfo();
        entity1.setUserId(entity.getUserId());
        entity1.setStatus(IBaseMicroConstants.USER_ACCOUNT_STATUS_DELETE);
        int ret = userService.updateNotNull(entity);
        Response<UserLoginInfo> resp = new Response<>(entity);
        resp.getServerResult().setResultCode(Integer.toString(ret));
        return resp;
    }
    
    @Override
    @ApiOperation(value = "用户搜索", notes = "用户搜索")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "用户搜索")
    public Response<UserLoginInfo> search(@RequestBody QueryTree queryTree)
    {
        return userService.search(queryTree);
    }
    
    @Override
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "获取用户列表")
    public Response<UserLoginInfo> getList(@RequestBody QueryTree queryTree)
    {
        return userService.search(queryTree);
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> get(@PathVariable(value = "userid") String userid)
    {
        UserLoginInfo userInfo = userService.getById(userid);
        Response<UserLoginInfo> resp = new Response<>(userInfo);

        return resp;
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> insert(@RequestBody UserLoginInfo entity)
    {
        entity.setStatus("1");
        int ret = userService.save(entity);

        Response<UserLoginInfo> resp = new Response<>(entity);
        resp.getServerResult().setResultCode(Integer.toString(ret));

        return resp;
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> registerUser(@RequestBody UserLoginInfo entity)
    {
        entity.setStatus("2");
        int ret = userService.save(entity);

        Response<UserLoginInfo> resp = new Response<>(entity);
        resp.getServerResult().setResultCode(Integer.toString(ret));

        return resp;
    }

    @Override
    public Response<UserLoginInfo> getByToken(@PathVariable(value = "token") String token) {
        UserLoginInfo userLoginInfo = userService.getUserByToken(token);

        return new Response<>(userLoginInfo);
    }

    @Override
    public Response<UserLoginInfo> getByLoginName(@PathVariable(value = "loginname") String loginname) {
        UserLoginInfo userLoginInfo = userService.getUserByLoginName(loginname);

        return new Response<>(userLoginInfo);
    }

    @Override
    public Response<UserLoginInfo> getByRealName(@PathVariable(value = "realname") String realname) {
        UserLoginInfo userLoginInfo = userService.getUserByRealName(realname);

        return new Response<>(userLoginInfo);
    }

    @Override
    public Response<UserLoginInfo> getByOrg(@PathVariable(value = "orgid") String orgid) {
        List<UserLoginInfo> userList = userService.getByOrg(orgid);

        return new Response<>(userList);
    }

    @Override
    public Response<String> getByArea(@PathVariable(value = "areaid") String areaid) {
        List<String> userList = userService.getByArea(areaid);
        if (userList != null) {
            userList.add("");
            userList.add("");
        }
        return new Response<>(userList);
    }

    @Override
    public Response<String> getUserIdsByQueryTree(@RequestBody QueryTree queryTree) {
        return userService.getUserIdsByQueryTree(queryTree);
    }

    @Override
    public Response<UserLoginInfo> getUserInfo(@PathVariable(value = "userid") String userid) {
        QueryTree queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("userid", QueryCondition.Prepender.AND, Operator.EQUAL, userid));
        List<UserLoginInfo> userData = userService.search(queryTree).getPageInfo().getList();
        if (CollectionUtils.isEmpty(userData))
            return new Response<>();

        UserLoginInfo user = userData.get(0);

        QueryTree roleTree = new QueryTree();
        roleTree.addCondition(new QueryCondition("userid", QueryCondition.Prepender.AND, Operator.EQUAL, userid));

        List<Role> roles = roleService.search(roleTree);
        user.setRoleIdList(new ArrayList<>());

        if (roles != null)
            for (Role role : roles)
                if (user.getRoleIdList().indexOf(role.getRoleid()) == -1)
                {
                    user.getRoleIdList().add(role.getRoleid());
                }

        return new Response<>(user);
    }

    @Override
    public Response<Role> getUserRole(@PathVariable(value = "userid") String userid) {
        QueryTree queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("userid", QueryCondition.Prepender.AND, Operator.EQUAL, userid));
        List<Role> roles = roleService.search(queryTree);
        return new Response<>(roles);
    }

    @Override
    public Response<UserLoginInfo> deleteUserRole(@PathVariable(value = "userid") String userid) {
        userService.deleteUserRole(userid);

        return new Response<UserLoginInfo>();
    }

    @Override
    public Response<UserLoginInfo> insertUserRole(@RequestBody Map<String, Object> param) {
        List<String> roleIdList = (List<String>)param.get("roleids");
        String userId = param.get("userid").toString();

        for (String roleId : roleIdList) {
            if (StringUtils.isEmpty(roleId))
                continue;
            Map<String, Object> userRole = new HashMap<>();
            userRole.put("roleid", roleId);
            userRole.put("userid", userId);
            userService.insertUserRole(userRole);
        }

        return new Response<>();
    }

    @Override
    public Response<UserLoginInfo> getUserByName(@RequestBody String username) {
        return userService.getByName(username);
    }

    @Override
    public Response<Integer> getLoginedUserCount(@RequestBody Map<String, Object> param) {
        String after;

        try {
            after = param.get("after").toString();
        }
        catch (Exception e) {
            after = DateUtil.format(new Date(System.currentTimeMillis()), DateUtil.FORMAT_YYYY_MM_DD);
        }
        after += " 00:00:00";
        param.put("after", Timestamp.valueOf(after));

        Integer count = userService.getLoginedUserCount(param);
        return new Response<>(count);
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> update(@RequestBody UserLoginInfo entity)
    {
        int ret;

        if (entity.getUserId() == null)
            ret = userService.save(entity);
        else
            ret = userService.updateNotNull(entity);

        Response<UserLoginInfo> resp = new Response<>(entity);
        resp.getServerResult().setResultCode(Integer.toString(ret));

        return resp;
    }

    @Override
    public Response<UserLoginInfo> changePassword(@RequestBody Map<String, Object> param) {
        Object userId = param.get("userid");
        UserLoginInfo user = new UserLoginInfo();

        if (userId != null)
        {
            user = userService.getById(userId.toString());
            if (user != null)
            {
                Object oldpass = param.get("oldpassword"), newpass = param.get("newpassword");
                if (oldpass != null && newpass != null)
                {
                    String oldkey = oldpass.toString(), newkey = newpass.toString();
                    if (user.getStaticPassword().equals(oldkey)) {
                        user.setStaticPassword(newkey);
                        userService.updateNotNull(user);
                    }
                }
            }
        }

        return new Response<>(user);
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> resetPassword(@RequestBody Map<String, Object> param)
    {
        List<String> userids = (List<String>)param.get("userids");

        for(String userid : userids)
        {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(userid);
            userLoginInfo.setStaticPassword("123456");
            userService.updateNotNull(userLoginInfo);
        }

        return new Response();
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> lockUser(@RequestBody Map<String, Object> param)
    {
        List<String> userids = (List<String>)param.get("userids");

        for(String userid : userids)
        {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(userid);
            if (param.get("islocked").toString().equals("1"))
            {
                userLoginInfo.setIsLocked(true);
                userLoginInfo.setLastLockedTime(new Date(System.currentTimeMillis()));
            }
            else
                userLoginInfo.setIsLocked(false);

            userService.updateNotNull(userLoginInfo);
        }

        return new Response();
    }

    @Override
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "queryTree", value = "搜索条件树", required = true, dataType = "QueryTree", paramType = "body")
    @SystemControllerLog(description = "")
    public Response<UserLoginInfo> allowUser(@RequestBody Map<String, Object> param)
    {
        List<String> userids = (List<String>)param.get("userids");

        for(String userid : userids)
        {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(userid);
            userLoginInfo.setStatus(param.get("status").toString());

            userService.updateNotNull(userLoginInfo);
        }

        return new Response();
    }

    @Override
    public void validate(MethodType methodType, Object p)
    {
        switch (methodType)
        {
            case ADD:
                UserLoginInfo entity = (UserLoginInfo)p;
                validateAdd(entity);
                break;
            case DELETE:
                break;
            case GET:
                break;
            case SEARCH:
                break;
            case UPDATE:
                break;
            case BATCHADD:
                break;
            case BATCHDELETEBYENTITY:
                break;
            case BATCHUPDATE:
                break;
            case DELETEBYENTITY:
                break;
            case GETBYENTITY:
                break;
            default:
                break;
        }
    }
    
    private void validateAdd(UserLoginInfo entity)
    {
        if (null == entity)
        {
            throw new ParameterException(IStateCode.PARAMETER_IS_EMPTY, "请求体为空");
        }
        ValidatorUtil.parameterValidate(entity);
        // 校验重复
        QueryTree queryTree = new QueryTree();
        queryTree.where("loginname", Operator.EQUAL, entity.getLoginName()).and("status",
            Operator.NOT_EQUAL,
            IBaseMicroConstants.USER_ACCOUNT_STATUS_DELETE);
        if (StringUtils.isNotBlank(entity.getOrgId()))
        {
            queryTree.and("orgid", Operator.EQUAL, entity.getOrgId());
        }
//        Example example = QueryUtils.queryTree2Example(queryTree, UserLoginInfo.class);
//        List<UserLoginInfo> existList = userService.selectByExample(example);
//        if (CollectionUtils.isNotEmpty(existList))
//        {
//            throw new ParameterException(IStateCode.PARAMETER_IS_INVALID, "登录名重复");
//        }

        String userid = userService.getLastId();
        if (userid != null && !userid.isEmpty())
//            userid = CommonUtil.increment(userid);
//        else
            userid = "user000000001";

        entity.setUserId(userid);
        entity.setCreateTime(new Date());
        entity.setCurrentLoginTime(null);
        entity.setLastLockedTime(null);
        entity.setLastLoginFailedTime(null);
        entity.setLastLoginTime(null);
        entity.setLastModifyTime(null);
    }
}
