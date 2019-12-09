package com.tianwen.springcloud.microservice.base.service;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.base.BaseService;
import com.tianwen.springcloud.datasource.util.QueryUtils;
import com.tianwen.springcloud.microservice.base.dao.RoleMapper;
import com.tianwen.springcloud.microservice.base.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role> {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> search(QueryTree queryTree) {
        Map<String, Object> map = QueryUtils.queryTree2Map(queryTree);
        return roleMapper.queryRoleForList(map);
    }
}
