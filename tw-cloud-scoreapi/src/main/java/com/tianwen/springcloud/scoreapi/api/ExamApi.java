package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.request.*;
import com.tianwen.springcloud.scoreapi.entity.request.ExamSyncInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kimchh on 11/5/2018.
 */
public interface ExamApi {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Response<Exam> add(@RequestBody Exam exam);

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    Response<Exam> update(@RequestBody Exam exam);

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    Response<Exam> sync(@RequestBody ExamSyncInfo examSyncInfo);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response<Exam> search(@RequestBody QueryTree querytree);

    @RequestMapping(value = "/recent}", method = RequestMethod.POST)
    Response<Exam> recent(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/recent/{limit}", method = RequestMethod.POST)
    Response<Exam> recent(@PathVariable Integer limit, @RequestBody QueryTree queryTree);

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    Response<Exam> delete(@PathVariable("id") String id);

    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    Response<Exam> close(@PathVariable("id") String id);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Response<Exam>   get(@PathVariable("id") String id);

    @RequestMapping(value = "/getSimpleList", method = RequestMethod.POST)
    Response<Exam> getSimpleList(@RequestBody Filter filter);

    @RequestMapping(value = "/getTermList", method = RequestMethod.POST)
    Response<Term> getTermList(@RequestBody Filter filter);

    @RequestMapping(value = "/getTypeList", method = RequestMethod.POST)
    Response<DictItem> getTypeList(@RequestBody ExamTypeFilter filter);

    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST)
    Response<DictItem> getGradeList(@RequestBody ExamGradeFilter filter);

    @RequestMapping(value = "/getSubjectList", method = RequestMethod.POST)
    Response<DictItem> getSubjectList(@RequestBody ExamSubjectFilter filter);

    @RequestMapping(value = "/getClassList", method = RequestMethod.POST)
    Response<ClassInfo> getClassList(@RequestBody ExamClassFilter filter);
}
