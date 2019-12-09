package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.ClassMicroApi;
import com.tianwen.springcloud.microservice.base.constant.IBaseMicroConstants;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.service.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/class")
public class ClassController extends AbstractCRUDController<ClassInfo> implements ClassMicroApi
{
    @Autowired
    private ClassService classService;

    @Override
    public Response<ClassInfo> getClassList(@RequestBody QueryTree queryTree) {
        return classService.search(queryTree);
    }

    @Override
    public Response<ClassInfo> getClassById(@PathVariable(value = "id") String id) {
        Map<String, Object> param = new HashMap<>();
        param.put("classid", id);
        param.put("lang", IBaseMicroConstants.zh_CN);
        ClassInfo classInfo = classService.getClassById(param);
        return new Response<>(classInfo);
    }

    @Override
    public Response<ClassInfo> insertClass(@RequestBody ClassInfo entity) {
        classService.save(entity);
        return new Response<>(entity);
    }

    @Override
    public Response<ClassInfo> removeClass(@PathVariable(value = "classid") String classid) {
        ClassInfo entity = classService.selectByKey(classid);
        classService.deleteByPrimaryKey(entity);
        return new Response<>(entity);
    }

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @ApiOperation(value = "批量添加实体对象", notes = "批量添加实体对象")
    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量添加实体对象")
    public Response<ClassInfo> batchAddUpdate(@RequestBody List<ClassInfo> classInfoList)
    {
        List<ClassInfo> updateList = new ArrayList<>();
        List<ClassInfo> addList = new ArrayList<>();

        for (ClassInfo classInfo: classInfoList) {
            if (classService.selectByKey(classInfo.getClassid()) == null) {
                addList.add(classInfo);
            } else {
                updateList.add(classInfo);
            }
        }

        if (addList.size() > 0) {
            validate(MethodType.BATCHADD, classInfoList);
            classService.batchInsert(addList);
        }

        if (updateList.size() > 0) {
            validate(MethodType.BATCHUPDATE, classInfoList);
            classService.batchUpdateAll(updateList);
        }

        return new Response<>(classInfoList);
    }
}
