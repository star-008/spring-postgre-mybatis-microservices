package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kimchh on 11/5/2018.
 */
public interface BaseApi {

    @RequestMapping(value = "/getSchoolSectionList", method = RequestMethod.POST)
    Response<DictItem> getSchoolSectionList(@RequestBody Navigation param);

    @RequestMapping(value = "/getSubjectList", method = RequestMethod.POST)
    Response<DictItem> getSubjectList(@RequestBody Navigation param);

    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST)
    Response<DictItem> getGradeList(@RequestBody Navigation param);

    @RequestMapping(value = "/getTermList", method = RequestMethod.POST)
    Response<DictItem> getTermList(@RequestBody Navigation param);

    @RequestMapping(value = "/getExamTypeList", method = RequestMethod.POST)
    Response<DictItem> getExamTypeList(@RequestBody Navigation param);

    @RequestMapping(value = "/getClassList", method = RequestMethod.POST)
    Response<ClassInfo> getClassList(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/getStudentList", method = RequestMethod.POST)
    Response<Student> getStudentList(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    Response<Exam.BaseData> sync(@RequestBody Exam.BaseData base);
}
