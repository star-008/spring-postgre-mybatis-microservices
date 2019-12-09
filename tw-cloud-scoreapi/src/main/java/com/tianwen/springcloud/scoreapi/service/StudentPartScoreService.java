package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.ExamPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.service.util.repo.LocalNameRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by R.JinHyok on 2018.11.19.
 */
@Service
public class StudentPartScoreService extends BaseService {

    public static final String TEMPLATE_FILE_PATH = "templates/student-subject-score.xls";
    public static final String EXPORT_ZIP_FILE_NAME = "{examName}-小题分成绩.zip";
    public static final String EXPORT_EXCEL_FILE_NAME = "{gradeName}-小题分成绩.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_CLASS_TYPE_NAME = new CellPos(0, 5);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);
    protected static final CellPos CELL_POS_SUBJECT_NAME_START = new CellPos(1, 5);
    protected static final int ROW_INDEX_STUDENT_START = 3;

    protected static final int DEFAULT_SCORE_WIDTH = 1200;
    protected static final short COLOR_SUBJECT = IndexedColors.YELLOW.index;
    protected static final short COLOR_QUESTION = IndexedColors.LIGHT_YELLOW.index;
    protected static final short COLOR_TOTAL_SCORE_NAME = IndexedColors.LIGHT_GREEN.index;
    protected static final boolean EXPORT_SCORE = false;

    @Autowired
    private ExamMicroApi examMicroApi;

    @Autowired
    private ExamPartScoreMicroApi examPartScoreMicroApi;

    @Autowired
    private StudentSubjectScoreMicroApi studentSubjectScoreMicroApi;

    @Autowired
    private StudentPartScoreMicroApi studentPartScoreMicroApi;

    public Response<StudentPartScore> importFromExcel(String examId, InputStream inputStream, Filter filter) {

        try {
            SimpleSheet sheet = new SimpleSheet(inputStream);

            String examId0 = sheet.getData(CELL_POS_EXAM_ID);
            if (StringUtils.isEmpty(examId0)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_ID_NOT_FOUND
                );
            }
            if (!StringUtils.equals(examId0, examId)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_ID_NO_MATCH,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_ID_NO_MATCH
                );
            }

            Exam exam = examMicroApi.get(examId).getResponseEntity();
            if (exam == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_NOT_FOUND
                );
            }

            if (!StringUtils.equals(exam.getName(), sheet.getData(CELL_POS_EXAM_NAME))) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH
                );
            }

            String classType = getClassType(sheet.getData(CELL_POS_CLASS_TYPE_NAME));
            if (!StringUtils.equals(classType, ClassInfo.CLASS_TYPE_NORMAL) && !StringUtils.equals(classType, ClassInfo.CLASS_TYPE_SPECIAL)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_CLASS_TYPE_INVALID,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_CLASS_TYPE_INVALID
                );
            }

            LocalNameRepositoryService nameRepositoryService = new LocalNameRepositoryService(examId);

            if (!StringUtils.equals(exam.getName(), sheet.getData(CELL_POS_EXAM_NAME))) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_EXAM_NAME_NO_MATCH,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_EXAM_NAME_NO_MATCH
                );
            }

            DictItem grade = nameRepositoryService.key2entity(sheet.getData(CELL_POS_GRADE_NAME), RepositoryService.EntityType.GRADE);
            if (grade == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_GRADE_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_GRADE_NOT_FOUND
                );
            }

            String gradeId = grade.getDictvalue();

            if (!filter.isForcing()) {
                QueryTree queryTree = new QueryTree().page(1, 1);
                queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
                queryTree.addCondition(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, gradeId));
                queryTree.addCondition(new QueryCondition("pt.classtype", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classType));
                if (StringUtils.equals(classType, ClassInfo.CLASS_TYPE_SPECIAL)) {
                    String subjectName = sheet.getData(CELL_POS_SUBJECT_NAME_START);
                    if (StringUtils.isEmpty(subjectName)) {
                        return new Response<>(
                                IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_EMPTY,
                                IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NAME_EMPTY
                        );
                    }
                    DictItem subject = nameRepositoryService.key2entity(subjectName, RepositoryService.EntityType.SUBJECT);
                    if (subject == null) {
                        return new Response<>(
                                IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_INVALID,
                                IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_SUBJECT_NAME_INVALID
                        );
                    }
                    queryTree.addCondition(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, subject.getDictid()));
                }
                queryTree.addCondition(new QueryCondition("pt.status", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, Arrays.asList(StudentSubjectScore.STATUS_SAVED, StudentSubjectScore.STATUS_PART_SCORE_SAVED)));
                if (studentSubjectScoreMicroApi.search(queryTree).getPageInfo().getSize() > 0) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_ALREADY_IMPORTED,
                            ""
                    );
                }
            }

            nameRepositoryService.setGradeId(gradeId);

            List<String> subjectIdList = new ArrayList<>();
