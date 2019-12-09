package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.base.entity.*;
import com.tianwen.springcloud.scoreapi.api.GuestApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.entity.request.LoginInfo;
import com.tianwen.springcloud.scoreapi.entity.request.SSOLoginInfo;
import com.tianwen.springcloud.scoreapi.entity.response.CaptchaInfo;
import com.tianwen.springcloud.scoreapi.entity.response.ECOTokenInfo;
import com.tianwen.springcloud.scoreapi.entity.response.ECOUserInfo;
import com.tianwen.springcloud.scoreapi.exception.LoginFailureException;
import com.tianwen.springcloud.scoreapi.service.util.CaptchaService;
import com.tianwen.springcloud.scoreapi.util.SecurityUtil;
import com.tianwen.springcloud.scoreapi.util.captcha.service.Captcha;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by kimchh on 11/7/2018.
 */
@RestController
public class GuestController extends AbstractController implements GuestApi {

    @Autowired
    private CaptchaService captchaService;

    @Override
    public Response<CaptchaInfo> requestCaptcha() throws Exception {
        return this.requestCaptcha(0);
    }

    @Override
    @RequestMapping(value = "/captcha/{force}", method = RequestMethod.GET)
    public Response<CaptchaInfo> requestCaptcha(@PathVariable Integer force) throws Exception {
        if (force == 1) {
            captchaService.saveLastLoginFailureTime();
        }

        if (!captchaService.doesRequireCaptcha()) {
            return new Response<>();
        }

        Captcha captcha = captchaService.generateCaptcha();
        String code = captcha.getChallenge();
        String verifyKey = SecurityUtil.generateHash(code);

        return new Response<>(new CaptchaInfo(captchaService.saveCaptcha(code, verifyKey, captcha.getImage()), verifyKey));
    }

    @Override
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(name = "param", value = "用户登录信息", required = true, dataType = "Map<String, Object>", paramType = "body")
    @SystemControllerLog(description = "用户登录")
    public Response<UserLoginInfo> login(@RequestBody LoginInfo loginInfo) {

        if (!StringUtils.isEmpty(loginInfo.getCaptcha()) || captchaService.doesRequireCaptcha()) {
            captchaService.verifyCode(loginInfo.getCaptcha(), loginInfo.getCaptchaVerifyKey());
            captchaService.deleteCaptcha(loginInfo.getCaptchaVerifyKey());
        }

        captchaService.removeLastLoginFailureTime();

        if (!environment.isLive()) {
            return loginDevMode(loginInfo);
        }

        //Login to ECO System
        ECOTokenInfo ecoTokenInfo = ecoService.login(loginInfo.getUsername(), loginInfo.getPassword(), loginInfo.getAppId(), loginInfo.getAppKey());
        if (ecoTokenInfo.getErrorCode() != null) {
            throw new LoginFailureException(
                    new ServerResult(
                            "-1",
                            ecoTokenInfo.getErrorMessage()
                    )
            );
        }

        return syncLoginInfo(ecoTokenInfo);
    }

    public Response<UserLoginInfo> loginSSO(@RequestBody SSOLoginInfo loginInfo) {

        ECOTokenInfo ecoTokenInfo;

        if (!StringUtils.isEmpty(loginInfo.getAccessToken())) {

            ecoTokenInfo = new ECOTokenInfo();
            ecoTokenInfo.setAccessToken(loginInfo.getAccessToken());
            ecoTokenInfo.setExpiresIn((long)43200000);

        } else if (!StringUtils.isEmpty(loginInfo.getAccreditCode())) {

            //Login to ECO System
            ecoTokenInfo = ecoService.loginSSO(loginInfo.getAccreditCode(), loginInfo.getAppId(), loginInfo.getAppKey());
            if (ecoTokenInfo.getErrorCode() != null) {

                throw new LoginFailureException(
                        new ServerResult(
                                "-1",
                                ecoTokenInfo.getErrorMessage()
                        )
                );
            }
        } else {
            throw new LoginFailureException(
                    new ServerResult(
                            "-1",
                            ""
                    )
            );
        }

        return syncLoginInfo(ecoTokenInfo);
    }

