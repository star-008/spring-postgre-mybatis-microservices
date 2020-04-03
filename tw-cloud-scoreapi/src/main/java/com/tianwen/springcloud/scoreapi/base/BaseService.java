package com.tianwen.springcloud.scoreapi.base;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.request.ExamGradeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.ExamService;
import com.tianwen.springcloud.scoreapi.service.util.ECOService;
import com.tianwen.springcloud.scoreapi.service.util.cache.AnalysisDataCacheService;
import com.tianwen.springcloud.scoreapi.service.util.repo.IdRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import com.tianwen.springcloud.scoreapi.session.UserSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
public class BaseService {

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectScoreMicroApi;

    @Autowired
    @Lazy
    protected AnalysisDataCacheService analysisDataCacheService;

    @Autowired
    protected IdRepositoryService repositoryService;

    @Autowired
    protected UserSessionBean sessionBean;

    @Autowired
    protected ECOService ecoService;

    public void resetRepository() {
        repositoryService.clearEntityMap();
    }

    public void resetClassRepository() {
        repositoryService.clearEntityMap(RepositoryService.EntityType.CLASS);
    }

    public List<DictItem> getExamGradeList(String examId) {
        return getExamGradeList(new ExamGradeFilter(examId));
    }

    public List<DictItem> getExamGradeList(ExamGradeFilter filter) {
        return repositoryService.key2entity(examSubjectScoreMicroApi.getGradeIdList(filter).getPageInfo().getList(), RepositoryService.EntityType.GRADE);
    }

    public List<DictItem> getExamSubjectList(String examId, String gradeId) {
        return getExamSubjectList(new ExamSubjectFilter(examId, gradeId));
    }

    public List<DictItem> getExamSubjectList(String examId, String gradeId, String classType) {
        return getExamSubjectList(new ExamSubjectFilter(examId, gradeId, classType));
    }

    public List<DictItem> getExamSubjectList(ExamSubjectFilter filter) {
        return repositoryService.key2entity(examSubjectScoreMicroApi.getSubjectIdList(filter).getPageInfo().getList(), RepositoryService.EntityType.SUBJECT);
    }

    protected boolean writeSheetData(SimpleSheet sheet, ExportedFile exportedFile) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(exportedFile.getPath()));
            sheet.write(fileOutputStream);
        } catch (Exception e) {
            return false;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return true;
    }

    protected Response<Exam> startExamEditing(String examId) {
        return BeanHolder.getBean(ExamService.class).startEditing(examId);
    }

    protected Response<Exam> startExamEditing(Exam exam) {
        return BeanHolder.getBean(ExamService.class).startEditing(exam);
    }

    protected <T extends Filter> T tweakFilter(T filter) {
        setTeacherScopeToFilter(filter);

        return filter;
    }

    protected <T extends Filter> T setTeacherScopeToFilter(T filter) {
        if (filter.doesCheckTeacherScope()) {
            filter.setTeacherId(sessionBean.getAccountId());
            filter.setTeacherClassIdList(sessionBean.getTeacherClassIdList());
            filter.setBelongsToManagerGeneral(sessionBean.getAccount().belongsToManagerGeneral());
        }

        return filter;
    }

    protected String getClassFullName(ClassInfo classInfo) {
        return classInfo == null ? "" : classInfo.getFullName();
    }

    protected String getSubjectName(DictItem subject) {
        return subject == null ? "" : subject.getDictname();
    }
}
