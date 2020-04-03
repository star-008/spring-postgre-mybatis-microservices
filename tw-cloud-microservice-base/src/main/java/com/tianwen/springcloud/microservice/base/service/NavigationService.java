package com.tianwen.springcloud.microservice.base.service;

import com.tianwen.springcloud.commonapi.log.SystemServiceLog;
import com.tianwen.springcloud.datasource.base.BaseService;
import com.tianwen.springcloud.microservice.base.dao.NavigationMapper;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NavigationService extends BaseService<Navigation>
{
    @Autowired
    private NavigationMapper navigationMapper;

    public void fixNavigationParam(Navigation navigation)
    {
        if (!StringUtils.isEmpty(navigation.getSchoolsectionid()))
            navigation.setSchoolsectionid("," + navigation.getSchoolsectionid() + ",");
        if (!StringUtils.isEmpty(navigation.getSubjectid()))
            navigation.setSubjectid("," + navigation.getSubjectid() + ",");
        if (!StringUtils.isEmpty(navigation.getGradeid()))
            navigation.setGradeid("," + navigation.getGradeid() + ",");
        if (!StringUtils.isEmpty(navigation.getEditiontypeid()))
            navigation.setEditiontypeid("," + navigation.getEditiontypeid() + ",");
        if (!StringUtils.isEmpty(navigation.getBookmodelid()))
            navigation.setBookmodelid("," + navigation.getBookmodelid() + ",");
    }

    @SystemServiceLog(description = "SchoolSectionList")
    public List<DictItem> getSchoolSectionList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> schoolSectionList = navigationMapper.getSchoolSectionList(myParam);
        return schoolSectionList;
    }

    @SystemServiceLog(description = "SubjectList")
    public List<DictItem> getSubjectList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> subjectList = navigationMapper.getSubjectList(myParam);
        return subjectList;
    }

    @SystemServiceLog(description = "GradeList")
    public List<DictItem> getGradeList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> gradeList = navigationMapper.getGradeList(myParam);
        return gradeList;
    }

    @SystemServiceLog(description = "TermList")
    public List<DictItem> getTermList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> termList = navigationMapper.getTermList(myParam);
        return termList;
    }

    @SystemServiceLog(description = "ExamTypeList")
    public List<DictItem> getExamTypeList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> examTypeList = navigationMapper.getExamTypeList(myParam);
        return examTypeList;
    }

    @SystemServiceLog(description = "QuestionCategoryList")
    public List<DictItem> getQuestionCategoryList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> questionCategoryList = navigationMapper.getQuestionCategoryList(myParam);
        return questionCategoryList;
    }

    @SystemServiceLog(description = "QuestionTypeList")
    public List<DictItem> getQuestionTypeList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> questionTypeList = navigationMapper.getQuestionTypeList(myParam);
        return questionTypeList;
    }

    @SystemServiceLog(description = "PaperVolumeList")
    public List<DictItem> getPaperVolumeList(Navigation param)
    {
        Navigation myParam = param.copyEntity();
        fixNavigationParam(myParam);
        List<DictItem> paperVolumeList = navigationMapper.getPaperVolumeList(myParam);
        return paperVolumeList;
    }
}
