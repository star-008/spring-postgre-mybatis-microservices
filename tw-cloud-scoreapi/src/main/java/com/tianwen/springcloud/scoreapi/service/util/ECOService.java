package com.tianwen.springcloud.scoreapi.service.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.scoreapi.entity.response.ECOTeach;
import com.tianwen.springcloud.scoreapi.entity.response.ECOTokenInfo;
import com.tianwen.springcloud.scoreapi.entity.response.ECOUserInfo;
import com.tianwen.springcloud.scoreapi.session.UserSessionBean;
import com.tianwen.springcloud.scoreapi.util.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kimchh on 11/7/2018.
 */

@Service
@Scope("application")
public class ECOService {

    @Value("${eco.server-url}")
    protected String ecoServerUrl;

    @Value("${eco.tenant-id}")
    protected String ecoTenantId;

    @Autowired
    protected UserSessionBean sessionBean;

    static class ECOApiEndpoint {
        static final String AUTH = "/openapi-uc/oauth/token";
        static final String GET_USER_BY_TOKEN = "/openapi-uc/uc/getUserByToken";

        static final String GET_TERM_LIST = "/openapi-base/base/queryTermList";
        static final String GET_STUDENT_LIST = "/openapi-base/base/queryStudents";
        static final String GET_TEACH_LIST = "/openapi-base/base/queryTeachs";
    }

    //private RestTemplate ecoClient = new RestTemplate();

    public ECOTokenInfo login(String username, String password, String ecoAppId, String ecoAppKey) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", ecoAppId);
        map.put("client_secret", ecoAppKey);
        map.put("grant_type", "password");
        map.put("username", username);
        map.put("password", password);

        ECOTokenInfo tokenInfo = new ECOTokenInfo();
        String result = HttpClientUtil.doPost(ecoServerUrl + ECOApiEndpoint.AUTH, null, map, "utf-8");

        //return JSONObject.parseObject(result).toJavaObject(ECOTokenInfo.class);

        JSONObject jsonObject = JSONObject.parseObject(result);
        tokenInfo.setErrorCode(jsonObject.getString("errorCode"));
        tokenInfo.setErrorMessage(jsonObject.getString("errorMesage"));
        if (tokenInfo.getErrorCode() == null) {
            tokenInfo.setAccessToken(jsonObject.getString("access_token"));
            tokenInfo.setExpiresIn(jsonObject.getLong("expires_in"));
            tokenInfo.setRefreshToken(jsonObject.getString("refresh_token"));
        }

        return tokenInfo;

        //return ecoClient.postForObject(ecoServerUrl + ECOApiEndpoint.AUTH, map, ECOTokenInfo.class);

