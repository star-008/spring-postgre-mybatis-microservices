package com.tianwen.springcloud.microservice.base.entity;

import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.commonapi.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "t_e_term")
public class Term extends BaseEntity
{
    @Id
    @Column(name = "termid")
    @ApiModelProperty(value = "", required = true)
    private String termId;

    @Column(name = "termname")
    @ApiModelProperty(value = "", required = true)
    private String termName;

    @Column(name = "termtype")
    @ApiModelProperty(value = "", required = true)
    private String termType;

    @Column(name = "termtypename")
    private String termTypeName;

    @Column(name = "orgid")
    @ApiModelProperty(value = "", required = true)
    private String orgId;

    @Column(name = "orgname")
    private String orgName;

    @Column(name = "startdate")
    @ApiModelProperty(value = "", required = true)
    private Timestamp startDate;

    @Column(name = "enddate")
    @ApiModelProperty(value = "", required = true)
    private Timestamp endDate;


    @Column(name = "lastmodifytime")
    @ApiModelProperty(value = "", required = true)
    private Timestamp lastModifyTime;

    @Column(name = "weektype")
    @ApiModelProperty(value = "", required = true)
    private String weekType;

    @Column(name = "status")
    @ApiModelProperty(value = "", required = true)
    private String status;

    public Term(){}

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Object lastModifyTime) {
        if (lastModifyTime instanceof Timestamp) {
            this.lastModifyTime = (Timestamp)lastModifyTime;
        } else if (lastModifyTime instanceof Date) {
            this.lastModifyTime = new Timestamp(((Date) lastModifyTime).getTime());
        } else {
            this.lastModifyTime = Timestamp.valueOf((String) lastModifyTime);
        }
    }

    public String getTermTypeName() {
        return termTypeName;
    }

    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        if (startDate instanceof Timestamp) {
            this.startDate = (Timestamp)startDate;
        } else if (startDate instanceof Date) {
            this.startDate = new Timestamp(((Date) startDate).getTime());
        } else {
            this.startDate = new Timestamp(DateUtil.formdate((String) startDate).getTime());
        }
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        if (endDate instanceof Timestamp) {
            this.endDate = (Timestamp)endDate;
        } else if (endDate instanceof Date) {
            this.endDate = new Timestamp(((Date) endDate).getTime());
        } else {
            this.endDate = new Timestamp(DateUtil.formdate((String) endDate).getTime());
        }
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }
}
