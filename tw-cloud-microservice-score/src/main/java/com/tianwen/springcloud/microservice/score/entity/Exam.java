package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "t_e_cj_exam")
@Fillable
public class Exam extends BaseEntity {
    private static final String TABLE_ID_PREFIX = "EX";

    public static final String STATUS_INITIAL = "0";
    public static final String STATUS_EDITING = "1";
    public static final String STATUS_HALF_PUBLISHED = "2";
    public static final String STATUS_PUBLISHED = "3";
    public static final String STATUS_DELETED = "9";

    public static final String SYNC_STATUS_MANUAL = "1";
    public static final String SYNC_STATUS_SYNCED = "2";
    public static final String SYNC_STATUS_CLOSED = "4";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('"+ TABLE_ID_PREFIX + "', 'seq_exam')")
    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String id;

    @Column(name = "examname")
    @ApiModelProperty(name = "examname", value = "考试名称")
    @JsonProperty(value = "examname")
    private String name;

    @Column(name = "termid")
    @ApiModelProperty(name = "termid", value = "学期")
    @JsonProperty("termid")
    private String termId;

    @JsonProperty( "term")
    @Fill(entityType = Fill.ENTITY_TYPE_EXAM_TERM)
    private Term term;

    @Column(name = "examtypeid")
    @ApiModelProperty(name = "examtypeid", value = "考试类型")
    @JsonProperty( "examtypeid")
    private String typeId;

    @JsonProperty( "examtype")
    @Fill(entityType = Fill.ENTITY_TYPE_EXAM_TYPE)
    private DictItem type;

    @Column(name = "schoolsectionid")
    @ApiModelProperty(name = "schoolsectionid", value = "schoolsectionid")
    @JsonProperty( "schoolsectionid")
    private String schoolSectionId;

    @Fill(entityType = Fill.ENTITY_TYPE_SCHOOL_SECTION)
    @JsonProperty( "schoolsection")
    private DictItem schoolSection;

    @Column(name = "examstartday")
    @ApiModelProperty(name = "examstartday", value = "考试时间")
    @JsonProperty("examstartday")
    private Timestamp startDate;

    @Column(name = "examendday")
    @ApiModelProperty(name = "examendday", value = "考试时间")
    @JsonProperty("examendday")
    private Timestamp endDate;

