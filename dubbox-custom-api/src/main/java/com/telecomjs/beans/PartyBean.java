package com.telecomjs.beans;

import java.io.Serializable;
import java.util.Date;

public class PartyBean implements Serializable  {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.PARTY_ID
     *
     * @mbggenerated
     */
    private String partyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.CREATE_DATE
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.CREATE_STAFF
     *
     * @mbggenerated
     */
    private String createStaff;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.ENGLISH_NAME
     *
     * @mbggenerated
     */
    private String englishName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.PARTY_ABBRNAME
     *
     * @mbggenerated
     */
    private String partyAbbrname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.PARTY_NAME
     *
     * @mbggenerated
     */
    private String partyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.PARTY_TYPE
     *
     * @mbggenerated
     */
    private String partyType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.REMARK
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.STATUS_CD
     *
     * @mbggenerated
     */
    private String statusCd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.STATUS_DATE
     *
     * @mbggenerated
     */
    private Date statusDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.UPDATE_DATE
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PARTY.UPDATE_STAFF
     *
     * @mbggenerated
     */
    private String updateStaff;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.PARTY_ID
     *
     * @return the value of PARTY.PARTY_ID
     *
     * @mbggenerated
     */
    public String getPartyId() {
        return partyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.PARTY_ID
     *
     * @param partyId the value for PARTY.PARTY_ID
     *
     * @mbggenerated
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId == null ? null : partyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.CREATE_DATE
     *
     * @return the value of PARTY.CREATE_DATE
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.CREATE_DATE
     *
     * @param createDate the value for PARTY.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.CREATE_STAFF
     *
     * @return the value of PARTY.CREATE_STAFF
     *
     * @mbggenerated
     */
    public String getCreateStaff() {
        return createStaff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.CREATE_STAFF
     *
     * @param createStaff the value for PARTY.CREATE_STAFF
     *
     * @mbggenerated
     */
    public void setCreateStaff(String createStaff) {
        this.createStaff = createStaff == null ? null : createStaff.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.ENGLISH_NAME
     *
     * @return the value of PARTY.ENGLISH_NAME
     *
     * @mbggenerated
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.ENGLISH_NAME
     *
     * @param englishName the value for PARTY.ENGLISH_NAME
     *
     * @mbggenerated
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.PARTY_ABBRNAME
     *
     * @return the value of PARTY.PARTY_ABBRNAME
     *
     * @mbggenerated
     */
    public String getPartyAbbrname() {
        return partyAbbrname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.PARTY_ABBRNAME
     *
     * @param partyAbbrname the value for PARTY.PARTY_ABBRNAME
     *
     * @mbggenerated
     */
    public void setPartyAbbrname(String partyAbbrname) {
        this.partyAbbrname = partyAbbrname == null ? null : partyAbbrname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.PARTY_NAME
     *
     * @return the value of PARTY.PARTY_NAME
     *
     * @mbggenerated
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.PARTY_NAME
     *
     * @param partyName the value for PARTY.PARTY_NAME
     *
     * @mbggenerated
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.PARTY_TYPE
     *
     * @return the value of PARTY.PARTY_TYPE
     *
     * @mbggenerated
     */
    public String getPartyType() {
        return partyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.PARTY_TYPE
     *
     * @param partyType the value for PARTY.PARTY_TYPE
     *
     * @mbggenerated
     */
    public void setPartyType(String partyType) {
        this.partyType = partyType == null ? null : partyType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.REMARK
     *
     * @return the value of PARTY.REMARK
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.REMARK
     *
     * @param remark the value for PARTY.REMARK
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.STATUS_CD
     *
     * @return the value of PARTY.STATUS_CD
     *
     * @mbggenerated
     */
    public String getStatusCd() {
        return statusCd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.STATUS_CD
     *
     * @param statusCd the value for PARTY.STATUS_CD
     *
     * @mbggenerated
     */
    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd == null ? null : statusCd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.STATUS_DATE
     *
     * @return the value of PARTY.STATUS_DATE
     *
     * @mbggenerated
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.STATUS_DATE
     *
     * @param statusDate the value for PARTY.STATUS_DATE
     *
     * @mbggenerated
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.UPDATE_DATE
     *
     * @return the value of PARTY.UPDATE_DATE
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.UPDATE_DATE
     *
     * @param updateDate the value for PARTY.UPDATE_DATE
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PARTY.UPDATE_STAFF
     *
     * @return the value of PARTY.UPDATE_STAFF
     *
     * @mbggenerated
     */
    public String getUpdateStaff() {
        return updateStaff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PARTY.UPDATE_STAFF
     *
     * @param updateStaff the value for PARTY.UPDATE_STAFF
     *
     * @mbggenerated
     */
    public void setUpdateStaff(String updateStaff) {
        this.updateStaff = updateStaff == null ? null : updateStaff.trim();
    }
}