//            List<DictItem> subjectList = new ArrayList<>();
            List<Integer> subjectColStartList = new ArrayList<>();

            Map<String, List<ExamPartScore>> examPartScoreMap = getExamPartScoreMap(examId, gradeId);
            Map<String, List<String>> allQuestionNoMap = new HashMap<>();
            for (Map.Entry<String, List<ExamPartScore>> entry: examPartScoreMap.entrySet()) {
                allQuestionNoMap.put(entry.getKey(), examPartScoreMap.get(entry.getKey()).stream().map(ExamPartScore::getSmallNo).collect(Collectors.toList()));
            }
            Map<String, List<String>> questionNoMap = new HashMap<>();

            List<String> allQuestionNoList;

            String subjectId = null, subjectName;
            DictItem subject;

            for (int i = CELL_POS_SUBJECT_NAME_START.getColIndex(); i <= sheet.getSheet().getRow(CELL_POS_SUBJECT_NAME_START.getRowIndex()).getPhysicalNumberOfCells(); i++) {

                subjectName = sheet.getData(CELL_POS_SUBJECT_NAME_START.getRowIndex(), i);
                String questionNo = sheet.getData(CELL_POS_SUBJECT_NAME_START.getRowIndex() + 1, i);

                if (StringUtils.isEmpty(subjectName)) {
                    if (StringUtils.isEmpty(questionNo)) {
                        break;
                    }
                } else {
                    subject = nameRepositoryService.key2entity(subjectName, RepositoryService.EntityType.SUBJECT);
                    subjectId = (subject == null ? null : subject.getDictvalue());
                    subjectIdList.add(subjectId);
                    subjectColStartList.add(i);

//                    subjectList.add(subject);
                }

                if (subjectId == null) {
                    continue;
                }

                //Check last column of a subject
                try {
                    Float.parseFloat(questionNo);
                } catch (NumberFormatException ex) {
                    continue;
                }

                if ((allQuestionNoList = allQuestionNoMap.get(subjectId)) == null) {
                    continue;
                }

                List<String> questionNoList = questionNoMap.get(subjectId);
                if (questionNoList == null) {
                    questionNoMap.put(subjectId, questionNoList = new ArrayList<>());
                }

                questionNoList.add(allQuestionNoList.indexOf(questionNo) >= 0 ? questionNo : null);
            }

            List<StudentPartScore> studentPartScoreList = new ArrayList<>();
            List<StudentSubjectScore> studentSubjectScoreList = getStudentSubjectList(examId, gradeId);

            int rowCount = sheet.getRowCount();
            int subjectCount = subjectIdList.size();

            String classId;
            String studentNo;
            String studentName;

            List<String> questionNoList;
            int studentSubjectScoreIndex;

            List<StudentSubjectScore> studentSubjectScoreUpdateList = new ArrayList<>();
            for (int rowIndex = ROW_INDEX_STUDENT_START; rowIndex < rowCount; rowIndex++) {
                if (StringUtils.isEmpty(classId = sheet.getData(rowIndex, 0))) {
                    continue;
                }
                if (StringUtils.isEmpty(studentNo = sheet.getData(rowIndex, 2))) {
                    continue;
                }
                studentName = sheet.getData(rowIndex, 3);

                for (int subjectIndex = 0; subjectIndex < subjectCount; subjectIndex++) {
                    if (StringUtils.isEmpty(subjectId = subjectIdList.get(subjectIndex))) {
                        continue;
                    }

                    StudentSubjectScore studentSubjectScore = new StudentSubjectScore(
                            exam.getId(),
                            gradeId,
                            classType,
                            subjectId,
                            classId,
                            studentNo,
                            studentName
                    );

                    if ((studentSubjectScoreIndex = studentSubjectScoreList.indexOf(studentSubjectScore)) <  0) {
                        continue;

//                        subject = subjectList.get(subjectIndex);
//                        return new Response<>(
//                                IErrorMessageConstants.ERR_CODE_STUDENT_SUBJECT_SCORE_STUDENT_NOT_FOUND,
//                                IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_SUBJECT_SCORE_STUDENT_NOT_FOUND
//                                        .replace("{studentName}", studentName).replace("{subjectName}", subject != null ? subject.getDictname() : "")
//                        );
                    }
                    studentSubjectScore = studentSubjectScoreList.remove(studentSubjectScoreIndex);

                    List<ExamPartScore> examPartScoreList = examPartScoreMap.get(subjectId);

                    int questionNoColIndex = subjectColStartList.get(subjectIndex);
                    questionNoList = questionNoMap.get(subjectId);

                    int colIndexTotal = questionNoColIndex + (questionNoList != null ? questionNoList.size() : 0);
                    String totalScore = sheet.getData(rowIndex, colIndexTotal);

                    if (StringUtils.equals(totalScore, ICommonConstants.EXAM_MISSED)) {
                        totalScore = "-1";
                    }

                    if (NumberUtils.toFloat(totalScore) != -1 && questionNoList != null) {

                        float scoreSum = 0;
                        for (String questionNo: questionNoList) {
                            if (questionNo != null) {

                                String score = sheet.getData(rowIndex, questionNoColIndex);
                                if (!(NumberUtils.isNumber(score)) || NumberUtils.toFloat(score) < 0) {
                                    return new Response<>(
                                            IErrorMessageConstants.ERR_CODE_STUDENT_PART_SCORE_SCORE_INVALID,
                                            IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_PART_SCORE_SCORE_INVALID
                                                    .replace("{cellName}", sheet.getCellName(rowIndex, questionNoColIndex))
                                    );
                                }

                                ExamPartScore examPartScore = new ExamPartScore();
                                examPartScore.setExamId(examId);
                                examPartScore.setGradeId(gradeId);
                                examPartScore.setSubjectId(subjectId);
                                examPartScore.setSmallNo(questionNo);

                                examPartScore = examPartScoreList.get(examPartScoreList.indexOf(examPartScore));

                                if (NumberUtils.toFloat(score) > NumberUtils.toFloat(examPartScore.getScore())) {
                                    return new Response<>(
                                            IErrorMessageConstants.ERR_CODE_STUDENT_PART_SCORE_SCORE_INVALID1,
                                            IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_PART_SCORE_SCORE_INVALID1
                                                    .replace("{cellName}", sheet.getCellName(rowIndex, questionNoColIndex))
                                    );
                                }

                                StudentPartScore studentPartScore = new StudentPartScore(
                                        examId,
                                        gradeId,
                                        classType,
                                        studentSubjectScore.getClassId(),
                                        subjectId,
                                        studentSubjectScore.getStudentId(),
                                        studentSubjectScore.getStudentName(),
                                        studentSubjectScore.getStudentNo(),
                                        examPartScore.getBigNo(),
                                        examPartScore.getQuestionNo(),
                                        examPartScore.getSmallNo()
                                );

                                studentPartScore.setStudentSubjectId(studentSubjectScore.getId());
                                studentPartScore.setQuestionCategoryId(examPartScore.getQuestionCategoryId());
                                studentPartScore.setQuestionTypeId(examPartScore.getQuestionTypeId());
                                studentPartScore.setScore(score);

                                studentPartScoreList.add(studentPartScore);

                                scoreSum += NumberUtils.toFloat(score);
                            }

                            questionNoColIndex ++;
                        }

                        totalScore = String.valueOf(scoreSum);
                    } else if (!StringUtils.isEmpty(totalScore) && !NumberUtils.isNumber(totalScore)) {
                        return new Response<>(
                                IErrorMessageConstants.ERR_CODE_STUDENT_PART_SCORE_SCORE_INVALID,
                                IErrorMessageConstants.ERR_MESSAGE_CODE_STUDENT_PART_SCORE_SCORE_INVALID
                                        .replace("{cellName}", sheet.getCellName(rowIndex, colIndexTotal))
                        );
                    }

                    studentSubjectScore.setScore(totalScore == null ? "" : totalScore);
                    studentSubjectScore.setStatus(!CollectionUtils.isEmpty(questionNoList) ? StudentSubjectScore.STATUS_PART_SCORE_SAVED : StudentSubjectScore.STATUS_SAVED);
                    studentSubjectScoreUpdateList.add(studentSubjectScore);
                }
            }

            if (studentSubjectScoreUpdateList.size() > 0) {
                studentSubjectScoreMicroApi.batchUpdate(studentSubjectScoreUpdateList);
            }

            if (studentPartScoreList.size() > 0) {
                studentPartScoreMicroApi.batchAdd(studentPartScoreList);

                startExamEditing(exam);
            }

            return new Response<>(studentSubjectScoreUpdateList.size() > 0 ? new StudentPartScore(studentSubjectScoreUpdateList.get(0)) : null);
        } catch (IOException | InvalidFormatException e) {
            return new Response<>(
                    "-1",
                    e.getMessage()
            );
        }
    }

    public void exportToExcel(Exam exam, OutputStream outputStream) {

        String examId = exam.getId();

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(outputStream, new CRC32()));
            for (DictItem grade: getExamGradeList(examId)) {
                String gradeId = grade.getDictvalue();

                List<DictItem> subjectList = getExamSubjectList(examId, gradeId, ClassInfo.CLASS_TYPE_NORMAL);
                List<String> subjectIdList = subjectList.stream().map(DictItem::getDictvalue).collect(Collectors.toList());
                Map<String, List<StudentPartScore>> studentPartScoreMap = getStudentPartScoreMap(examId, gradeId, ClassInfo.CLASS_TYPE_NORMAL, subjectIdList);
                Map<String, List<ExamPartScore>> examPartScoreMap = getExamPartScoreMap(examId, gradeId, ClassInfo.CLASS_TYPE_NORMAL, subjectIdList);
                List<Integer> subjectColOffsetList = new ArrayList<>();
                int subjectColOffset = 0;
                for (DictItem subject: subjectList) {
                    List<ExamPartScore> examPartScoreList = examPartScoreMap.get(subject.getDictvalue());
                    int questionCount = 1; //1 is for total score column.
                    if (examPartScoreList != null) {
                        questionCount += examPartScoreList.size();
                    }
                    subjectColOffsetList.add(subjectColOffset);
                    subjectColOffset += questionCount; //Add offsets for next subject.
                }
                exportToExcelByClassType(exam, grade, ClassInfo.CLASS_TYPE_NORMAL, subjectList, subjectIdList, studentPartScoreMap, examPartScoreMap, subjectColOffsetList, zipOutputStream);

                subjectList = getExamSubjectList(examId, gradeId, ClassInfo.CLASS_TYPE_SPECIAL);
                for (DictItem subject: subjectList) {
                    subjectIdList = Collections.singletonList(subject).stream().map(DictItem::getDictvalue).collect(Collectors.toList());
                    studentPartScoreMap = getStudentPartScoreMap(examId, gradeId, ClassInfo.CLASS_TYPE_SPECIAL, subjectIdList);
                    examPartScoreMap = getExamPartScoreMap(examId, gradeId, ClassInfo.CLASS_TYPE_SPECIAL, subjectIdList);
                    subjectColOffsetList = new ArrayList<>();
                    subjectColOffsetList.add(0);
                    exportToExcelByClassType(exam, grade, ClassInfo.CLASS_TYPE_SPECIAL, Collections.singletonList(subject), subjectIdList, studentPartScoreMap, examPartScoreMap, subjectColOffsetList, zipOutputStream);
                }
            }

            zipOutputStream.flush();
            zipOutputStream.close();

        } catch (IOException | InvalidFormatException e) {

        }
    }

    public void exportToExcelByClassType(Exam exam, DictItem grade, String classType, List<DictItem> subjectList, List<String> subjectIdList, Map<String, List<StudentPartScore>> studentPartScoreMap, Map<String, List<ExamPartScore>> examPartScoreMap, List<Integer> subjectColOffsetList, ZipOutputStream zipOutputStream) throws IOException, InvalidFormatException {
        String examId = exam.getId();
        String gradeId = grade.getDictvalue();

        SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

        //Save exam and grade info into sheet.
        sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
        sheet.setData(CELL_POS_EXAM_ID, examId);
        sheet.setData(CELL_POS_CLASS_TYPE_NAME, getClassTypeName(classType));
        sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());

        //Save subject info into sheet.
        int subjectColOffset = 0;
        for (DictItem subject: subjectList) {
            CellPos subjectNamePos = new CellPos(CELL_POS_SUBJECT_NAME_START.getRowIndex(), CELL_POS_SUBJECT_NAME_START.getColIndex() + subjectColOffset);
            sheet.setData(subjectNamePos, subject.getDictname());

            CellPos questionNoPos = new CellPos(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex());

            List<ExamPartScore> examPartScoreList = examPartScoreMap.get(subject.getDictvalue());
            if (examPartScoreList == null) {
                examPartScoreList = new ArrayList<>(); //For easy handling below
            }

            //Setting question numbers into sheet.
            for (ExamPartScore examPartScore: examPartScoreList) {
                sheet.setData(questionNoPos, examPartScore.getSmallNo())
                        .setColumnWidth(questionNoPos.getColIndex(), DEFAULT_SCORE_WIDTH)
                        .setCellBackgroundColor(questionNoPos, COLOR_QUESTION);
                questionNoPos.increaseColumn();
            }
            sheet.setData(questionNoPos, ICommonConstants.TOTAL_SCORE_NAME)
                    .setColumnWidth(questionNoPos.getColIndex(), DEFAULT_SCORE_WIDTH)
                    .setCellBackgroundColor(questionNoPos, COLOR_TOTAL_SCORE_NAME);

            int questionCount = examPartScoreList.size() + 1; //1 is for total score column.
            sheet.setCellSpan(subjectNamePos, questionCount) //Merge subject cells.
                    .setCellAlignCenter(subjectNamePos)
                    .setCellBackgroundColor(subjectNamePos, COLOR_SUBJECT);

            subjectColOffset += questionCount; //Add offsets for next subject.
        }

        int rowStudent = ROW_INDEX_STUDENT_START - 1;
        int subjectIdIndex, questionIndex;

        String studentId = null;
        for (StudentSubjectScore studentSubjectScore: getStudentSubjectList(examId, gradeId, classType, subjectIdList)) {
            if (!StringUtils.equals(studentId, studentSubjectScore.getStudentId())) {
                studentId = studentSubjectScore.getStudentId();
                rowStudent ++;

                setStudentData(sheet, rowStudent, studentSubjectScore);
            }

            if (!EXPORT_SCORE) {
                continue;
            }

            String subjectId = studentSubjectScore.getSubjectId();
            if ((subjectIdIndex = subjectIdList.indexOf(subjectId)) >= 0) {

                //Gets offset of subject column.
                subjectColOffset = subjectColOffsetList.get(subjectIdIndex);
                CellPos scorePos = new CellPos(rowStudent, CELL_POS_SUBJECT_NAME_START.getColIndex() + subjectColOffset);

                List<ExamPartScore> examPartScoreList = examPartScoreMap.get(subjectId);
                if (examPartScoreList == null) {
                    examPartScoreList = new ArrayList<>();
                }
                //Sets total score of the subject into sheet.
                sheet.setData(scorePos.getRowIndex(), scorePos.getColIndex() + examPartScoreList.size(), studentSubjectScore.getScore());

                List<StudentPartScore> studentPartScoreList = studentPartScoreMap.get(subjectId);
                if (studentPartScoreList != null) {
                    for (ExamPartScore examPartScore: examPartScoreList) {
                        if ((questionIndex = studentPartScoreList.indexOf(new StudentPartScore(
                                examId,
                                gradeId,
                                classType,
                                subjectId,
                                studentId,
                                examPartScore.getSmallNo()
                        ))) >= 0) {
                            sheet.setData(scorePos, studentPartScoreList.get(questionIndex).getScore());
                        }
                        scorePos.increaseColumn();
                    }
                }
            }
        }

        ZipEntry entry = new ZipEntry(EXPORT_EXCEL_FILE_NAME.replace("{gradeName}", grade.getDictname() + (StringUtils.equals(classType, ClassInfo.CLASS_TYPE_SPECIAL) ? "-" + getClassTypeName(classType) + "-" + subjectList.get(0).getDictname() : "")));
        zipOutputStream.putNextEntry(entry);

        sheet.write(zipOutputStream);

        zipOutputStream.flush();
        zipOutputStream.closeEntry();
    }

    private String getClassTypeName(String classType) {
        return ICommonConstants.getClassTypeName(NumberUtils.toInt(classType) - 1);
    }

    private String getClassType(String classTypeName) {
        return ICommonConstants.getClassType(classTypeName);
    }

    public List<StudentSubjectScore> getStudentSubjectList(String examId, String gradeId) {
        return getStudentSubjectList(examId, gradeId, null, null);
    }

    public List<StudentSubjectScore> getStudentSubjectList(String examId, String gradeId, String classType, List<String> subjectIdList) {
        QueryTree queryTree = new QueryTree().page(Pagination.QUERY_ALL, 0);
        queryTree.getPagination().setNumPerPage(0);
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        queryTree.addCondition(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, gradeId));
        if (!StringUtils.isEmpty(classType)) {
            queryTree.addCondition(new QueryCondition("pt.classtype", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classType));
        }
        if (!CollectionUtils.isEmpty(subjectIdList)) {
            queryTree.addCondition(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, subjectIdList));
        }

        return studentSubjectScoreMicroApi.search(queryTree).getPageInfo().getList();
    }

    public void setStudentData(SimpleSheet sheet, int rowStudent, StudentSubjectScore studentSubjectScore) {
        sheet.setData(rowStudent, 0, studentSubjectScore.getClassId());
        sheet.setData(rowStudent, 2, studentSubjectScore.getStudentNo());
        sheet.setData(rowStudent, 3, studentSubjectScore.getStudentName());
        sheet.setData(rowStudent, 4, studentSubjectScore.getSexName());

        ClassInfo classInfo;
        if ((classInfo = repositoryService.key2entity(studentSubjectScore.getClassId(), RepositoryService.EntityType.CLASS)) != null) {
            sheet.setData(rowStudent, 1, classInfo.getFullName());
        }
    }

    public Map<String, List<ExamPartScore>> getExamPartScoreMap(String examId, String gradeId) {
        return getExamPartScoreMap(examId, gradeId, null, null);
    }

    public Map<String, List<ExamPartScore>> getExamPartScoreMap(String examId, String gradeId, String classType, List<String> subjectIdList) {
        Map<String, List<ExamPartScore>> examPartScoreMap = new HashMap<>();
        QueryTree queryTree = new QueryTree().page(Pagination.QUERY_ALL, 0);
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        queryTree.addCondition(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, gradeId));
        if (!StringUtils.isEmpty(classType)) {
            queryTree.addCondition(new QueryCondition("pt.classtype", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classType));
        }
        if (!CollectionUtils.isEmpty(subjectIdList)) {
            queryTree.addCondition(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, subjectIdList));
        }

        List<ExamPartScore> examPartScoreList = examPartScoreMicroApi.search(queryTree).getPageInfo().getList();
        for (ExamPartScore examPartScore: examPartScoreList) {
            List<ExamPartScore> list = examPartScoreMap.get(examPartScore.getSubjectId());
            if (list == null) {
                list = new ArrayList<>();
                examPartScoreMap.put(examPartScore.getSubjectId(), list);
            }
            list.add(examPartScore);
        }

        return examPartScoreMap;
    }

    public Map<String, List<StudentPartScore>> getStudentPartScoreMap(String examId, String gradeId, String classType, List<String> subjectIdList) {
        Map<String, List<StudentPartScore>> studentPartScoreMap = new HashMap<>();
        QueryTree queryTree = new QueryTree().page(Pagination.QUERY_ALL, 0);
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        queryTree.addCondition(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, gradeId));
        if (!StringUtils.isEmpty(classType)) {
            queryTree.addCondition(new QueryCondition("pt.classtype", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classType));
        }
        if (!CollectionUtils.isEmpty(subjectIdList)) {
            queryTree.addCondition(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, subjectIdList));
        }

        List<StudentPartScore> studentPartScoreList = studentPartScoreMicroApi.search(queryTree).getPageInfo().getList();
        for (StudentPartScore studentPartScore: studentPartScoreList) {
            List<StudentPartScore> list = studentPartScoreMap.get(studentPartScore.getSubjectId());
            if (list == null) {
                list = new ArrayList<>();
                studentPartScoreMap.put(studentPartScore.getSubjectId(), list);
            }
            list.add(studentPartScore);
        }

        return studentPartScoreMap;
    }

    public Response<StudentPartScore> search(QueryTree querytree) {
        //return new ApiResponse<>(studentPartScoreMicroApi.search(querytree));
        Response<StudentPartScore> response = studentPartScoreMicroApi.search(querytree);
        List<StudentPartScore> list = response.getPageInfo().getList();

        for (StudentPartScore studentPartScore: list) {
            //repositoryService.fill(studentPartScore);

            studentPartScore.setGrade(repositoryService.key2entity(studentPartScore.getGradeId(), RepositoryService.EntityType.GRADE));
            studentPartScore.setSubject(repositoryService.key2entity(studentPartScore.getSubjectId(), RepositoryService.EntityType.SUBJECT));
            studentPartScore.setClassInfo(repositoryService.key2entity(studentPartScore.getClassId(), RepositoryService.EntityType.CLASS));
            studentPartScore.setQuestionCategory(repositoryService.key2entity(studentPartScore.getQuestionCategoryId(), RepositoryService.EntityType.QUESTION_CATEGORY));
            studentPartScore.setQuestionType(repositoryService.key2entity(studentPartScore.getQuestionTypeId(), RepositoryService.EntityType.QUESTION_TYPE));

        }

        return response;
    }
}
