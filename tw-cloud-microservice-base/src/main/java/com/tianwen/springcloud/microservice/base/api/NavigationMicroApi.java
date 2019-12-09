package com.tianwen.springcloud.microservice.base.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户相关对外接口
 * 
 * @author wangbin
 * @version [版本号, 2017年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@FeignClient(value = "base-service", url = "http://localhost:2226/base-service/navigation")
public interface NavigationMicroApi extends ICRUDMicroApi<Navigation>
{
    @RequestMapping(value = "/getSchoolSectionList", method = RequestMethod.POST)
    public Response<DictItem> getSchoolSectionList(@RequestBody  Navigation param);

    @RequestMapping(value = "/getSubjectList", method = RequestMethod.POST)
    public Response<DictItem> getSubjectList(@RequestBody Navigation param);

    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST)
    public Response<DictItem> getGradeList(@RequestBody Navigation param);

    @RequestMapping(value = "/getTermList", method = RequestMethod.POST)
    public Response<DictItem> getTermList(@RequestBody Navigation param);

    @RequestMapping(value = "/getExamTypeList", method = RequestMethod.POST)
    public Response<DictItem> getExamTypeList(@RequestBody Navigation param);

    @RequestMapping(value = "/getQuestionCategoryList", method = RequestMethod.POST)
    public Response<DictItem> getQuestionCategoryList(@RequestBody Navigation param);

    @RequestMapping(value = "/getQuestionTypeList", method = RequestMethod.POST)
    public Response<DictItem> getQuestionTypeList(@RequestBody Navigation param);

    @RequestMapping(value = "/getPaperVolumeList", method = RequestMethod.POST)
    public Response<DictItem> getPaperVolumeList(@RequestBody Navigation param);
}
