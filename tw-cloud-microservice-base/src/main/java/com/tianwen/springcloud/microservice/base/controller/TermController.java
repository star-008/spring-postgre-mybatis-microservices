package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.TermMicroApi;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.microservice.base.service.TermService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/term")
public class TermController extends AbstractCRUDController<Term> implements TermMicroApi
{
    @Autowired
    private TermService termService;

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @ApiOperation(value = "批量添加实体对象", notes = "批量添加实体对象")
    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量添加实体对象")
    public Response<Term> batchAddUpdate(@RequestBody List<Term> termList)
    {
        List<Term> updateList = new ArrayList<>();
        List<Term> addList = new ArrayList<>();

        for (Term term: termList) {
            if (termService.selectByKey(term.getTermId()) == null) {
                addList.add(term);
            } else {
                updateList.add(term);
            }
        }
        
        if (addList.size() > 0) {
            validate(MethodType.BATCHADD, termList);
            termService.batchInsert(addList);
        }

        if (updateList.size() > 0) {
            validate(MethodType.BATCHUPDATE, termList);
            termService.batchUpdateAll(updateList);
        }

        return new Response<>(termList);
    }
}
