package com.tianwen.springcloud.scoreapi.session;

import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.api.StudentMicroApi;
import com.tianwen.springcloud.microservice.base.api.UserMicroApi;
import com.tianwen.springcloud.microservice.base.entity.UserLoginInfo;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.exception.InvalidTokenException;
import com.tianwen.springcloud.scoreapi.service.util.cache.TokenCacheService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
public class UserSessionBean implements DisposableBean, Serializable {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    protected UserMicroApi userMicroApi;

    @Autowired
    protected TokenCacheService tokenCache;

    @Override
    public void destroy() throws Exception {

    }

    public String getToken() {
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }
        
        return token;
    }

    public UserLoginInfo getAccount() {
        UserLoginInfo account = (UserLoginInfo)httpServletRequest.getAttribute("account");
        if (account == null) {
            account = loadAccount();
            validateExpiration(account);
            httpServletRequest.setAttribute("account", account);
        }

        return account;
    }

    public String getAccountId() {
        return getAccount().getUserId();
    }

    public String getAccountName() {
        return getAccount().getRealName();
    }

    protected void validateExpiration(UserLoginInfo user) {
        long expiration = System.currentTimeMillis() - user.getTokenrefreshedtime().getTime();
        if (expiration > user.getExpiresin()) {
            tokenCache.remove(user.getToken());
            throw new InvalidTokenException(new ServerResult(
                    IErrorMessageConstants.ERR_TOKEN_TIMEOUT,
                    IErrorMessageConstants.ERR_MESSAGE_TOKEN_TIMEOUT
            ));
        }

        if (expiration >= user.getExpiresin() / 2) {
            user.setTokenrefreshedtime(new Date(System.currentTimeMillis()));
            userMicroApi.update(user);
        }
    }

    protected UserLoginInfo loadAccount() {
        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException(new ServerResult(
                    IErrorMessageConstants.ERR_TOKEN_EMPTY,
                    IErrorMessageConstants.ERR_MESSAGE_TOKEN_EMPTY
            ));
        }

        UserLoginInfo user = userMicroApi.getByToken(token).getResponseEntity();
        if (user == null) {
            if (tokenCache.get(token) != null) {
                tokenCache.remove(token);
                throw new InvalidTokenException(new ServerResult(
                        IErrorMessageConstants.ERR_TOKEN_VALIDATION_MOVE,
                        IErrorMessageConstants.ERR_MESSAGE_TOKEN_VALIDATION_MOVE
                ));
            } else {
                throw new InvalidTokenException(new ServerResult(
                        IErrorMessageConstants.ERR_TOKEN_NOT_CORRECT,
                        IErrorMessageConstants.ERR_MESSAGE_TOKEN_NOT_CORRECT
                ));
            }
        }
        user = userMicroApi.getUserInfo(user.getUserId()).getResponseEntity();

        return user;
    }

    public List<String> getTeacherClassIdList() {
        QueryTree queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("adviserids", QueryCondition.Prepender.AND, QueryCondition.Operator.FUZZY_MATCH, getAccount().getUserId()));
        return BeanHolder.getBean(StudentMicroApi.class).getClassIdList(queryTree).getPageInfo().getList();
    }

    public boolean hasTeacherClass(String classId) {
        return getTeacherClassIdList().indexOf(classId) >= 0;
    }

    public boolean isSubjectTeacher(String teacherId) {
        return StringUtils.equals(getAccount().getUserId(), teacherId);
    }
}
