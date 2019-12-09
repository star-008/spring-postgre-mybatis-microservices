package com.tianwen.springcloud.scoreapi.service.util.repo;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.commonapi.utils.EntityUtils;
import com.tianwen.springcloud.microservice.base.api.ClassMicroApi;
import com.tianwen.springcloud.microservice.base.api.NavigationMicroApi;
import com.tianwen.springcloud.microservice.base.api.TermMicroApi;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.exception.InvalidTokenException;
import com.tianwen.springcloud.scoreapi.service.util.ECOService;
import com.tianwen.springcloud.scoreapi.session.UserSessionBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kimchh on 11/9/2018.
 */
public abstract class RepositoryService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserSessionBean userSessionBean;

    @Autowired
    private ECOService ecoService;

    public enum EntityType {
        PAPER_VOLUME, QUESTION_CATEGORY, QUESTION_TYPE, GRADE, SUBJECT, CLASS, SCHOOL_SECTION, EXAM_TYPE, EXAM_TERM, EXAM
    }

    protected abstract String getKeyFieldName(EntityType entityType);

    public Map<EntityType, Map<String, Object>> getRepository() {
        String repositoryName = "repository[" + getRepositoryName() + "]";
        Map<EntityType, Map<String, Object>> repository = (Map<EntityType, Map<String, Object>>)httpServletRequest.getAttribute(repositoryName);
        if (repository == null) {
            repository = new HashMap<>();
            httpServletRequest.setAttribute(repositoryName, repository);
        }

        return repository;
    }

    protected String getRepositoryName() {
        return getClass().getName();
    }

    public void clearEntityMap() {
        getRepository().clear();
    }

    public void clearEntityMap(EntityType entityType) {
        if (getRepository().containsKey(entityType)) {
            getRepository().remove(entityType);
        }
    }

    protected Map<String, Object> getEntityMap(EntityType entityType) {
        Map<String, Object> map;
        if (!getRepository().containsKey(entityType)) {
            map = loadEntityMap(entityType);
            getRepository().put(entityType, map);
        } else {
            map = getRepository().get(entityType);
        }

        return map;
    }
    protected Map<String, Object> loadEntityMap(EntityType entityType) {
        return list2Map(getList(entityType), entityType);
    }

    protected List getList(EntityType entityType) {
        try {
            switch (entityType) {
                case PAPER_VOLUME:
                    //return BeanHolder.getBean(NavigationMicroApi.class).getPaperVolumeList(new Navigation()).getPageInfo().getList();
                    return getPaperVolumeList();
                case QUESTION_CATEGORY:
                    return BeanHolder.getBean(NavigationMicroApi.class).getQuestionCategoryList(new Navigation()).getPageInfo().getList();
                case QUESTION_TYPE:
                    return BeanHolder.getBean(NavigationMicroApi.class).getQuestionTypeList(new Navigation()).getPageInfo().getList();
                case GRADE:
                    return BeanHolder.getBean(NavigationMicroApi.class).getGradeList(new Navigation()).getPageInfo().getList();
                case SUBJECT:
                    return BeanHolder.getBean(NavigationMicroApi.class).getSubjectList(new Navigation()).getPageInfo().getList();
                case CLASS:
                    return BeanHolder.getBean(ClassMicroApi.class).getClassList(getDefaultQueryTree()).getPageInfo().getList();
                case SCHOOL_SECTION:
                    return BeanHolder.getBean(NavigationMicroApi.class).getSchoolSectionList(new Navigation()).getPageInfo().getList();
                case EXAM_TERM:
                    //return BeanHolder.getBean(TermMicroApi.class).search(getDefaultQueryTree()).getPageInfo().getList();
                    return ecoService.getTermList(userSessionBean.getAccount().getOrgId()).getPageInfo().getList();
                case EXAM_TYPE:
                    return BeanHolder.getBean(NavigationMicroApi.class).getExamTypeList(new Navigation()).getPageInfo().getList();
                case EXAM:
                    return BeanHolder.getBean(ExamMicroApi.class).search(getDefaultQueryTree()).getPageInfo().getList();
            }
        } catch (InvalidTokenException itex) {
            throw itex;
        } catch (Exception ex) {
            System.err.println("Cannot load repository for " + entityType.name() + "(" + ex.getMessage() + ")");
        }
        return null;
    }

    protected List<DictItem> getPaperVolumeList() {
        List<DictItem> paperVolumeList = new ArrayList<>();

        String[] volumeTypes = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < volumeTypes.length; i++) {
            paperVolumeList.add(new DictItem(null, null, volumeTypes[i] + ICommonConstants.VOLUME, String.valueOf(i + 1)));
        }

        return paperVolumeList;
    }

    protected QueryTree getDefaultQueryTree() {

        QueryTree queryTree = new QueryTree();
        //queryTree.getPagination().setPageNo(Pagination.QUERY_ALL);
        queryTree.getPagination().setNumPerPage(65536);

        return queryTree;
    }

    private Map<String, Object> list2Map(List list, EntityType entityType) {
        if (list == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        for (Object obj: list) {
            map.put((String)EntityUtils.getFieldValue(getKeyFieldName(entityType), obj), obj);
        }

        return map;
    }

    public void fill(Object obj) {
        if (obj == null || obj.getClass().getAnnotation(Fillable.class) == null) {
            return;
        }

        for (Field field: obj.getClass().getDeclaredFields()) {
            Fill fill = field.getAnnotation(Fill.class);
            if (fill == null) {
                continue;
            }

            String fieldName = field.getName();

            if (List.class.isAssignableFrom(field.getType())) {
                Object value = EntityUtils.getFieldValue(fieldName, obj);
                if (value != null) {
                    fill((List) value);
                }
                continue;
            } else if (field.getType().getAnnotation(Fillable.class) != null) {
                Object value = EntityUtils.getFieldValue(fieldName, obj);
                if (value != null) {
                    fill(value);
                }
                continue;
            }

            String idFieldName = fill.value();
            if (idFieldName == null || idFieldName.isEmpty()) {
                idFieldName = fill.idFieldName();
            }
            if (idFieldName == null || idFieldName.isEmpty()) {
                idFieldName = field.getName() + "Id";
            }

            String key = (String)EntityUtils.getFieldValue(idFieldName, obj);

            String entityTypeStr = fill.entityType();
            if (entityTypeStr == null || entityTypeStr.isEmpty()) {
                 entityTypeStr = field.getName().toUpperCase();
            }

            EntityType entityType = null;
            try {
                entityType = EntityType.valueOf(entityTypeStr);
            } catch (Exception ex) {
            }
            if (entityType == null) {
                continue;
            }
            Object entity = key2entity(key, entityType);
            if (entity == null) {
                continue;
            }

            try {

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                pd.getWriteMethod().invoke(obj, entity);
            } catch (Exception ex) {
            }

        }
    }
    public void fill(List list) {
        list.forEach(this::fill);
    }

    public <T> void fill(Response<T> response) {
        if (response.getResponseEntity() != null) {
            fill(response.getResponseEntity());
        }

        if (response.getPageInfo() != null && response.getPageInfo().getList() != null) {
            fill(response.getPageInfo().getList());
        }
    }

    public <T> T key2entity(String id, EntityType entityType) {
        Map<String, Object> entityMap = getEntityMap(entityType);

        return entityMap == null ? null : (T)entityMap.get(id);
    }

    public <T> List<T> key2entity(List<String> idList, EntityType entityType) {
        return key2entity(idList, entityType, true);
    }

    public <T> List<T> key2entity(List<String> idList, EntityType entityType, boolean removeNullEntity) {
        return idList == null || idList.isEmpty() ? new ArrayList<>() : idList.stream().map(id -> (T) key2entity(id, entityType)).filter(entity -> !removeNullEntity || entity != null).collect(Collectors.toList());
    }
}
