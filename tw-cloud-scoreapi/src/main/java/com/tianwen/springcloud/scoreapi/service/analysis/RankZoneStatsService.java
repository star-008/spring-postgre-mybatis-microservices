package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.RankZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankZoneStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class RankZoneStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/rank-zone-stats.xls";
    public static final String EXPORT_FILE_NAME = "{gradeName}{examName}--班级单科名次段统计.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int ROW_INDEX_SCORE_ZONE_START = 2;

    @Autowired
    private RankZoneStatsMicroApi rankZoneStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<RankZoneStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(RankZoneStats.class, rankZoneStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<RankZoneStats> rankZoneStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            int rowScoreZone = ROW_INDEX_SCORE_ZONE_START + 1;
            int scoreZoneLength = 0;
            int columnCountStart = 1;
            int columnLength = 1;

            for (RankZoneStats rankZoneStats : rankZoneStatsList) {
                scoreZoneLength = rankZoneStats.getRankZoneList().size();

                for (int i = 0; i < scoreZoneLength; i ++) {
                    sheet.setData(rowScoreZone + i, 0, "[*," + rankZoneStats.getRankZoneList().get(i).getLevel() + "]")
                            .setCellAlignCenter(new CellPos(rowScoreZone + i, 0));
                }

                int rowCountStart = rowScoreZone + scoreZoneLength;
                //Adding rows field
                sheet.setData(rowCountStart ++, 0, ICommonConstants.COUNT)
                        .setCellAlignCenter(new CellPos(rowCountStart - 1, 0));
                sheet.setData(rowCountStart ++, 0, ICommonConstants.MAX_SCORE)
                        .setCellAlignCenter(new CellPos(rowCountStart - 1, 0));
                sheet.setData(rowCountStart ++, 0, ICommonConstants.MIN_SCORE)
                        .setCellAlignCenter(new CellPos(rowCountStart - 1, 0));
                sheet.setData(rowCountStart ++, 0, ICommonConstants.AVG_SCORE)
                        .setCellAlignCenter(new CellPos(rowCountStart - 1, 0));
                sheet.setData(rowCountStart ++, 0, ICommonConstants.DIFFERENCE)
                        .setCellAlignCenter(new CellPos(rowCountStart - 1, 0));

                //Adding columns field
                sheet.setData(ROW_INDEX_SCORE_ZONE_START, columnCountStart ++, getClassFullName(rankZoneStats.getClassInfo()))
                        .setCellAlignCenter(new CellPos(rowCountStart, 0));

                for (int i = 0; i < scoreZoneLength; i ++) {
                    sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getRankZoneList().get(i).getCount())
                            .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                }
                sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getApplyCount())
                        .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getMaxScore())
                        .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getMinScore())
                        .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getScore())
                        .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                sheet.setData(rowScoreZone ++, columnLength, rankZoneStats.getDiff())
                        .setCellAlignCenter(new CellPos(rowScoreZone - 1, columnLength));
                columnLength ++;
                rowScoreZone = ROW_INDEX_SCORE_ZONE_START + 1;

            }


            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(EXPORT_FILE_NAME
                    .replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

}
