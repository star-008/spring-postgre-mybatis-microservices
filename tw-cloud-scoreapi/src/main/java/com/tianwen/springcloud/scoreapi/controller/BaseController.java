package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.api.*;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.scoreapi.api.BaseApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimchh on 11/9/2018.
 */
@RestController
@RequestMapping(value = "/base")
public class BaseController extends AbstractController implements BaseApi {

    @Autowired
    private NavigationMicroApi navigationMicroApi;

    @Autowired
    private StudentMicroApi studentMicroApi;

    @Autowired
    private ClassMicroApi classMicroApi;

    @Override
    public Response<DictItem> getSchoolSectionList(@RequestBody Navigation param) {
        return navigationMicroApi.getSchoolSectionList(param);
    }

    @Override
    public Response<DictItem> getSubjectList(@RequestBody Navigation param) {
        return navigationMicroApi.getSubjectList(param);
    }

    @Override
    public Response<DictItem> getGradeList(@RequestBody Navigation param) {
        return navigationMicroApi.getGradeList(param);
    }

    @Override
    public Response<DictItem> getTermList(@RequestBody Navigation param) {
        return navigationMicroApi.getTermList(param);
    }

    @Override
    public Response<DictItem> getExamTypeList(@RequestBody Navigation param) {
        return navigationMicroApi.getExamTypeList(param);
    }

    @Override
    public Response<ClassInfo> getClassList(@RequestBody QueryTree queryTree) {
        return classMicroApi.search(queryTree);
    }

    @Override
    public Response<Student> getStudentList(@RequestBody QueryTree queryTree) {
        return studentMicroApi.search(queryTree);
    }

    @Override
    public Response<Exam.BaseData> sync(@RequestBody Exam.BaseData base) {
        if (!CollectionUtils.isEmpty(base.getTermList())) {
            BeanHolder.getBean(TermMicroApi.class).batchAddUpdate(base.getTermList());
        }

        List<DictItem> baseDataList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(base.getTypeList())) {
            baseDataList.addAll(base.getTypeList());
        }

        if (!CollectionUtils.isEmpty(base.getSchoolSectionList())) {
            baseDataList.addAll(base.getSchoolSectionList());
        }

        if (!CollectionUtils.isEmpty(base.getGradeList())) {
            baseDataList.addAll(base.getGradeList());
        }

        if (!CollectionUtils.isEmpty(base.getSubjectList())) {
            baseDataList.addAll(base.getSubjectList());
        }

        BeanHolder.getBean(DictItemMicroApi.class).batchAddUpdate(baseDataList);

        return new Response<>(base);
    }
}
