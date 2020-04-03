package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.api.StudentMicroApi;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentSubjectModifyScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.entity.word.SimpleWord;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class StudentSubjectScoreService extends BaseService {

    public static final String TEMPLATE_FILE_PATH = "templates/student-subject-score.xls";
    public static final String EXPORT_ZIP_FILE_NAME = "{examName}-成绩.zip";
    public static final String EXPORT_EXCEL_FILE_NAME = "{gradeName}-成绩.xls";
    public static final String EXPORT_WORD_FILE_NAME = "成绩.docx";

    private static final String EXPORT_TABLE_HEADER1 = "考试名称";
    private static final String TITLE_PERSONAL_SCORE = "个人成绩册";
    private static final String TOTAL_SCORE = "总分";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);
    protected static final CellPos CELL_POS_SUBJECT_NAME = new CellPos(1, 5);
    protected static final int ROW_INDEX_STUDENT_START = 3;

    protected static final boolean EXPORT_SCORE = false;

    @Autowired
    private StudentSubjectScoreMicroApi studentSubjectScoreMicroApi;

    @Autowired
    private StudentSubjectModifyScoreMicroApi studentSubjectModifyScoreMicroApi;

    public String exportToWord(QueryTree queryTree) throws IOException, XmlException {
        List<StudentSubjectScore> studentSubjectScoreList = search(queryTree).getPageInfo().getList();

        Map<String, List<StudentSubjectScore>> studentSubjectScoreMap = new HashMap<>();
        List<String> studentIdList = new ArrayList<>();

        List<StudentSubjectScore> subList;
        for (StudentSubjectScore studentSubjectScore: studentSubjectScoreList) {

            String studentId = studentSubjectScore.getStudentId();

            if (studentIdList.indexOf(studentId) < 0) {
                studentIdList.add(studentId);

                subList = new ArrayList<>();
                studentSubjectScoreMap.put(studentId, subList);
            } else {
                subList = studentSubjectScoreMap.get(studentId);
            }

            subList.add(studentSubjectScore);
        }

        List<DictItem> subjectList = getSubjectList(queryTree);

        SimpleWord simpleWord = new SimpleWord();
        if (!CollectionUtils.isEmpty(studentIdList)) {
            int i = 1;
            for (String studentId : studentIdList) {
                List<StudentSubjectScore> scoreList = studentSubjectScoreMap.get(studentId);//each student subject score list

                if (!CollectionUtils.isEmpty(scoreList)) {
                    StudentSubjectScore subjectScore = scoreList.get(0);
                    if (StringUtils.isEmpty(subjectScore.getStudentNo())) {
                        simpleWord.addTitle(subjectScore.getStudentName() + " " + TITLE_PERSONAL_SCORE);
                    } else {
                        simpleWord.addTitle(subjectScore.getStudentName() + " (" + subjectScore.getStudentNo() + ") " + TITLE_PERSONAL_SCORE);
                    }

                    List<String> headers = new ArrayList<>();
                    List<String> subjectIdList = new ArrayList<>();
                    List<DictItem> existingSubjectList = new ArrayList<>();

                    List<String> studentExamIdList = new ArrayList<>();
                    Map<String, List<StudentSubjectScore>> studentExamMap = new HashMap<>();
                    for (StudentSubjectScore score : scoreList) {
                        String examId = score.getExamId();
                        subList = studentExamMap.get(examId);

                        if (subList == null) {
                            studentExamIdList.add(examId);

                            subList = new ArrayList<>();
                            studentExamMap.put(examId, subList);
                        }
                        subList.add(score);
                    }

                    for (String examId : studentExamIdList) {
                        List<StudentSubjectScore> datas = studentExamMap.get(examId);

                        for (DictItem subject : subjectList) {
                            int index = getIndexOfData(datas, subject.getDictvalue());
                            if (index >= 0 && existingSubjectList.indexOf(subject) == -1) {
                                existingSubjectList.add(subject);
                            }
                        }
                    }

                    headers.add(EXPORT_TABLE_HEADER1);
                    for (DictItem subject : existingSubjectList) {
                        headers.add(subject.getDictname());
                        subjectIdList.add(subject.getDictvalue());
                    }

                    SimpleWord.SimpleTable table = simpleWord.createTable(studentExamIdList.size(), headers.size());
                    headers.add(TOTAL_SCORE);
                    table.setTopHeader(headers);

                    for (String examId : studentExamIdList) {
                        List<StudentSubjectScore> datas = studentExamMap.get(examId);
                        List<String> rowData = new ArrayList<>();

                        if (!CollectionUtils.isEmpty(datas)) {
                            Exam exam = datas.get(0).getExam();
                            if (exam != null)
                                rowData.add(exam.getName());
                        }

                        Double totalScore = 0.0;
                        for (String subjectid : subjectIdList) {
                            int index = getIndexOfData(datas, subjectid);
                            String score = null;
                            if (index >= 0) {
                                StudentSubjectScore subdata = datas.get(index);
                                score = subdata.getScore();
                                if (score.isEmpty()) {

                                } else if (Double.parseDouble(score) == -1) {
                                    score = ICommonConstants.EXAM_MISSED;
                                } else {
                                    totalScore += Double.parseDouble(score);
                                }
                            }
                            rowData.add(score==null?"":score);
                        }
                        rowData.add(String.valueOf(totalScore));
                        table.setRowData(rowData);
                    }
                }

                if (studentIdList.size() > i ++) { //If current student is not last student
                    simpleWord.addBreak();
                }
            }
        }

        ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(EXPORT_WORD_FILE_NAME, ICommonConstants.CONTENT_TYPE_WORD);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(exportedFile.getPath()));
            simpleWord.write(fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
    }

    protected List<DictItem> getSubjectList(QueryTree queryTree) {
        ExamSubjectFilter examSubjectFilter = new ExamSubjectFilter();
        List<String> examIds = getFieldValueList(queryTree, "pt.examid");
        if (!CollectionUtils.isEmpty(examIds)) {
            examSubjectFilter.setExamIdList(examIds);
        }

        String gradeId = getFieldValue(queryTree, "gradeid");
        if (!StringUtils.isEmpty(gradeId)) {
            examSubjectFilter.setGradeId(gradeId);
        }

        String classId = getFieldValue(queryTree, "classid");
        if (!StringUtils.isEmpty(classId)) {
            examSubjectFilter.setClassId(classId);
        }

        String termId = getFieldValue(queryTree, "termid");
        if (!StringUtils.isEmpty(termId)) {
            examSubjectFilter.setTermId(termId);
        }

        String examTypeId = getFieldValue(queryTree, "examtypeid");
        if (!StringUtils.isEmpty(examTypeId)) {
            examSubjectFilter.setExamTypeId(examTypeId);
        }

        examSubjectFilter.setPubStatusPublished();

        return getExamSubjectList(examSubjectFilter);
    }

    public Response<StudentSubjectScore> save(List<StudentSubjectScore> studentSubjectScoreList) {
        studentSubjectScoreList.forEach(studentSubjectScore -> studentSubjectScore.setStatus(StudentSubjectScore.STATUS_SAVED));
        return studentSubjectScoreMicroApi.batchUpdate(studentSubjectScoreList);
    }

    public Response<StudentSubjectScore> search(QueryTree queryTree) {
        queryTree.addCondition(new QueryCondition("exam.status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));
        return new ApiResponse<>(studentSubjectScoreMicroApi.search(queryTree));
    }

    public Response<Student> getStudentListForChange(Filter filter) {
        QueryTree queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("classId", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, filter.getClassId()));
        Response<Student> response = BeanHolder.getBean(StudentMicroApi.class).search(queryTree);

        queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, filter.getExamId()));
        queryTree.addCondition(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, filter.getSubjectId()));
        queryTree.addCondition(new QueryCondition("pt.classid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, filter.getClassId()));
        queryTree.addCondition(new QueryCondition("pt.status", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, StudentSubjectModifyScore.STATUS_INITIAL));
        Response<StudentSubjectModifyScore> response1 = studentSubjectModifyScoreMicroApi.search(queryTree);
        List<String> requestedStudentIdList = response1.getPageInfo().getList().stream().map(StudentSubjectModifyScore::getStudentId).collect(Collectors.toList());

        Iterator<Student> it = response.getPageInfo().getList().iterator();
        int index;
        while (it.hasNext()) {
            if ((index = requestedStudentIdList.indexOf(it.next().getUserId())) >= 0) {
                requestedStudentIdList.remove(index);
                it.remove();
            }
        }

        return response;
    }

    public Response<StudentSubjectScore> searchForChange(QueryTree queryTree) {
        queryTree.addCondition(new QueryCondition("exam.status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));
        queryTree.addCondition(new QueryCondition("eg.pubstatus", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, ExamSubjectScore.PUB_STATUS_PUBLISHED));
        return new ApiResponse<>(studentSubjectScoreMicroApi.search(queryTree));
    }

    public Response<StudentSubjectModifyScore> requestChange(StudentSubjectModifyScore studentSubjectModifyScore) {

        if (!sessionBean.getAccount().belongsToManagerGeneral() && !sessionBean.getAccount().isTeacher()) {
            return new ApiResponse<>(
                    IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_ONLY_TEACHER,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_ONLY_TEACHER
            );
        }

        StudentSubjectScore studentSubjectScore = studentSubjectScoreMicroApi.get(studentSubjectModifyScore.getStudentSubjectId()).getResponseEntity();
//        if (!sessionBean.hasTeacherClass(studentSubjectScore.getClassId()) && !sessionBean.isSubjectTeacher(studentSubjectScore.getTeacherId())) {
//            return new ApiResponse<>(
//                    IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_DENIED,
//                    IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_REQUEST_CHANGE_DENIED
//            );
//        }

        String newScore = studentSubjectModifyScore.getModifyScore();
        Float newScoreF;
        if (!(NumberUtils.isNumber(newScore)) || ((newScoreF = NumberUtils.toFloat(newScore)) != -1 && newScoreF < 0)) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID
            );
        }

        ExamSubjectScore examSubjectScore = new ExamSubjectScore(studentSubjectScore.getExamId(), studentSubjectScore.getGradeId(), studentSubjectScore.getSubjectId());
        Response<ExamSubjectScore> response = BeanHolder.getBean(ExamSubjectScoreMicroApi.class).searchByEntity(examSubjectScore);
        if (response.getPageInfo().getList() == null || response.getPageInfo().getList().size() <= 0) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_SCORE,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_SCORE
            );
        }
        examSubjectScore = response.getPageInfo().getList().get(0);

        if (newScoreF > NumberUtils.toFloat(examSubjectScore.getScore())) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID1,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SCORE_INVALID1
            );
        }

        studentSubjectModifyScore.setExamId(studentSubjectScore.getExamId());
        studentSubjectModifyScore.setGradeId(studentSubjectScore.getGradeId());
        studentSubjectModifyScore.setSubjectId(studentSubjectScore.getSubjectId());
        studentSubjectModifyScore.setClassId(studentSubjectScore.getClassId());
        studentSubjectModifyScore.setClassType(studentSubjectScore.getClassType());
        studentSubjectModifyScore.setStudentId(studentSubjectScore.getStudentId());
        
        studentSubjectModifyScore.setStatus(StudentSubjectModifyScore.STATUS_INITIAL);
        studentSubjectModifyScore.setApplicantId(sessionBean.getAccount().getUserId());
        studentSubjectModifyScore.setApplicantName(sessionBean.getAccount().getRealName());
        studentSubjectModifyScore.setApplTime(new Timestamp(System.currentTimeMillis()));

        return studentSubjectModifyScoreMicroApi.add(studentSubjectModifyScore);
    }

    public Response<StudentSubjectModifyScore> acceptChanges(List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        List<StudentSubjectScore> studentSubjectScoreList = new ArrayList<>();
        for (StudentSubjectModifyScore studentSubjectModifyScore: studentSubjectModifyScoreList) {
            studentSubjectScoreList.add(
                    new StudentSubjectScore(
                            studentSubjectModifyScore.getStudentSubjectId(),
                            studentSubjectModifyScore.getModifyScore())
            );
            studentSubjectModifyScore.setApproverId(sessionBean.getAccount().getUserId());
            studentSubjectModifyScore.setApproverName(sessionBean.getAccount().getRealName());
            studentSubjectModifyScore.setApprTime(new Timestamp(System.currentTimeMillis()));
            studentSubjectModifyScore.setStatus(StudentSubjectModifyScore.STATUS_ACCEPTED);
        }

        Response response = studentSubjectScoreMicroApi.batchUpdate(studentSubjectScoreList);
        if (IStateCode.HTTP_200.equals(response.getServerResult().getResultCode())) {
            analysisDataCacheService.clear(studentSubjectScoreList.stream().map(StudentSubjectScore::getExamId).collect(Collectors.toList()));
        }
        return studentSubjectModifyScoreMicroApi.batchUpdate(studentSubjectModifyScoreList);
    }

    public Response<StudentSubjectModifyScore> rejectChanges(List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        for (StudentSubjectModifyScore studentSubjectModifyScore: studentSubjectModifyScoreList) {
            studentSubjectModifyScore.setApproverName(sessionBean.getAccount().getRealName());
            studentSubjectModifyScore.setApprTime(new Timestamp(System.currentTimeMillis()));
            studentSubjectModifyScore.setStatus(StudentSubjectModifyScore.STATUS_REJECTED);
        }

        return studentSubjectModifyScoreMicroApi.batchUpdate(studentSubjectModifyScoreList);
    }

    public Response<StudentSubjectModifyScore> searchChanges(QueryTree querytree) {

        if (!sessionBean.getAccount().belongsToManagerGeneral()) {
            querytree.addCondition(new QueryCondition("pt.applicantid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, sessionBean.getAccount().getUserId()));
        }

        querytree.addCondition(new QueryCondition("pt.status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, StudentSubjectModifyScore.STATUS_DELETED));

        Response<StudentSubjectModifyScore> response = studentSubjectModifyScoreMicroApi.search(querytree);
        if (response.getPageInfo() != null && response.getPageInfo().getList() != null) {
            response.getPageInfo().getList().forEach(entity->repositoryService.fill(entity.getStudentSubjectScore()));
        }
        return response;
    }

    public Response<StudentSubjectModifyScore> removeChanges(List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        for (StudentSubjectModifyScore studentSubjectModifyScore: studentSubjectModifyScoreList) {
            studentSubjectModifyScore.setApproverName(sessionBean.getAccount().getRealName());
            studentSubjectModifyScore.setApprTime(new Timestamp(System.currentTimeMillis()));
            studentSubjectModifyScore.setStatus(StudentSubjectModifyScore.STATUS_DELETED);
        }

        return studentSubjectModifyScoreMicroApi.batchUpdate(studentSubjectModifyScoreList);
    }

    public Response<StudentSubjectModifyScore> clearChanges(String examId, String gradeId) {
        StudentSubjectModifyScore condition = new StudentSubjectModifyScore();
        condition.setExamId(examId);
        condition.setGradeId(gradeId);
        return studentSubjectModifyScoreMicroApi.deleteByEntity(condition);
    }

    public Response<StudentSubjectScore> getScoreEnteredList(StudentSubjectFilter filter) {
        return new ApiResponse<>(studentSubjectScoreMicroApi.getScoreEnteredList(filter));
    }

    public Response<StudentSubjectScore> fillUnenteredScores(StudentSubjectFilter filter) {
        List<StudentSubjectScore> studentSubjectScoreList = studentSubjectScoreMicroApi.getScoreUnenteredList(filter).getPageInfo().getList();
        for (StudentSubjectScore studentSubjectScore: studentSubjectScoreList) {
            studentSubjectScore.setStatus(StudentSubjectScore.STATUS_SAVED);
            studentSubjectScore.setScore("0");
        }
        return studentSubjectScoreMicroApi.batchUpdate(studentSubjectScoreList);
    }

    public Response<StudentSubjectScore> getScoreUnenteredList(StudentSubjectFilter filter) {
        return new ApiResponse<>(studentSubjectScoreMicroApi.getScoreUnenteredList(filter));
    }

    public Response<StudentSubjectScore> getExamMissedList(StudentSubjectFilter filter) {
        return new ApiResponse<>(studentSubjectScoreMicroApi.getExamMissedList(filter));
    }

    private int getIndexOfData(List<StudentSubjectScore> datas, String subjectid) {
        for (int i = 0; i < datas.size(); i++) {
            StudentSubjectScore subjectScore = datas.get(i);
            if (StringUtils.equals(subjectScore.getSubjectId(), subjectid)) return i;
        }

        return -1;
    }

    private String getFieldValue(QueryTree queryTree, String key) {
        String value = "";
        QueryCondition queryCondition = queryTree.getQueryCondition(key);
        if (queryCondition != null) {
            value = queryCondition.getFieldValues()[0].toString();
        }

        return value;
    }

    private List<String> getFieldValueList(QueryTree queryTree, String key) {
        List<String> values = null;
        QueryCondition queryCondition = queryTree.getQueryCondition(key);
        if (queryCondition != null) {
            queryCondition.getFieldValues();
            values = CollectionUtils.arrayToList(queryCondition.getFieldValues());
        }

        return values;
    }
}
