package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.StudentMicroApi;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.base.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController extends AbstractCRUDController<Student> implements StudentMicroApi
{
    @Autowired
    private StudentService studentService;

    @Override
    public Response<Student> getStudentList(@RequestBody QueryTree queryTree) {

        return  studentService.search(queryTree);
    }

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @ApiOperation(value = "批量添加实体对象", notes = "批量添加实体对象")
    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量添加实体对象")
    public Response<Student> batchAddUpdate(@RequestBody List<Student> studentList)
    {
        List<Student> updateList = new ArrayList<>();
        List<Student> addList = new ArrayList<>();

        for (Student student: studentList) {
            if (studentService.selectByKey(student.getUserId()) == null) {
                addList.add(student);
            } else {
                updateList.add(student);
            }
        }
        
        if (addList.size() > 0) {
            validate(MethodType.BATCHADD, studentList);
            studentService.batchInsert(addList);
        }

        if (updateList.size() > 0) {
            validate(MethodType.BATCHUPDATE, studentList);
            studentService.batchUpdateAll(updateList);
        }

        return new Response<>(studentList);
    }

    @Override
    public Response<String> getClassIdList(@RequestBody QueryTree queryTree) {
        return new Response<>(studentService.selectClassIdList(queryTree));
    }
}