    private Response<UserLoginInfo> syncLoginInfo(ECOTokenInfo ecoTokenInfo) {

        //Get user info from ECO System
        Response<ECOUserInfo> ecoResponse = ecoService.getUserInfo(ecoTokenInfo.getAccessToken());
        if (!ecoResponse.getServerResult().getResultCode().equals(IStateCode.HTTP_200)) {
            return new Response<>(ecoResponse.getServerResult().getResultCode(), ecoResponse.getServerResult().getResultMessage());

        }
        ECOUserInfo ecoUserInfo = ecoResponse.getResponseEntity();

        // Check user role
//        if (loginInfo.getRole() != null && !loginInfo.getRole().isEmpty() && !ecoUserInfo.hasRole(loginInfo.getRole())) {
//            return new Response<>("-1", IErrorMessageConstants.ERR_MESSAGE_USER_ROLE_INCORRECT);
//        }

        // Load user info from local(Score) system
        UserLoginInfo userInfo = userMicroApi.getByLoginName(ecoUserInfo.getLoginName()).getResponseEntity();
        userInfo = userInfo == null ? null : userMicroApi.getUserInfo(userInfo.getUserId()).getResponseEntity();

        boolean bNewUser = false;
        if (userInfo == null) {
            userInfo = new UserLoginInfo();
            userInfo.setUserId(ecoUserInfo.getUserId());
            bNewUser = true;
        }

        userInfo.setRealName(ecoUserInfo.getRealName());
        userInfo.setLoginName(ecoUserInfo.getLoginName());
        userInfo.setLoginEmail(ecoUserInfo.getLoginEmail());
        userInfo.setLoginMobile(ecoUserInfo.getLoginMobile());
        userInfo.setStaticPassword(ecoUserInfo.getStaticPassword());
        userInfo.setOrgId(ecoUserInfo.getOrgId());
        userInfo.setBirthday(ecoUserInfo.getBirthday());
        userInfo.setImagePath(ecoUserInfo.getImagePath());
        userInfo.setFileId(ecoUserInfo.getFileId());
        userInfo.setIdCardNo(ecoUserInfo.getIdCardNo());
        userInfo.setSex(ecoUserInfo.getSex());
        userInfo.setRoleIdList(ecoUserInfo.getRoleIdList());

        userInfo.setLastLoginTime(userInfo.getCurrentLoginTime());
        userInfo.setCurrentLoginTime(new Timestamp(System.currentTimeMillis()));
        userInfo.setToken(ecoTokenInfo.getAccessToken());

        userInfo.setExpiresin(ecoTokenInfo.getExpiresIn());
        userInfo.setRefreshtoken(ecoTokenInfo.getRefreshToken());
        userInfo.setTokenrefreshedtime(new Date(System.currentTimeMillis()));

        if (bNewUser) {
            userInfo = userMicroApi.insert(userInfo).getResponseEntity();
        } else {
            userInfo = userMicroApi.update(userInfo).getResponseEntity();
        }

        userMicroApi.deleteUserRole(userInfo.getUserId());
        Map<String, Object> roleData = new HashMap<>();
        roleData.put("userid", userInfo.getUserId());
        roleData.put("roleids", userInfo.getRoleIdList());
        userMicroApi.insertUserRole(roleData);

//        long timeout = userInfo.getExpiresin() / 1000;
//        httpSession.setMaxInactiveInterval((int) timeout);

        tokenCache.put(userInfo.getToken(), userInfo.getUserId());

        return new Response<>(userInfo);
    }

    protected Response<UserLoginInfo> loginDevMode(LoginInfo loginInfo) {

        UserLoginInfo userInfo = userMicroApi.getByLoginName(loginInfo.getUsername()).getResponseEntity();
        if (userInfo == null) {
            throw new LoginFailureException(
                    new ServerResult(
                            "-1",
                            "登录失败."
                    )
            );
        }

        userInfo = userMicroApi.getUserInfo(userInfo.getUserId()).getResponseEntity(); //I think we don't need these double, we can handle this in micro service, but this login is dev mode only.
        if (userInfo == null) {
            throw new LoginFailureException(
                    new ServerResult(
                            "-1",
                            "登录失败."
                    )
            );
        }

        if (!loginInfo.getPassword().equals(userInfo.getStaticPassword())) {

            userInfo.setLastLoginFailedTime(new Timestamp(System.currentTimeMillis()));

            Integer failCount = userInfo.getLoginFailedCount();
            userInfo.setLoginFailedCount(failCount == null ? 1 : failCount.intValue() + 1);

            userInfo.setBirthday(null);

            throw new LoginFailureException(
                    new ServerResult(
                            "-1",
                            "登录失败."
                    )
            );
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        String access_token = SecurityUtil.generateHash(current.toString());

        userInfo.setLastLoginTime(userInfo.getCurrentLoginTime());
        userInfo.setCurrentLoginTime(current);
        userInfo.setToken(access_token);
        userInfo.setTokenrefreshedtime(new Date(System.currentTimeMillis()));
        userInfo.setExpiresin((long)43200000);

        userInfo = userMicroApi.update(userInfo).getResponseEntity();

        tokenCache.put(access_token, userInfo.getUserId());

        return new Response<>(userInfo);
    }

    @Override
    public Response logout(@RequestHeader(value = "token") String token) {
        try {
            UserLoginInfo account = sessionBean.getAccount();

            if (account != null) {
                account.setExpiresin((long)43200000);
                account.setToken("");
                userMicroApi.update(account);
                tokenCache.remove(token);
            }
        } catch (Exception ex) {
            System.out.println("No user to logout.");
        }

        return new Response();
    }
}
