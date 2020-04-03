package com.tianwen.springcloud.scoreapi.service.util.repo;

import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.score.entity.request.ExamGradeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.scoreapi.service.ExamService;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kimchh on 11/16/2018.
 */
public class LocalNameRepositoryService extends RepositoryService {

    public static final String DEFAULT_KEY_FIELD_NAME = "name";
    public static final String DICTITEM_KEY_FIELD_NAME = "dictname";

    private Map<String, Map<EntityType, Map<String, Object>>> repositoryMap = new HashMap<>();

    private String examId;

    private String gradeId;

    public LocalNameRepositoryService(String examId) {
        this.examId = examId;
    }

    @Override
    protected String getKeyFieldName(EntityType entityType) {
        switch (entityType) {
            case PAPER_VOLUME:
            case QUESTION_CATEGORY:
            case QUESTION_TYPE:
            case GRADE:
            case SUBJECT:
            case SCHOOL_SECTION:
            case EXAM_TERM:
            case EXAM_TYPE:
                return DICTITEM_KEY_FIELD_NAME;
            default:
                return DEFAULT_KEY_FIELD_NAME;
        }
    }

    @Override
    public Map<EntityType, Map<String, Object>> getRepository() {
        String repositoryName = "repository[" + getRepositoryName() + "]";
        Map<EntityType, Map<String, Object>> repository = repositoryMap.get("repository[" + getRepositoryName() + "]");
        if (repository == null) {
            repository = new HashMap<>();
            repositoryMap.put(repositoryName, repository);
        }

        return repository;
    }

    protected List getList(EntityType entityType) {
        try {
            switch (entityType) {
                case GRADE:
                    return BeanHolder.getBean(ExamService.class).getGradeList(new ExamGradeFilter(examId)).getPageInfo().getList();
                case SUBJECT:
                    ExamSubjectFilter filter = new ExamSubjectFilter(examId);
                    if (!StringUtils.isEmpty(gradeId)) {
                        filter.setGradeId(gradeId);
                    }
                    return BeanHolder.getBean(ExamService.class).getSubjectList(filter).getPageInfo().getList();
                default:
                    return super.getList(entityType);
            }
        } catch (Exception ex) {
            System.err.println("Cannot load repository for " + entityType.name() + "(" + ex.getMessage() + ")");
        }
        return null;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}