    @Column(name = "examnumber")
    @ApiModelProperty(name = "examnumber", value = "examnumber")
    @JsonProperty("examnumber")
    private String number;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "schoolid")
    @ApiModelProperty(name = "schoolid", value = "schoolid")
    @JsonProperty(value = "schoolid")
    private String schoolId;

    @Column(name = "createtime")
    @ApiModelProperty(name = "createtime", value = "createtime")
    @JsonProperty(value = "createtime")
    private Timestamp createdTime;

    @Column(name = "lastmodifytime")
    @ApiModelProperty(name = "lastmodifytime", value = "lastmodifytime")
    @JsonProperty(value = "lastmodifytime")
    private Timestamp lastModifiedTime;

    @Column(name = "creatorid")
    @ApiModelProperty(name = "creatorid", value = "creatorid")
    @JsonProperty("creatorid")
    private String creatorId;

    @Column(name = "creatorname")
    @ApiModelProperty(name = "creatorname", value = "creatorname")
    @JsonProperty("creatorname")
    private String creatorName;

    @Column(name = "examcount")
    @ApiModelProperty(name = "examcount", value = "examcount")
    @JsonProperty("examcount")
    private String examCount;

    @Column(name = "syncstatus")
    @ApiModelProperty(name = "syncstatus", value = "syncstatus")
    @JsonProperty(value = "syncstatus")
    private String syncStatus;

    @Column(name = "synctime")
    @ApiModelProperty(name = "synctime", value = "synctime")
    @JsonProperty(value = "synctime")
    private Timestamp syncTime;

    @Transient
    @ApiModelProperty(name = "classSubjectList", value = "classSubjectList")
    @JsonProperty("classSubjectList")
    @Fill
    private List<ClassSubject> classSubjectList;

    private BaseData base;

    public Exam() {

    }

    public Exam(String name) {
        this.name = name;
    }

    public Exam(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSchoolSectionId() {
        return schoolSectionId;
    }

    public void setSchoolSectionId(String schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<ClassSubject> getClassSubjectList() {
        return classSubjectList;
    }

    public void setClassSubjectList(List<ClassSubject> classSubjectList) {
        this.classSubjectList = classSubjectList;
    }

    public void fillClassSubjectList(List<ExamClass> examClassList, List<ExamSubjectScore> examSubjectList) {
        if (classSubjectList == null) {
            classSubjectList = new ArrayList<>();
        }

        int index;
        
        for(ExamClass examClass: examClassList) {
            ClassSubject classSubject = new ClassSubject(examClass.getGradeId());
            if ((index = classSubjectList.indexOf(classSubject)) > -1) {
                classSubject = classSubjectList.get(index);
            } else {
                classSubjectList.add(classSubject);
            }

            examClass.setGradeId(null);

            if (ClassInfo.CLASS_TYPE_NORMAL.equals(examClass.getClassType())) {
                if (classSubject.getExamClassList() == null) {
                    classSubject.setExamClassList(new ArrayList<>());
                }
                classSubject.getExamClassList().add(examClass);
            } else {
                if (classSubject.getExamSpecialityClassList() == null) {
                    classSubject.setExamSpecialityClassList(new ArrayList<>());
                }

                ExamSubjectScore examSubject = new ExamSubjectScore(examClass.getExamId(), examClass.getGradeId(), examClass.getSubjectId());
                SpecialityClass specialityClass = new SpecialityClass(examSubject);

                if ((index = classSubject.getExamSpecialityClassList().indexOf(specialityClass)) >= 0) {
                    specialityClass = classSubject.getExamSpecialityClassList().get(index);
                } else {
                    if ((index = examSubjectList.indexOf(examSubject)) >= 0) {
                        specialityClass.setExamSubject(examSubjectList.remove(index));
                        classSubject.getExamSpecialityClassList().add(specialityClass);
                    } else {
                        continue;
                    }
                }

                if (specialityClass.getExamClassList() == null) {
                    specialityClass.setExamClassList(new ArrayList<>());
                }
                specialityClass.getExamClassList().add(examClass);
            }
        }

        for(ExamSubjectScore examSubject: examSubjectList) {
            ClassSubject classSubject = new ClassSubject(examSubject.getGradeId());
            if ((index = classSubjectList.indexOf(classSubject)) > -1) {
                classSubject = classSubjectList.get(index);
            } else {
                classSubjectList.add(classSubject);
            }
            if (classSubject.getExamSubjectList() == null) {
                classSubject.setExamSubjectList(new ArrayList<>());
            }
            examSubject.setGradeId(null);
            classSubject.getExamSubjectList().add(examSubject);
        }

    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public DictItem getType() {
        return type;
    }

    public void setType(DictItem type) {
        this.type = type;
    }

    public DictItem getSchoolSection() {
        return schoolSection;
    }

    public void setSchoolSection(DictItem schoolSection) {
        this.schoolSection = schoolSection;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getExamCount() {
        return examCount;
    }

    public void setExamCount(String examCount) {
        this.examCount = examCount;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Timestamp getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    public BaseData getBase() {
        return base;
    }

    public void setBase(BaseData base) {
        this.base = base;
    }

    public static class BaseData {

        @JsonProperty("typelist")
        private List<DictItem> typeList;

        @JsonProperty("termlist")
        private List<Term> termList;

        @JsonProperty("schoolsectionlist")
        private List<DictItem> schoolSectionList;

        @JsonProperty("gradelist")
        private List<DictItem> gradeList;

        @JsonProperty("subjectlist")
        private List<DictItem> subjectList;

        public List<Term> getTermList() {
            return termList;
        }

        public void setTermList(List<Term> termList) {
            this.termList = termList;
        }

        public List<DictItem> getSchoolSectionList() {
            return schoolSectionList;
        }

        public void setSchoolSectionList(List<DictItem> schoolSectionList) {
            this.schoolSectionList = schoolSectionList;
        }

        public List<DictItem> getGradeList() {
            return gradeList;
        }

        public void setGradeList(List<DictItem> gradeList) {
            this.gradeList = gradeList;
        }

        public List<DictItem> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<DictItem> subjectList) {
            this.subjectList = subjectList;
        }

        public List<DictItem> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<DictItem> typeList) {
            this.typeList = typeList;
        }
    }
}