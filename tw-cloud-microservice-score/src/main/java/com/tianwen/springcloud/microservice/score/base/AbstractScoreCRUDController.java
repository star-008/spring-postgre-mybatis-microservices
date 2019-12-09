package com.tianwen.springcloud.microservice.score.base;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.service.MessageSourceService;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kimchh on 11/5/2018.
 */
public abstract class AbstractScoreCRUDController<T> extends AbstractCRUDController<T> {

    @Autowired
    private ScoreBaseService<T> scoreBaseService;

    private MessageSourceService messageService = null;

    private boolean usingGetListForSearch = false;

    @Override
    public Response<T> search(@RequestBody QueryTree queryTree)
    {
        if (isUsingGetListForSearch()) {
            return getList(queryTree);
        } else {
            scoreBaseService.convertQueryTreeFromJsonProperty(queryTree);
            return super.search(queryTree);
        }
    }

    @Override
    public Response<T> searchByEntity(@RequestBody T entity)
    {
        validate(MethodType.SEARCHBYENTITY, entity);
        return new Response<>(scoreBaseService.select(entity, 0, 0));
    }

    @Override
    public Response<T> deleteByEntity(@RequestBody T entity)
    {
        validate(MethodType.DELETEBYENTITY, entity);
        scoreBaseService.delete(entity);
        return new Response<>();
    }

    protected Response<T> getList(@RequestBody QueryTree queryTree) {
        validate(MethodType.SEARCH, queryTree);
        return new Response<>(scoreBaseService.getList(queryTree));
    }

    protected MessageSourceService getMessageService() {
        if (null == messageService) {
            messageService = BeanHolder.getBean(MessageSourceService.class);
        }

        return messageService;
    }

    public boolean isUsingGetListForSearch() {
        return usingGetListForSearch;
    }

    public void setUsingGetListForSearch(boolean usingGetListForSearch) {
        this.usingGetListForSearch = usingGetListForSearch;
    }

    public void useGetListForSearch() {
        useGetListForSearch(true);
    }
    public void useGetListForSearch(boolean usingGetListForSearch) {
        setUsingGetListForSearch(usingGetListForSearch);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "IStatsMicroApi根据条件 查询，带分页")
    public Response<T> getList(@RequestBody Request request) {
        return new Response<>(scoreBaseService.getList(request));
    }

    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "IStatsMicroApi GetCount")
    public Response<Integer> getCount(@RequestBody Request request) {
        return new Response<>(scoreBaseService.getCount(request));
    }
}
