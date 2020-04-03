package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.query.OrderMethod;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.api.ClassMicroApi;
import com.tianwen.springcloud.microservice.base.api.DictItemMicroApi;
import com.tianwen.springcloud.microservice.base.api.StudentMicroApi;
import com.tianwen.springcloud.microservice.base.api.TermMicroApi;
import com.tianwen.springcloud.microservice.base.entity.*;
import com.tianwen.springcloud.microservice.score.api.*;
import com.tianwen.springcloud.microservice.score.entity.*;
import com.tianwen.springcloud.microservice.score.entity.request.*;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.entity.request.ExamSyncInfo;
import com.tianwen.springcloud.scoreapi.entity.response.ECOTeach;
import com.tianwen.springcloud.scoreapi.service.util.repo.IdRepositoryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class ExamService extends BaseService {

    @Autowired
    private ExamMicroApi examMicroApi;

    @Autowired
    private ClassMicroApi classMicroApi;

    @Autowired
    private StudentMicroApi studentMicroApi;

    @Autowired
    private ExamClassMicroApi examClassMicroApi;

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectScoreMicroApi;

    @Autowired
    private ExamGradeClassSubjectMicroApi examGradeClassSubjectMicroApi;

    @Autowired
    private StudentSubjectScoreMicroApi studentSubjectScoreMicroApi;

    public Response<Exam> add(Exam exam) {
        return save(exam, true);
    }

    public Response<Exam> update(Exam exam) {
        return save(exam);
    }

    public Response<Exam> save(Exam exam) {
        return save(exam, false);
    }

    public Response<Exam> save(Exam exam, boolean bInserting) {

        UserLoginInfo account = sessionBean.getAccount();

        if (bInserting) {
            exam.setStatus(Exam.STATUS_INITIAL);
            exam.setCreatorId(account.getUserId());
            exam.setCreatorName(account.getRealName());
        } else {
            //Need to set modifierId and modifierName, but we don't have such fields in exam yet.
        }

        List<ClassSubject> classSubjectList = exam.getClassSubjectList();
        exam.setClassSubjectList(null);

        Exam.BaseData baseData = exam.getBase();
        exam.setBase(null);

        if (getDuplicatedExamByName(exam) != null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_DUPLICATED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_DUPLICATED
            );
        }

        if (getDuplicatedExamByDate(exam) != null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_DATE_DUPLICATED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_DATE_DUPLICATED
            );
        }

        Response<Exam> response;
        if (bInserting) {
            response = examMicroApi.add(exam);
        } else {
            clear(exam.getId());
            response = examMicroApi.update(exam);
        }

        if (null == (exam = response.getResponseEntity()) || exam.getId() == null || exam.getId().isEmpty()) {
            return response;
        }

        if (classSubjectList == null) {
            return response;
        }

        saveClassSubjectList(exam, classSubjectList);

        saveBaseData(baseData);

        //fillClassSubjectList(exam); //No need to fill since this data is not used on front side

        resetRepository();

        return new ApiResponse<>(response);
    }

    public Response<Exam> sync(ExamSyncInfo examSyncInfo) {
        UserLoginInfo account = sessionBean.getAccount();

        boolean bInserting = true;
        if (get(examSyncInfo.getExam().getId()).getResponseEntity() != null) {
            bInserting = false;
        }

        Exam exam = examSyncInfo.getExam();
        exam.setStatus(Exam.STATUS_HALF_PUBLISHED);
        exam.setCreatorId(account.getUserId());
        exam.setCreatorName(account.getRealName());
        exam.setSyncStatus(Exam.SYNC_STATUS_SYNCED);

        List<ClassSubject> classSubjectList = exam.getClassSubjectList();
        exam.setClassSubjectList(null);
        exam.setBase(null);

        if (getDuplicatedExamByName(exam) != null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_DUPLICATED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_DUPLICATED
            );
        }

        if (getDuplicatedExamByDate(exam) != null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_DATE_DUPLICATED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_DATE_DUPLICATED
            );
        }

        Response<Exam> response;
        if (bInserting) {
            response = examMicroApi.add(exam);
        } else {
            clear(exam.getId());
            response = examMicroApi.update(exam);
        }

        if (null == (exam = response.getResponseEntity()) || exam.getId() == null || exam.getId().isEmpty()) {
            return response;
        }

        if (classSubjectList == null) {
            return response;
        }

        saveClassSubjectList(exam, classSubjectList);
        updateStudentSubjectScore(examSyncInfo);

        resetRepository();

        return new ApiResponse<>(response);
    }

    public Exam getDuplicatedExamByName(Exam exam) {
        QueryTree queryTree = new QueryTree();
        if (!StringUtils.isEmpty(exam.getId())) {
            queryTree.addCondition(new QueryCondition("examid", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, exam.getId()));
        }
        queryTree.addCondition(new QueryCondition("examname", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, exam.getName()));
        queryTree.addCondition(new QueryCondition("status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));

        List<Exam> examList = examMicroApi.search(queryTree).getPageInfo().getList();

        return !CollectionUtils.isEmpty(examList) ? examList.get(0) : null;
    }

    public Exam getDuplicatedExamByDate(Exam exam) {
        QueryTree queryTree = new QueryTree();
        if (!StringUtils.isEmpty(exam.getId())) {
            queryTree.addCondition(new QueryCondition("examid", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, exam.getId()));
        }
        queryTree.addCondition(new QueryCondition("status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));

        queryTree.addCondition(new QueryCondition("examendday::DATE::CHARACTER VARYING", QueryCondition.Prepender.AND, QueryCondition.Operator.GREATER_EQUAL, DateFormatUtils.format(exam.getStartDate(), DateFormatUtils.ISO_DATE_FORMAT.getPattern())));
        queryTree.addCondition(new QueryCondition("examstartday::DATE::CHARACTER VARYING", QueryCondition.Prepender.AND, QueryCondition.Operator.LESS_EQUAL, DateFormatUtils.format(exam.getEndDate(), DateFormatUtils.ISO_DATE_FORMAT.getPattern())));

        List<Exam> examList = examMicroApi.search(queryTree).getPageInfo().getList();

        return !CollectionUtils.isEmpty(examList) ? examList.get(0) : null;
    }

    public Response<Exam> delete(String id)
    {
        Exam exam = new Exam();
        exam.setId(id);
        exam.setStatus(Exam.STATUS_DELETED);
        examMicroApi.update(exam);
        return new Response<>();
    }

    public Response<Exam> closeSync(String id)
    {
        Exam exam = new Exam();
        exam.setId(id);
        exam.setSyncStatus(Exam.SYNC_STATUS_CLOSED);
        examMicroApi.update(exam);
        return new Response<>();
    }

    public Response<Exam> search(QueryTree queryTree) {

        queryTree.addCondition(new QueryCondition("status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));

        queryTree.orderBy("(CASE WHEN creatorid='" + sessionBean.getAccountId() + "' THEN 1 ELSE 0 END)", OrderMethod.Method.DESC);
        queryTree.orderBy("(CASE WHEN status='" + Exam.STATUS_PUBLISHED + "' THEN 0 ELSE 1 END)", OrderMethod.Method.DESC);
        queryTree.orderBy("examstartday", OrderMethod.Method.DESC);

        return new ApiResponse<>(examMicroApi.search(queryTree));
    }

    public Response<Exam> recent(Integer limit, QueryTree queryTree) {

        queryTree.addCondition(new QueryCondition("status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));

        queryTree.orderBy("(CASE WHEN creatorid='" + sessionBean.getAccountId() + "' THEN 1 ELSE 0 END)", OrderMethod.Method.DESC);
        queryTree.orderBy("(CASE WHEN status='" + Exam.STATUS_PUBLISHED + "' THEN 0 ELSE 1 END)", OrderMethod.Method.DESC);
        queryTree.orderBy("examstartday", OrderMethod.Method.DESC);

        queryTree.getPagination().setNumPerPage(limit);

        Response<Exam> response = examMicroApi.search(queryTree);

        fillClassSubjectList(response.getPageInfo().getList());

        return new ApiResponse<>(response);
    }

    public Response<Exam> get(String id)
    {
        Response<Exam> response = examMicroApi.get(id);
        Exam exam = response.getResponseEntity();

        if (exam != null && exam.getId() != null && !exam.getId().isEmpty()) {
            fillClassSubjectList(exam);
        }

        return new ApiResponse<>(response);
    }

    public void fillClassSubjectList(List<Exam> examList) {
        if (examList != null && examList.size() > 0) {
            examList.forEach(this::fillClassSubjectList);
        }
    }
    public void fillClassSubjectList(Exam exam) {
        List<ExamClass> examClassList = examClassMicroApi.searchByEntity(new ExamClass(exam.getId())).getPageInfo().getList();
        List<ExamSubjectScore> examSubjectList = examSubjectScoreMicroApi.searchByEntity(new ExamSubjectScore(exam.getId())).getPageInfo().getList();
        exam.fillClassSubjectList(
                examClassList,
                examSubjectList
        );
    }

    public void saveClassSubjectList(Exam exam, List<ClassSubject> classSubjectList)  {

        List<ExamClass> examClassList = new ArrayList<>();
        List<ExamSubjectScore> examSubjectList = new ArrayList<>();
        List<ExamGradeClassSubject> examGradeClassSubjectList = new ArrayList<>();
        List<StudentSubjectScore> studentSubjectScoreList = new ArrayList<>();

        List<ClassInfo> allClassList = new ArrayList<>();
        List<Student> allStudentList = new ArrayList<>();

        List<Student> studentList;
        String teacherId, teacherName;
        ECOTeach teach;

        for (ClassSubject classSubject: classSubjectList) {

            //region //Make list of exam subject to save into db
            if (classSubject.getExamSubjectList() != null) {
                for (ExamSubjectScore examSubject: classSubject.getExamSubjectList()) {
                    examSubject.setExamId(exam.getId());
                    examSubject.setGradeId(classSubject.getGradeId());
                    examSubject.setScore(ExamSubjectScore.DEFAULT_SCORE);
                    examSubject.setStatus(ExamSubjectScore.STATUS_INITIAL);
                    examSubject.setPubStatus(ExamSubjectScore.PUB_STATUS_INITIAL);
                    examSubject.setClassType(ClassInfo.CLASS_TYPE_NORMAL);

                    examSubjectList.add(examSubject);
                }
            }
            //endregion

            //Loads list of subject teachers from ECO system for ClassInfo.CLASS_TYPE_NORMAL in this grade
            Map<String, List<ECOTeach>> teachMap = loadTeachMap(exam.getSchoolId(), classSubject.getGradeId(), ClassInfo.CLASS_TYPE_NORMAL);

            //region //Make list of exam class to save into db
            if (classSubject.getExamClassList() != null) {
                for (ExamClass examClass : classSubject.getExamClassList()) {
                    examClass.setExamId(exam.getId());
                    examClass.setGradeId(classSubject.getGradeId());
                    examClass.setClassType(ClassInfo.CLASS_TYPE_NORMAL);

                    ClassInfo classInfo = examClass.getClassInfo();
                    if (StringUtils.isEmpty(classInfo.getClassid())) {
                        classInfo.setClassid(examClass.getClassId());
                    }
                    classInfo.setClassType(ClassInfo.CLASS_TYPE_NORMAL);

                    //Save class info into base table while adding exam
                    //saveClass(classInfo);
                    //Adding class into allClassList to save into db
                    allClassList.add(classInfo);

                    examClass.setClassInfo(null);
                    examClassList.add(examClass);

                    studentList = loadStudentList(examClass.getClassId(), examClass.getClassType());
                    allStudentList.addAll(studentList);

                    for (ExamSubjectScore examSubject: classSubject.getExamSubjectList()) {

                        teach = findECOTeach(teachMap, examClass.getClassId(), examSubject.getSubjectId());
                        if (teach != null) {
                            teacherId = teach.getUserId();
                            teacherName = teach.getTeacherName();
                        } else {
                            teacherId = null;
                            teacherName = null;
                        }

                        ExamGradeClassSubject examGradeClassSubject = new ExamGradeClassSubject(
                                examClass.getExamId(),
                                examClass.getGradeId(),
                                examClass.getClassId(),
                                examSubject.getSubjectId(),
                                ClassInfo.CLASS_TYPE_NORMAL,
                                teacherId,
                                teacherName
                        );
                        examGradeClassSubjectList.add(examGradeClassSubject);

                        if (!CollectionUtils.isEmpty(studentList)) {
                            studentSubjectScoreList.addAll(studentList.stream().map(student -> new StudentSubjectScore(examGradeClassSubject, student)).collect(Collectors.toList()));
                        }
                    }
                }
            }
            //endregion

            //region //Make list of exam class and exam subject for special class to save into db
            if (classSubject.getExamSpecialityClassList() != null) {

                //Loads list of subject teachers from ECO system for ClassInfo.CLASS_TYPE_SPECIAL in this grade
                teachMap = loadTeachMap(exam.getSchoolId(), classSubject.getGradeId(), ClassInfo.CLASS_TYPE_SPECIAL);

                for (SpecialityClass specialityClass : classSubject.getExamSpecialityClassList()) {

                    ExamSubjectScore examSubject = specialityClass.getExamSubject();
                    if (examSubject == null || StringUtils.isEmpty(examSubject.getSubjectId())) {
                        continue;
                    }

                    examSubject.setExamId(exam.getId());
                    examSubject.setGradeId(classSubject.getGradeId());
                    examSubject.setScore(ExamSubjectScore.DEFAULT_SCORE);
                    examSubject.setStatus(ExamSubjectScore.STATUS_INITIAL);
                    examSubject.setPubStatus(ExamSubjectScore.PUB_STATUS_INITIAL);
                    examSubject.setClassType(ClassInfo.CLASS_TYPE_SPECIAL);

                    examSubjectList.add(examSubject);

                    if (specialityClass.getExamClassList() != null) {
                        for (ExamClass examClass : specialityClass.getExamClassList()) {
                            examClass.setExamId(exam.getId());
                            examClass.setGradeId(classSubject.getGradeId());
                            examClass.setClassType(ClassInfo.CLASS_TYPE_SPECIAL);
                            examClass.setSubjectId(examSubject.getSubjectId());

                            ClassInfo classInfo = examClass.getClassInfo();
                            if (StringUtils.isEmpty(classInfo.getClassid())) {
                                classInfo.setClassid(examClass.getClassId());
                            }
                            classInfo.setClassType(ClassInfo.CLASS_TYPE_SPECIAL);

                            //Adding class into allClassList to save into db
                            allClassList.add(classInfo);

                            examClass.setClassInfo(null);
                            examClassList.add(examClass);

                            teach = findECOTeach(teachMap, examClass.getClassId(), examSubject.getSubjectId());
                            if (teach != null) {
                                teacherId = teach.getUserId();
                                teacherName = teach.getTeacherName();
                            } else {
                                teacherId = null;
                                teacherName = null;
                            }

                            ExamGradeClassSubject examGradeClassSubject = new ExamGradeClassSubject(
                                    examClass.getExamId(),
                                    examClass.getGradeId(),
                                    examClass.getClassId(),
                                    examSubject.getSubjectId(),
                                    ClassInfo.CLASS_TYPE_SPECIAL,
                                    teacherId,
                                    teacherName
                            );
                            examGradeClassSubjectList.add(examGradeClassSubject);

                            studentList = loadStudentList(examClass.getClassId(), examClass.getClassType());
                            allStudentList.addAll(studentList);

                            if (!CollectionUtils.isEmpty(studentList)) {
                                studentSubjectScoreList.addAll(studentList.stream().map(student ->
                                        new StudentSubjectScore(
                                                examGradeClassSubject, student
                                        )
                                ).collect(Collectors.toList()));
                            }

                            classInfo.setAdviserIds(null);
                            classInfo.setAdviserNames(null);
                        }
                    }
                }
            }
            //endregion

        }

        examClassMicroApi.batchAdd(examClassList);
        examSubjectScoreMicroApi.batchAdd(examSubjectList);
        examGradeClassSubjectMicroApi.batchAdd(examGradeClassSubjectList);
        studentSubjectScoreMicroApi.batchAdd(studentSubjectScoreList);

        classMicroApi.batchAddUpdate(allClassList);
        studentMicroApi.batchAddUpdate(allStudentList);
    }

    private void updateStudentSubjectScore(ExamSyncInfo examSyncInfo) {
        List<StudentSubjectScore> studentSubjectScoreList = getStudentSubjectList(examSyncInfo.getExam().getId());
        List<StudentSubjectScore> studentSubjectScoreUpdateList = new ArrayList<>();
        int index;
        for (StudentSubjectScore studentSubjectScore: examSyncInfo.getStudentSubjectScoreList()) {
            if ((index = studentSubjectScoreList.indexOf(studentSubjectScore)) < 0) continue;
            StudentSubjectScore score = studentSubjectScoreList.get(index);
            score.setScore(studentSubjectScore.getScore());
            studentSubjectScoreUpdateList.add(score);
        }

        if (studentSubjectScoreUpdateList.size() > 0) {
            studentSubjectScoreMicroApi.batchUpdate(studentSubjectScoreUpdateList);
        }
    }

    public List<StudentSubjectScore> getStudentSubjectList(String examId) {
        QueryTree queryTree = new QueryTree().page(Pagination.QUERY_ALL, 0);
        queryTree.getPagination().setNumPerPage(0);
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));

        return studentSubjectScoreMicroApi.search(queryTree).getPageInfo().getList();
    }

    private Map<String, List<ECOTeach>> loadTeachMap(String orgId, String gradeId, String classType) {
        Map<String, List<ECOTeach>> teachMap = new HashMap<>();

        List<ECOTeach> teachList;
        for (ECOTeach teach: ecoService.getTeachList(orgId, gradeId, classType).getPageInfo().getList()) {
            teachList = teachMap.get(teach.getClassId());
            if (teachList == null) {
                teachList = new ArrayList<>();
                teachMap.put(teach.getClassId(), teachList);
            }

            teachList.add(teach);
        }

        return teachMap;
    }

    private ECOTeach findECOTeach(Map<String, List<ECOTeach>> teachMap, String classId, String subjectId) {
        List<ECOTeach> teachList = teachMap.get(classId);
        if (CollectionUtils.isEmpty(teachList)) {
            return null;
        }

        ECOTeach teach = null;
        int teachIndex;
        if ((teachIndex = teachList.indexOf(new ECOTeach(classId, subjectId))) >= 0) {
            teach = teachList.get(teachIndex);
        }

        return teach;
    }

    private List<Student> loadStudentList(String classId, String classType) {
        List studentList = null;
        Response<Student> response = ecoService.getStudentList(classId, classType);
        if (response != null && response.getPageInfo() != null) {
            studentList = response.getPageInfo().getList();
        }

        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        return studentList;
    }

    private void saveBaseData(Exam.BaseData baseData) {
        if (!CollectionUtils.isEmpty(baseData.getTermList())) {
            BeanHolder.getBean(TermMicroApi.class).batchAddUpdate(baseData.getTermList());
        }

        List<DictItem> baseDataList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(baseData.getTypeList())) {
            baseDataList.addAll(baseData.getTypeList());
        }

        if (!CollectionUtils.isEmpty(baseData.getSchoolSectionList())) {
            baseDataList.addAll(baseData.getSchoolSectionList());
        }

        if (!CollectionUtils.isEmpty(baseData.getGradeList())) {
            baseDataList.addAll(baseData.getGradeList());
        }

        if (!CollectionUtils.isEmpty(baseData.getSubjectList())) {
            baseDataList.addAll(baseData.getSubjectList());
        }

        BeanHolder.getBean(DictItemMicroApi.class).batchAddUpdate(baseDataList);
    }

    private void clear(String examId) {
        examClassMicroApi.deleteByEntity(new ExamClass(examId));
        examSubjectScoreMicroApi.deleteByEntity(new ExamSubjectScore(examId));
        examGradeClassSubjectMicroApi.deleteByEntity(new ExamGradeClassSubject(examId));
        BeanHolder.getBean(ExamPartScoreMicroApi.class).deleteByEntity(new ExamPartScore(examId));
        studentSubjectScoreMicroApi.deleteByEntity(new StudentSubjectScore(examId));
        BeanHolder.getBean(StudentPartScoreMicroApi.class).deleteByEntity(new StudentPartScore(examId));
        BeanHolder.getBean(StudentSubjectModifyScoreMicroApi.class).deleteByEntity(new StudentSubjectModifyScore(examId));
        BeanHolder.getBean(SysAnalysisConfigMicroApi.class).deleteByEntity(new SysAnalysisConfig(examId));
        BeanHolder.getBean(SysAnalysisScoreLevelMicroApi.class).deleteByEntity(new SysAnalysisScoreLevel(examId));
    }

    public Response<Exam> startEditing(String examId) {

        Exam exam = examMicroApi.get(examId).getResponseEntity();
        if (exam == null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_NOT_FOUND
            );
        }

        return startEditing(exam);
    }

    public Response<Exam> startEditing(Exam exam) {

        exam.setStatus(Exam.STATUS_EDITING);

        Response<Exam> response = examMicroApi.update(exam);
        if (IStateCode.HTTP_200.equals(response.getServerResult().getResultCode())) {
            analysisDataCacheService.clear(exam.getId());
        }

        return response;
    }

    public Response<Exam> publish(String examId) {

        Exam exam = examMicroApi.get(examId).getResponseEntity();
        if (exam == null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_NOT_FOUND
            );
        }

        if (BeanHolder.getBean(ExamSubjectScoreService.class).hasUnpublishedOne(examId)) {
            exam.setStatus(Exam.STATUS_HALF_PUBLISHED);
        } else {
            exam.setStatus(Exam.STATUS_PUBLISHED);
        }

        exam.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));

        return examMicroApi.update(exam);
    }

    public Response<Exam> cancelPublishing(String examId) {

        Exam exam = examMicroApi.get(examId).getResponseEntity();
        if (exam == null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_NOT_FOUND
            );
        }

        if (BeanHolder.getBean(ExamSubjectScoreService.class).hasPublishedOne(examId)) {
            exam.setStatus(Exam.STATUS_HALF_PUBLISHED);
        } else {
            exam.setStatus(Exam.STATUS_EDITING);
        }

        exam.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));

        Response<Exam> response = examMicroApi.update(exam);
        if (IStateCode.HTTP_200.equals(response.getServerResult().getResultCode())) {
            analysisDataCacheService.clear(exam.getId());
        }

        return response;
    }

    public Response<Exam> getSimpleList(Filter filter) {
        Request request = new Request(tweakFilter(filter));
        request.orderBy("(CASE WHEN creatorid='" + sessionBean.getAccountId() + "' THEN 1 ELSE 0 END)", OrderMethod.Method.DESC);
        request.orderBy("(CASE WHEN status='" + Exam.STATUS_PUBLISHED + "' THEN 0 ELSE 1 END)", OrderMethod.Method.DESC);
        request.orderBy("examstartday", OrderMethod.Method.DESC);

        return examMicroApi.getSimpleList(request);
    }

    public Response<Term> getTermList(Filter filter) {
        return new Response<>(repositoryService.key2entity(examMicroApi.getTermIdList(tweakFilter(filter)).getPageInfo().getList(), IdRepositoryService.EntityType.EXAM_TERM));
    }

    public Response<DictItem> getTypeList(ExamTypeFilter filter) {
        return new Response<>(repositoryService.key2entity(examMicroApi.getTypeIdList(tweakFilter(filter)).getPageInfo().getList(), IdRepositoryService.EntityType.EXAM_TYPE));
    }

    public Response<DictItem> getGradeList(ExamGradeFilter filter) {
        return new Response<>(repositoryService.key2entity(examSubjectScoreMicroApi.getGradeIdList(tweakFilter(filter)).getPageInfo().getList(), IdRepositoryService.EntityType.GRADE));
    }

    public Response<DictItem> getSubjectList(ExamSubjectFilter filter) {
        if (!CollectionUtils.isEmpty(filter.getSubjectIdList())) {
            return new Response<>(repositoryService.key2entity(filter.getSubjectIdList(), IdRepositoryService.EntityType.SUBJECT));
        }
        return new Response<>(repositoryService.key2entity(examSubjectScoreMicroApi.getSubjectIdList(tweakFilter(filter)).getPageInfo().getList(), IdRepositoryService.EntityType.SUBJECT));
    }

    public List<String> getClassIdList(ExamClassFilter filter) {
        return examClassMicroApi.getClassIdList(tweakFilter(filter)).getPageInfo().getList();
    }

    public Response<ClassInfo> getClassList(ExamClassFilter filter) {
        List<ClassInfo> classList;
        if (!CollectionUtils.isEmpty(filter.getClassIdList())) {
            classList = repositoryService.key2entity(filter.getClassIdList(), IdRepositoryService.EntityType.CLASS);
        } else {
            classList = repositoryService.key2entity(getClassIdList(filter), IdRepositoryService.EntityType.CLASS);
        }

        Collections.sort(classList, ClassInfo::compareName);

        return new Response<>(classList);
    }

    public boolean isAccountExamOwner(String examId) {

        Exam exam = examMicroApi.get(examId).getResponseEntity();

        return exam != null && sessionBean.getAccountId().equals(exam.getCreatorId());
    }
}
