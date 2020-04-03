package com.tianwen.springcloud.microservice.score.entity;

import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by kimchh on 11/25/2018.
 */
@Fillable
public class SpecialityClass {

    @Fill
    private ExamSubjectScore examSubject;

    @Fill
    private List<ExamClass> examClassList;

    public SpecialityClass() {

    }

    public SpecialityClass(ExamSubjectScore examSubject) {
        this.examSubject = examSubject;
    }

    public ExamSubjectScore getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(ExamSubjectScore examSubject) {
        this.examSubject = examSubject;
    }

    public List<ExamClass> getExamClassList() {
        return examClassList;
    }

    public void setExamClassList(List<ExamClass> examClassList) {
        this.examClassList = examClassList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SpecialityClass)
            return equals((SpecialityClass)object);

        return false;
    }

    private boolean equals(SpecialityClass specialityClass) {
        if (null == specialityClass) {
            return false;
        } else if (null == examSubject) {
            if (null == specialityClass.examSubject) {
                return true;
            } else {
                return false;
            }
        } else if (null == specialityClass.examSubject) {
            return false;
        } else if (null == examSubject.getSubjectId()) {
            return (StringUtils.isEmpty(examSubject.getSubjectId()) &&
                    StringUtils.isEmpty(specialityClass.examSubject.getSubjectId()));
        } else {
            return examSubject.getSubjectId().equals(specialityClass.examSubject.getSubjectId());
        }
    }
}
