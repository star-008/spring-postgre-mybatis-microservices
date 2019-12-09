package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.ClassTopCountStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.ClassTopCountStats;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class ClassTopCountStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/class-top-count-stats.xls";
    public static final String COMPARE_TEMPLATE_FILE_PATH = "templates/class-top-count-stats-compare.xls";
    public static final String EXPORT_FILE_NAME = "{gradeName}{examName}--班级科目年级前若干名统计.xls";
    public static final String EXPORT_FOR_COMPARE_FILE_NAME = "{gradeName}-{examName1}-{examName2}--班级科目年级前若干名统计.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_EXAM_NAME2 = new CellPos(0, 5);
    protected static final CellPos CELL_POS_EXAM_ID2 = new CellPos(0, 7);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final CellPos CELL_POS_SUBJECT_NAME = new CellPos(2, 2);

    protected static final int ROW_INDEX_CLASS_START = 3;

    @Autowired
    private ClassTopCountStatsMicroApi classTopCountStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<ClassTopCountStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(ClassTopCountStats.class, classTopCountStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {

        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<ClassTopCountStats> classTopCountStatsList = getList(request).getPageInfo().getList();

        List<String> subjectIdList = request.getFilter().getSubjectIdList();
        List<DictItem> subjectList = repositoryService.key2entity(subjectIdList, RepositoryService.EntityType.SUBJECT);

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            // Adding subject field name and total field name
            CellPos subjectNamePos = new CellPos(CELL_POS_SUBJECT_NAME);
            for (DictItem subject: subjectList) {
                sheet.setData(subjectNamePos, subject.getDictname());
                subjectNamePos.increaseColumn();
            }
            sheet.setData(subjectNamePos, ICommonConstants.TOTAL);

            int rowClassStart = ROW_INDEX_CLASS_START;
            int rowClass = rowClassStart;
            String classId = null;
            int colSubject;

            for (ClassTopCountStats classTopCountStats : classTopCountStatsList) {
                if (!StringUtils.equals(classId, classTopCountStats.getClassId())) {
                    classId = classTopCountStats.getClassId();

                    rowClassStart = rowClass;

                    sheet.setData(rowClass, 0, getClassFullName(classTopCountStats.getClassInfo()));

                    sheet.setData(rowClass ++, 1, ICommonConstants.CLASS_AVG_SCORE);
                    sheet.setData(rowClass ++, 1, ICommonConstants.CLASS_AVG_RANK_IN_GRADE);
                    sheet.setData(rowClass ++, 1, ICommonConstants.GRADE_AVG_SCORE);

                    for (ClassTopCountStats.Count count: classTopCountStats.getCountList()) {
                        sheet.setData(rowClass ++, 1,
                                ClassTopCountStats.COUNT_LEVEL_TOP.equals(count.getType()) ?
                                        ICommonConstants.GRADE_TOP + count.getLevel() + ICommonConstants.GRADE_COUNT.replace("{level}", count.getLevel()) :
                                        ICommonConstants.GRADE_LAST + count.getLevel() + ICommonConstants.GRADE_COUNT.replace("{level}", count.getLevel())
                        );
                    }

                    sheet.mergeCells(rowClassStart, rowClass - rowClassStart, 0, 1);
                    sheet.setCellVerticalAlignCenter(new CellPos(rowClassStart, 0));
                }

                rowClass = rowClassStart;

                colSubject = CELL_POS_SUBJECT_NAME.getColIndex() + subjectIdList.indexOf(classTopCountStats.getSubjectId());
                sheet.setData(rowClass ++,  colSubject, classTopCountStats.getScore());
                sheet.setData(rowClass ++, colSubject, classTopCountStats.getRank());
                sheet.setData(rowClass ++, colSubject, classTopCountStats.getAvgScore());

                for (ClassTopCountStats.Count count: classTopCountStats.getCountList()) {
                    sheet.setData(rowClass ++, colSubject, count.getValue());
                }
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME.replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    public String exportToExcelForCompare(Request request) {

        List<String> examIdList = request.getFilter().getExamIdList();
        if (examIdList.size() < 2) return null;
        Exam exam1 = examMicroApi.get(examIdList.get(0)).getResponseEntity();
        Exam exam2 = examMicroApi.get(examIdList.get(1)).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<ClassTopCountStats> classTopCountStatsList = getList(request).getPageInfo().getList();

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

            sheet.setData(new CellPos(ROW_INDEX_CLASS_START - 1, 6), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START - 1, 7), exam2.getName());

            int rowClassStart = ROW_INDEX_CLASS_START;

            Map<String, Map<String, List<ClassTopCountStats>>> statsBySubjectAndClass = classTopCountStatsList.stream().collect(Collectors.groupingBy(ClassTopCountStats::getSubjectId, Collectors.groupingBy(ClassTopCountStats::getClassId)));

            List<String> keyList = new ArrayList<>();
            for (String key : statsBySubjectAndClass.keySet()) {
                keyList.add(key);
            }
            keyList.sort((o1, o2) -> o1.compareTo(o2));
            for (String key : keyList) {
                Map<String, List<ClassTopCountStats>> stringListMap = statsBySubjectAndClass.get(key);
                int rows = renderForSubject(sheet, rowClassStart, stringListMap, exam1.getId(), exam2.getId());
                sheet.mergeCells(rowClassStart, rows, 0, 1);
                sheet.setCellVerticalAlign(new CellPos(rowClassStart, 0), CellStyle.VERTICAL_CENTER);
                rowClassStart += rows;
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FOR_COMPARE_FILE_NAME.replace("{gradeName}", grade.getDictname()).replace("{examName1}", exam1.getName()).replace("{examName2}", exam2.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    private int renderForSubject(SimpleSheet sheet, int rowStart, Map<String, List<ClassTopCountStats>> stringListMap, String exam1, String exam2) {
        int rows = 0;
        List<String> keyList = new ArrayList<>();
        for (String key : stringListMap.keySet()) {
            keyList.add(key);
        }
        keyList.sort((o1, o2) -> o1.equals(ICommonConstants.TOTAL_CLASS_ID)?1:(o1.compareTo(o2)));

        for (String key : keyList) {
            List<ClassTopCountStats> statForOneClass = stringListMap.get(key);
            if (statForOneClass.size() == 0) continue;
            sheet.setData(rowStart + rows, 0, StringUtils.equals(statForOneClass.get(0).getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(statForOneClass.get(0).getSubject()));
            sheet.setData(rowStart + rows, 1, StringUtils.equals(statForOneClass.get(0).getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(statForOneClass.get(0).getClassInfo()));
            sheet.setData(rowStart + rows, 2, statForOneClass.get(0).getTotalCount());
            sheet.setData(rowStart + rows, 3, statForOneClass.get(0).getApplyCount());
            sheet.setData(rowStart + rows, 4, statForOneClass.get(0).getClassInfo() == null ? null : statForOneClass.get(0).getClassInfo().getAdviserNames());
            sheet.setData(rowStart + rows, 5, statForOneClass.get(0).getTeacherName());

            String topCount1 = "0", topCount2 = "0";
            for (ClassTopCountStats classTopCountStats : statForOneClass) {
                if (classTopCountStats.getExamId().equals(exam1)) {
                    topCount1 = classTopCountStats.getCountList().get(0).getValue();
                } else if (classTopCountStats.getExamId().equals(exam2)) {
                    topCount2 = classTopCountStats.getCountList().get(0).getValue();
                }
            }
            sheet.setData(rowStart + rows, 6, topCount1);
            sheet.setData(rowStart + rows, 7, topCount2);
            sheet.setData(rowStart + rows, 8, String.valueOf(Integer.valueOf(topCount2) - Integer.valueOf(topCount1)));
//            if (Integer.valueOf(topCount2) - Integer.valueOf(topCount1) < 0)
//                sheet.setTextColor(new CellPos(rowStart + rows, 8), IndexedColors.RED.getIndex());
//            else if (Integer.valueOf(topCount2) - Integer.valueOf(topCount1) > 0)
//                sheet.setTextColor(new CellPos(rowStart + rows, 8), IndexedColors.GREEN.getIndex());
//            else
//                sheet.setTextColor(new CellPos(rowStart + rows, 8), IndexedColors.BLACK.getIndex());
            rows += 1;
        }
        return rows;
    }
}
