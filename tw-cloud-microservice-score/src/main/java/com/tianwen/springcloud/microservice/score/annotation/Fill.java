package com.tianwen.springcloud.microservice.score.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kimchh on 11/9/2018.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fill {
    public static final String ENTITY_TYPE_PAPER_VOLUME = "PAPER_VOLUME";
    public static final String ENTITY_TYPE_QUESTION_CATEGORY = "QUESTION_CATEGORY";
    public static final String ENTITY_TYPE_QUESTION_TYPE = "QUESTION_TYPE";
    public static final String ENTITY_TYPE_GRADE = "GRADE";
    public static final String ENTITY_TYPE_SUBJECT = "SUBJECT";
    public static final String ENTITY_TYPE_CLASS = "CLASS";

    public static final String ENTITY_TYPE_SCHOOL_SECTION = "SCHOOL_SECTION";
    public static final String ENTITY_TYPE_EXAM_TYPE = "EXAM_TYPE";
    public static final String ENTITY_TYPE_EXAM_TERM = "EXAM_TERM";

    @AliasFor("idFieldName")
    String value() default "";

    @AliasFor("value")
    String idFieldName() default "";

    String entityType() default "";
}