        /*
        return ecoClient.exchange(
                ecoServerUrl + ECOApiEndpoint.AUTH, //URL
                HttpMethod.POST,    //Method
                new HttpEntity<>(map, getHttpHeaders()), //Request param and headers
                ECOTokenInfo.class
        ).getBody();
        */


    }

    public ECOTokenInfo loginSSO(String accreditCode, String ecoAppId, String ecoAppKey) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", ecoAppId);
        map.put("client_secret", ecoAppKey);
        map.put("grant_type", "client_credentials");
        map.put("accredit_code", accreditCode);

        ECOTokenInfo tokenInfo = new ECOTokenInfo();
        String result = HttpClientUtil.doPost(ecoServerUrl + ECOApiEndpoint.AUTH, null, map, "utf-8");

        JSONObject jsonObject = JSONObject.parseObject(result);
        tokenInfo.setErrorCode(jsonObject.getString("errorCode"));
        tokenInfo.setErrorMessage(jsonObject.getString("errorMessage"));
        if (tokenInfo.getErrorCode() == null) {
            tokenInfo.setAccessToken(jsonObject.getString("access_token"));
            tokenInfo.setExpiresIn(jsonObject.getLong("expires_in"));
            tokenInfo.setRefreshToken(jsonObject.getString("refresh_token"));
        }

        return tokenInfo;
    }

    public Response<ECOUserInfo> getUserInfo(String token) {
        Response<ECOUserInfo> response = new Response<>();

        String result = HttpClientUtil.doGet(ecoServerUrl + ECOApiEndpoint.GET_USER_BY_TOKEN + "/" + token, "");
        JSONObject jsonObject = JSONObject.parseObject(result);

        JSONObject jsonServerResult = jsonObject.getJSONObject("serverResult");
        if (jsonServerResult != null) {
            response.setServerResult(jsonServerResult.toJavaObject(ServerResult.class));
        }

        JSONObject jsonUser = jsonObject.getJSONObject("responseEntity");
        if (jsonUser != null) {
            response.setResponseEntity(jsonUser.toJavaObject(ECOUserInfo.class));
        }

        return response;

//        return ecoClient.exchange(
//                ecoServerUrl + ECOApiEndpoint.GET_USER_BY_TOKEN + "/" + token, //URL
//                HttpMethod.GET,    //Method
//                null, //Request param and headers
//                new ParameterizedTypeReference<Response<ECOUserInfo>>() {} //Response type
//        ).getBody();
    }

    public Response<Term> getTermList(String orgId) {
        Response<Term> response = new Response<>();

        JSONObject params = new JSONObject();
        params.put("orgId", orgId);
        params.put("pageNo", "1");
        params.put("numPerPage", "1000");

        String result = HttpClientUtil.doPost2(ecoServerUrl + ECOApiEndpoint.GET_TERM_LIST, getHttpHeaders(), params, "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        JSONObject jsonServerResult = jsonObject.getJSONObject("serverResult");
        if (jsonServerResult != null) {
            response.setServerResult(jsonServerResult.toJavaObject(ServerResult.class));
        }

        JSONObject jsonPageInfo = jsonObject.getJSONObject("pageInfo");
        if (jsonPageInfo != null) {
            JSONArray jsonList = jsonPageInfo.getJSONArray("list");
            if (jsonList != null) {
                response.setPageInfo(new Response<>(jsonList.toJavaList(Term.class)).getPageInfo());
            }
        }

        return response;
    }

    public Response<Student> getStudentList(String classId, String classType) {
        Response<Student> response = new Response<>();

        JSONObject params = new JSONObject();
        params.put("classId", classId);
        params.put("classType", classType);
        params.put("pageNo", "1");
        params.put("numPerPage", "1000");
        String result = HttpClientUtil.doPost2(ecoServerUrl + ECOApiEndpoint.GET_STUDENT_LIST, getHttpHeaders(), params, "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        JSONObject jsonServerResult = jsonObject.getJSONObject("serverResult");
        if (jsonServerResult != null) {
            response.setServerResult(jsonServerResult.toJavaObject(ServerResult.class));
        }

        JSONObject jsonPageInfo = jsonObject.getJSONObject("pageInfo");
        if (jsonPageInfo != null) {
            JSONArray jsonList = jsonPageInfo.getJSONArray("list");
            if (jsonList != null) {
                response.setPageInfo(new Response<>(jsonList.toJavaList(Student.class).stream().map(Student::consumeExtInfo).collect(Collectors.toList())).getPageInfo());
            }
        }

        return response;

//        return ecoClient.exchange(
//                ecoServerUrl + ECOApiEndpoint.GET_STUDENT_LIST, //URL
//                HttpMethod.POST,    //Method
//                new HttpEntity<>(new Student(classId), getHttpHeaders()), //Request param and headers
//                new ParameterizedTypeReference<Response<Student>>() {} //Response type
//        ).getBody();
    }

    public Response<ECOTeach> getTeachList(String orgId, String gradeId, String classType) {
        Response<ECOTeach> response = new Response<>();

        JSONObject params = new JSONObject();
        params.put("classType", classType);
        params.put("classStatus", "1");
        params.put("grade", gradeId);
        params.put("orgId", orgId);
        params.put("pageNo", "1");
        params.put("numPerPage", "1000");

        String result = HttpClientUtil.doPost2(ecoServerUrl + ECOApiEndpoint.GET_TEACH_LIST, getHttpHeaders(), params, "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        JSONObject jsonServerResult = jsonObject.getJSONObject("serverResult");
        if (jsonServerResult != null) {
            response.setServerResult(jsonServerResult.toJavaObject(ServerResult.class));
        }

        JSONObject jsonPageInfo = jsonObject.getJSONObject("pageInfo");
        if (jsonPageInfo != null) {
            JSONArray jsonList = jsonPageInfo.getJSONArray("list");
            if (jsonList != null) {
                response.setPageInfo(new Response<>(jsonList.toJavaList(ECOTeach.class)).getPageInfo());
            }
        }

        return response;
    }

    protected List<Header> getHttpHeaders() {
        List<Header> headers = new ArrayList<>();

        headers.add(new BasicHeader("access-token", getAccessToken()));
        headers.add(new BasicHeader("tenant-Id", ecoTenantId));

        return headers;
    }

    protected String getAccessToken() {
        return sessionBean.getToken();
    }
}
