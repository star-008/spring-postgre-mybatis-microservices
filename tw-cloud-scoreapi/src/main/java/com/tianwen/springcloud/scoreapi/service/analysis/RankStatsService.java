package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.RankStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.EqualityStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.PassZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.Rank;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class RankStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/rank-stats.xls";
    public static final String COMPARE_TEMPLATE_FILE_PATH = "templates/rank-stats-compare.xls";
    public static final String EXPORT_FILE_NAME = "{gradeName}--{examName}--成绩统计排名.xls";
    public static final String EXPORT_FOR_COMPARE_FILE_NAME = "{gradeName}--{examName1}-{examName2}--成绩统计排名.xls";

    protected static final CellPos CELL_POS_SUBJECT_NAME = new CellPos(1, 5);
    protected static final String SUBJECT_NAME_SELECTED = "所选科目";
    protected final String[] SUBJECT_DATA_NAMES = new String[] {"班排名", "级排名", "等级"};

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_EXAM_NAME2 = new CellPos(0, 5);
    protected static final CellPos CELL_POS_EXAM_ID2 = new CellPos(0, 7);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int ROW_INDEX_STUDENT_START = 3;
    protected static final int ROW_INDEX_STUDENT_START2 = 4;

    @Autowired
    private RankStatsMicroApi rankStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<RankStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(RankStats.class, rankStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        return new ExcelExporter(request).run();
    }

    public String exportToExcelForCompare(Request request) {
        List<String> examIdList = request.getFilter().getExamIdList();
        if (examIdList.size() < 2) return null;
        Exam exam1 = examMicroApi.get(examIdList.get(0)).getResponseEntity();
        Exam exam2 = examMicroApi.get(examIdList.get(1)).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<RankStats> rankStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(COMPARE_TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam1.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, exam1.getId())
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_EXAM_NAME2, exam2.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME2);
            sheet.setData(CELL_POS_EXAM_ID2, exam2.getId())
                    .setCellAlignCenter(CELL_POS_EXAM_ID2);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 3), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 6), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 9), exam1.getName());

            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 4), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 7), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_STUDENT_START2 - 1, 10), exam2.getName());

            int rowStart = ROW_INDEX_STUDENT_START2;

            while (rankStatsList.size() > 0) {
                RankStats rankStats = rankStatsList.get(0);
                List<RankStats> sameones = rankStatsList.stream().filter(rankStats1 -> rankStats1.getStudentId().equals(rankStats.getStudentId())).collect(Collectors.toList());
                if (sameones.size() == 2) {
                    renderForSubject(sheet, rowStart++, sameones, exam1.getId(), exam2.getId());
                }
                rankStatsList.removeAll(sameones);
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FOR_COMPARE_FILE_NAME.replace("{examName1}", exam1.getName()).replace("{examName2}", exam2.getName()).replace("{gradeName}", grade.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    private int renderForSubject(SimpleSheet sheet, int rowStart, List<RankStats> stringListMap, String exam1, String exam2) {
        String score1 = "0", score2 = "0";
        String realscore1 = "0", realscore2 = "0";
        String classOrder1 = "0", classOrder2 = "0";
        String gradeOrder1 = "0", gradeOrder2 = "0";

        for (RankStats rankStat: stringListMap) {
            if (rankStat.getStuRankInGrade().equals("0")) rankStat.setStuRankInGrade("");
            if (rankStat.getStuRankInClass().equals("0")) rankStat.setStuRankInClass("");

            if (rankStat.getExamId().equals(exam1)) {
                score1 = rankStat.getScore();
                realscore1 = rankStat.getRealscore();
                classOrder1 = rankStat.getStuRankInClass();
                gradeOrder1 = rankStat.getStuRankInGrade();
            } else if (rankStat.getExamId().equals(exam2)) {
                score2 = rankStat.getScore();
                realscore2 = rankStat.getRealscore();
                classOrder2 = rankStat.getStuRankInClass();
                gradeOrder2 = rankStat.getStuRankInGrade();
            }
        }

        sheet.setData(rowStart, 0, stringListMap.get(0).getStudentNo());
        sheet.setData(rowStart, 1, stringListMap.get(0).getStudentName());
        sheet.setData(rowStart, 2, stringListMap.get(0).getClassInfo().getName());

        if (realscore1.equals("-1")) {
            sheet.setData(rowStart, 3, "缺考");
        } else {
            sheet.setData(rowStart, 3, score1);
        }
        if (realscore2.equals("-1")) {
            sheet.setData(rowStart, 4, "缺考");
        } else {
            sheet.setData(rowStart, 4, score2);
        }
        if (classOrder1.equals("-1") || classOrder2.equals("-1")) {
            sheet.setData(rowStart, 5, "");
        } else {
            sheet.setData(rowStart, 5, String.valueOf(Float.valueOf(score1) - Float.valueOf(score2)));
        }

        sheet.setData(rowStart, 6, classOrder1);
        sheet.setData(rowStart, 7, classOrder2);
        if (classOrder1.equals("") || classOrder2.equals("")) {
            sheet.setData(rowStart, 8, "");
        } else {
            sheet.setData(rowStart, 8, String.valueOf(Float.valueOf(classOrder1) - Float.valueOf(classOrder2)));
        }

        sheet.setData(rowStart, 9, gradeOrder1);
        sheet.setData(rowStart, 10, gradeOrder2);
        if (gradeOrder1.equals("") || gradeOrder2.equals("")) {
            sheet.setData(rowStart, 11, "");
        } else {
            sheet.setData(rowStart, 11, String.valueOf(Float.valueOf(gradeOrder1) - Float.valueOf(gradeOrder2)));
        }

        return 1;
    }

    class ExcelExporter {

        private Request request;

        private Exam exam;
        private DictItem grade;

        private SimpleSheet sheet;

        private int spanSubject = 1;
        private List<String> subjectIdList = new ArrayList<>();
        private List<DictItem> subjectList;
        private List<Integer> visibleSubjectDataIndexList = new ArrayList<>();

        private List<String> studentIdList = new ArrayList<>();
        private Map<String, List<RankStats>> rankStatsMap = new HashMap<>();

        boolean hasAllTotal = false;

        ExcelExporter(Request request) {
            this.request = request;
        }

        String run() {
            return writeToExcel();
        }

        String writeToExcel() {

            getReady();

            try {
                sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

                writeHeader();

                studentIdList.forEach(this::writeStudentStats);

                ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(EXPORT_FILE_NAME
                        .replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
                writeSheetData(sheet, exportedFile);

                return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
            } catch (IOException | InvalidFormatException e) {

            }

            return null;
        }

        private void getReady() {
            exam = examMicroApi.get(request.getFilter().getExamId()).getResponseEntity();
            grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

            spanSubject = 1;
            int showMode = request.getFilter().getShowMode() == null ? 0 : request.getFilter().getShowMode();

            for (int i = 0; i < SUBJECT_DATA_NAMES.length; i++) {
                if (((int)Math.pow(2, i) & showMode) > 0) {
                    visibleSubjectDataIndexList.add(i);
                    spanSubject ++;
                }
            }

            subjectIdList = request.getFilter().getSubjectIdList();
            subjectList = repositoryService.key2entity(subjectIdList, RepositoryService.EntityType.SUBJECT, false);
            hasAllTotal = subjectIdList.contains("ALL_TOTAL");

            List<RankStats> rankStatsList = getList(request).getPageInfo().getList();

            List<RankStats> studentRankStatsList;
            for (RankStats rankStats : rankStatsList) {

                String studentId = rankStats.getStudentId();
                if (studentIdList.indexOf(studentId) < 0) {
                    studentIdList.add(studentId);
                }

                studentRankStatsList = rankStatsMap.get(studentId);
                if (studentRankStatsList == null) {
                    studentRankStatsList = new ArrayList<>();
                    rankStatsMap.put(studentId, studentRankStatsList);
                }
                studentRankStatsList.add(rankStats);
            }
        }

        private void writeHeader() {

            sheet.setData(CELL_POS_EXAM_ID, request.getFilter().getExamId());
            sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());

            CellPos subjectNamePos = new CellPos(CELL_POS_SUBJECT_NAME);
            int subjectIndex = 0;
            for (String subjectId: subjectIdList) {
                DictItem subject = subjectList.get(subjectIndex);
                //  Adding subjects names

                sheet.setData(subjectNamePos, subjectId.equals("ALL_TOTAL") ? spanSubject + 1 : spanSubject,
                        subjectId.equals("ALL_TOTAL") ? ICommonConstants.TOTAL_SCORE : (subjectId.equals(ICommonConstants.TOTAL_SUBJECT_ID) ? ICommonConstants.TOTAL_SCORE_NORMAL : getSubjectName(subject)))
                        .setCellAlignCenter(subjectNamePos);

                sheet.setData(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex(), subjectId.contains(ICommonConstants.TOTAL_SUBJECT_ID) ? ICommonConstants.TOTAL_SCORE_NAME : ICommonConstants.SCORE_NAME)
                        .setCellAlignCenter(new CellPos(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex()));
                subjectNamePos.increaseColumn();

                for (Integer visibleSubjectDataIndex: visibleSubjectDataIndexList) {
                    sheet.setData(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex(), SUBJECT_DATA_NAMES[visibleSubjectDataIndex])
                            .setCellAlignCenter(new CellPos(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex()));
                    subjectNamePos.increaseColumn();
                }

                subjectIndex ++;
            }

            if (hasAllTotal) {
                sheet.setData(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex(), SUBJECT_NAME_SELECTED)
                        .setCellAlignCenter(new CellPos(subjectNamePos.getRowIndex() + 1, subjectNamePos.getColIndex()));
            }
        }

        private void writeStudentStats(String studentId) {

            int rowStudent = ROW_INDEX_STUDENT_START + studentIdList.indexOf(studentId);

            List<RankStats> rankStatsList = rankStatsMap.get(studentId);

            int subjectIndex;
            int colSubjectScore;
            String[] subjectDataList;
            String subjectPrefixes = "";

            writeStudentInfo(sheet, rowStudent, rankStatsList.get(0));

            StringBuilder builder = new StringBuilder();
            for (RankStats rankStats: rankStatsList) {

                if ((subjectIndex = subjectIdList.indexOf(rankStats.getSubjectId())) < 0) {
                    continue;
                }

                if (rankStats.getRealscore().equals("-1")) rankStats.setScore("缺考");
                if (rankStats.getStuRankInGrade().equals("0")) rankStats.setStuRankInGrade("");
                if (rankStats.getStuRankInClass().equals("0")) rankStats.setStuRankInClass("");

                colSubjectScore = CELL_POS_SUBJECT_NAME.getColIndex() + subjectIndex * spanSubject;

                sheet.setData(rowStudent, colSubjectScore, rankStats.getScore());
                colSubjectScore ++;

                subjectDataList = new String[] {
                        rankStats.getStuRankInClass(),
                        rankStats.getStuRankInGrade(),
                        rankStats.getDegree()
                };

                for (Integer visibleSubjectDataIndex: visibleSubjectDataIndexList) {
                    sheet.setData(rowStudent, colSubjectScore ++, subjectDataList[visibleSubjectDataIndex]);
                }

                if (StringUtils.equals(rankStats.getClassType(), ClassInfo.CLASS_TYPE_SPECIAL)) {
                    builder.append(getSubjectPrefix(subjectList.get(subjectIndex)));
                }
            }

            subjectPrefixes = builder.toString();
            if (hasAllTotal) {
                sheet.setData(rowStudent, CELL_POS_SUBJECT_NAME.getColIndex() + subjectIdList.size() * spanSubject, subjectPrefixes);
            }
        }

        private String getSubjectPrefix(DictItem subject) {
            String subjectName = getSubjectName(subject);
            return StringUtils.isEmpty(subjectName) ? "" : subjectName.substring(0, 1);
        }

        private void writeStudentInfo(SimpleSheet sheet, int rowStudent, RankStats rankStats) {
            sheet.setData(rowStudent, 0, rankStats.getStudentNo());
            sheet.setData(rowStudent, 1, rankStats.getStudentExamNo());
            sheet.setData(rowStudent, 2, rankStats.getStudentName());
            sheet.setData(rowStudent, 3, rankStats.getSexName());
            sheet.setData(rowStudent, 4, getClassFullName(repositoryService.key2entity(rankStats.getClassId(), RepositoryService.EntityType.CLASS)));
        }

        private String getClassFullName(ClassInfo classInfo) {
            if  (classInfo == null) {
                return null;
            }

            int index = classInfo.getName().indexOf("(");
            if (index >= 0) {
                classInfo.setName(classInfo.getName().substring(0, index));
            }

            return RankStatsService.this.getClassFullName(classInfo);
        }
    }
}
