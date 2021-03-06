package com.telecomjs.beans;

import java.io.Serializable;
import java.util.Date;

public class EopAppAccessBean implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.ACCESS_ID
     *
     * @mbggenerated
     */
    private Long accessId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.APP_ID
     *
     * @mbggenerated
     */
    private Long appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.DOMAIN
     *
     * @mbggenerated
     */
    private String domain;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.METHOD_NAME
     *
     * @mbggenerated
     */
    private String methodName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.TIMES
     *
     * @mbggenerated
     */
    private Long times;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EOP_APP_ACCESS.CREATE_DATE
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.ACCESS_ID
     *
     * @return the value of EOP_APP_ACCESS.ACCESS_ID
     *
     * @mbggenerated
     */
    public Long getAccessId() {
        return accessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.ACCESS_ID
     *
     * @param accessId the value for EOP_APP_ACCESS.ACCESS_ID
     *
     * @mbggenerated
     */
    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.APP_ID
     *
     * @return the value of EOP_APP_ACCESS.APP_ID
     *
     * @mbggenerated
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.APP_ID
     *
     * @param appId the value for EOP_APP_ACCESS.APP_ID
     *
     * @mbggenerated
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.DOMAIN
     *
     * @return the value of EOP_APP_ACCESS.DOMAIN
     *
     * @mbggenerated
     */
    public String getDomain() {
        return domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.DOMAIN
     *
     * @param domain the value for EOP_APP_ACCESS.DOMAIN
     *
     * @mbggenerated
     */
    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.METHOD_NAME
     *
     * @return the value of EOP_APP_ACCESS.METHOD_NAME
     *
     * @mbggenerated
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.METHOD_NAME
     *
     * @param methodName the value for EOP_APP_ACCESS.METHOD_NAME
     *
     * @mbggenerated
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.TIMES
     *
     * @return the value of EOP_APP_ACCESS.TIMES
     *
     * @mbggenerated
     */
    public Long getTimes() {
        return times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.TIMES
     *
     * @param times the value for EOP_APP_ACCESS.TIMES
     *
     * @mbggenerated
     */
    public void setTimes(Long times) {
        this.times = times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EOP_APP_ACCESS.CREATE_DATE
     *
     * @return the value of EOP_APP_ACCESS.CREATE_DATE
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EOP_APP_ACCESS.CREATE_DATE
     *
     * @param createDate the value for EOP_APP_ACCESS.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}