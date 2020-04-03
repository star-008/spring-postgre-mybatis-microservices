package com.tianwen.springcloud.scoreapi.service.util.repo;

import org.springframework.stereotype.Service;

/**
 * Created by kimchh on 11/9/2018.
 */
@Service
public class IdRepositoryService extends RepositoryService {

    public static final String DEFAULT_KEY_FIELD_NAME = "id";
    public static final String DICTITEM_KEY_FIELD_NAME = "dictvalue";

    protected String getKeyFieldName(EntityType entityType) {
        switch (entityType) {
            case PAPER_VOLUME:
            case QUESTION_CATEGORY:
            case QUESTION_TYPE:
            case GRADE:
            case SUBJECT:
            case SCHOOL_SECTION:
            case EXAM_TYPE:
                return DICTITEM_KEY_FIELD_NAME;
            case EXAM_TERM:
                return "termId";
            case CLASS:
                return "classid";
            default:
                return DEFAULT_KEY_FIELD_NAME;
        }
    }
}
