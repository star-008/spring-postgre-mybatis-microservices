package com.tianwen.springcloud.scoreapi.service.analysis;

import com.alibaba.fastjson.JSONObject;
import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.score.entity.request.ExamClassFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.service.ExamService;
import com.tianwen.springcloud.scoreapi.util.SecurityUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by kimchh on 12/11/2018.
 */
public class AnalysisService extends BaseService {

    private Logger cacheLogger = Logger.getLogger(this.getClass().getSuperclass().getName());
    private final Map<String, List<Thread>> queueMap = new HashMap<>();

    protected List<String> getJoinedClassIdList(Filter filter) {
        List<String> joinedClassIdList = null, classIdList;

        ExamService examService = BeanHolder.getBean(ExamService.class);
        for (String examId: filter.getExamIdList()) {

            ExamClassFilter examClassFilter = new ExamClassFilter(examId);
            examClassFilter.setGradeId(filter.getGradeId());
            examClassFilter.setClassType(filter.getClassType());

            classIdList = examService.getClassIdList(examClassFilter);

            if (joinedClassIdList == null) {
                joinedClassIdList = classIdList;
            } else {
                final List<String> list = joinedClassIdList;
                joinedClassIdList = classIdList.stream().filter(list::contains).collect(Collectors.toList());
            }
        }

        return joinedClassIdList;

    }

    public <T> Response<T> loadAnalysisListData(Class<T> clazz, ICRUDMicroApi<T> microApi, Request request) {

        if (!StringUtils.isEmpty(request.getFilter().getExamId())) {
            return loadAnalysisListDataByExam(clazz, microApi, request);

        } else if (!CollectionUtils.isEmpty(request.getFilter().getExamIdList())) {
            if (request.getFilter().getExamIdList().size() > 1
                    && StringUtils.isEmpty(request.getFilter().getClassId()) && CollectionUtils.isEmpty(request.getFilter().getClassIdList())) {

                //This is comparison module, we need to load class list manually common to all exams in request.getFilter().getExamIdList()
                List<String> classIdList = getJoinedClassIdList(request.getFilter());
                if (CollectionUtils.isEmpty(classIdList)) {
                    return new Response<>(new ArrayList<>());
                }
            }

            Response<T> response = new Response<>();

            List<String> examIdList = request.getFilter().getExamIdList();
            request.getFilter().setExamIdList(null);

            for (String examId: examIdList) {
                request.getFilter().setExamId(examId);

                Response<T> res = loadAnalysisListDataByExam(clazz, microApi, request);

                if (!IStateCode.HTTP_200.equals(res.getServerResult().getResultCode())) {
                    response.setServerResult(res.getServerResult());
                } else {
                    if (response.getPageInfo() == null || response.getPageInfo().getList() == null) {
                        response.setPageInfo(res.getPageInfo());
                    } else {
                        response.getPageInfo().getList().addAll(res.getPageInfo().getList());
                        response.getPageInfo().setSize(response.getPageInfo().getSize() + res.getPageInfo().getSize());
                    }
                }
            }

            return response;
        } else {
            return loadAnalysisListDataFromMicroService(clazz, microApi, request);
        }
    }

    public <T> Response<T> loadAnalysisListDataByExam(Class<T> clazz, ICRUDMicroApi<T> microApi, Request request) {
        if (!analysisDataCacheService.isCacheEnabled()) {
            return loadAnalysisListDataFromMicroService(clazz, microApi, request);
        }

        return loadAnalysisListDataFromCache(clazz, microApi, request);
    }

    public <T> Response<T> loadAnalysisListDataFromCache(Class<T> clazz, ICRUDMicroApi<T> microApi, Request request) {
        String examId = request.getFilter().getExamId();
        String moduleKey = getModuleKey();
        String dataKey = getDataKey(request);

        Response<T> response = new Response<>(analysisDataCacheService.loadListData(clazz, examId, moduleKey, dataKey));

        if (response.getPageInfo().getList() == null) {
            response = loadAnalysisListDataFromMicroService(clazz, microApi, request);

            if (response != null && response.getPageInfo().getList() != null) {
                analysisDataCacheService.saveListData(examId, moduleKey, dataKey, response.getPageInfo().getList());
            }
        }

        return response;
    }

    public <T> Response<T> loadAnalysisListDataFromMicroService(Class<T> clazz, ICRUDMicroApi<T> microApi, Request request) {
        try {
            Method method = microApi.getClass().getDeclaredMethod("getList", Request.class);
            return (Response<T>)method.invoke(microApi, request);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

        }

        return null;
    }

    public String getModuleKey() {
        return getClass().getSimpleName().replace("Service", "");
    }

    public String getDataKey(Object... args) {
        String key = null;

        if (args.length > 0) {
            if (args[0] instanceof Request) {
                Request request = (Request)args[0];
                key = SecurityUtil.generateHash(JSONObject.toJSON(request).toString());
            }
        }

        return key;
    }
}
