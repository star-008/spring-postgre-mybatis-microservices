package com.tianwen.springcloud.microservice.base.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NavigationMapper extends MyMapper<Navigation>
{
    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getSchoolSectionList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getSubjectList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getGradeList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getTermList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getExamTypeList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getQuestionCategoryList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getQuestionTypeList(Navigation param);

    /**
     *
     * @param param
     * @return
     */
    List<DictItem> getPaperVolumeList(Navigation param);
}
