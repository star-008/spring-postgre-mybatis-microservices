package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.SysExamSubject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface SysExamSubjectApi {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Response add(@RequestBody SysExamSubject sysExamSubject);

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    Response batchAdd(@RequestBody List<SysExamSubject> sysExamSubjectList);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    Response delete(@RequestBody SysExamSubject sysExamSubject);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response search(@RequestBody QueryTree querytree);
}
