package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.DictItemMicroApi;
import com.tianwen.springcloud.microservice.base.constant.IBaseMicroConstants;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.service.DictItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/dictitem")
public class DictItemController extends AbstractCRUDController<DictItem> implements DictItemMicroApi
{
    @Autowired
    private DictItemService dictItemService;

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    public Response<DictItem> getByDictInfo(@RequestBody DictItem dictItem) {
        return dictItemService.getByDictInfo(dictItem);
    }

    @Override
    public Response<DictItem> getList(@RequestBody QueryTree queryTree) {
        return dictItemService.search(queryTree);
    }

    @Override
    public Response<Integer> getCount(@RequestBody QueryTree queryTree) {
        int count = dictItemService.getCount(queryTree);
        return new Response<>(count);
    }

    @Override
    public Response<DictItem> insert(@RequestBody DictItem entity) {
        String maxId = dictItemService.getMaxId();
        maxId = dictItemService.increment(maxId);

        DictItem zhCn = new DictItem();
        // parameter data
        zhCn.setDictname(entity.getDictname());
        zhCn.setDictvalue(entity.getDictvalue());
        zhCn.setRemark(entity.getRemark());
        zhCn.setParentdictid(entity.getParentdictid());
        zhCn.setSortno(entity.getSortno());
        zhCn.setIseditable(entity.getIseditable());
        //???
        zhCn.setDicttypeid(entity.getDicttypeid());
        // self-calc value
        zhCn.setStatus("1");
        zhCn.setLang(IBaseMicroConstants.zh_CN);
        zhCn.setLastmodifytime(new Timestamp(System.currentTimeMillis()));
        zhCn.setDictid(maxId);
        dictItemService.save(zhCn);

        DictItem enUs = new DictItem();
        // parameter data
        enUs.setDictname(entity.getDictname());
        enUs.setDictvalue(entity.getDictvalue());
        enUs.setRemark(entity.getRemark());
        enUs.setParentdictid(entity.getParentdictid());
        enUs.setSortno(entity.getSortno());
        enUs.setIseditable(entity.getIseditable());
        //???
        enUs.setDicttypeid(entity.getDicttypeid());
        // self-calc value
        enUs.setStatus("1");
        enUs.setLang(IBaseMicroConstants.en_US);
        enUs.setLastmodifytime(new Timestamp(System.currentTimeMillis()));
        enUs.setDictid(maxId);
        dictItemService.save(enUs);

        return new Response<>();
    }

    @Override
    public Response<DictItem> modify(@RequestBody DictItem entity) {
        entity.setLastmodifytime(new Timestamp(System.currentTimeMillis()));
        dictItemService.doUpdate(entity);
        return new Response<>(entity);
    }

    @Override
    public Response<DictItem> remove(@PathVariable(value = "dictid") String dictid) {
        dictItemService.doRemove(dictid);
        return new Response<>();
    }

    @Override
    @ApiOperation(value = "批量添加实体对象", notes = "批量添加实体对象")
    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量添加实体对象")
    public Response<DictItem> batchAddUpdate(@RequestBody List<DictItem> itemList) {

        for (DictItem item: itemList) {
            try {
                dictItemService.save(item);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "[" + item + "]");
            }
        }

//        List<DictItem> updateList = new ArrayList<>();
//        List<DictItem> addList = new ArrayList<>();
//
//        for (DictItem dictItem: itemList) {
//            List<DictItem> list= dictItemService.select(new DictItem(dictItem.getDictid(),dictItem.getLang()), 0, 0);
//            if (CollectionUtils.isEmpty(list)) {
//                addList.add(dictItem);
//            } else {
//                updateList.add(dictItem);
//            }
//        }
//
//        if (addList.size() > 0) {
//            validate(MethodType.BATCHADD, itemList);
//            dictItemService.batchInsert(addList);
//        }
//
//        if (updateList.size() > 0) {
//            validate(MethodType.BATCHUPDATE, itemList);
//            dictItemService.batchUpdateAll(updateList);
//        }

        return new Response<>(itemList);
    }
}
