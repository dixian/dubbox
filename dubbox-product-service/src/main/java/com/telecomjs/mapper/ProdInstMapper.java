package com.telecomjs.mapper;

import com.telecomjs.entities.ProdInst;

public interface ProdInstMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String prodInstId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    int insert(ProdInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    int insertSelective(ProdInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    ProdInst selectByPrimaryKey(String prodInstId);

    ProdInst selectByAccNbr(String accNbr);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProdInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PROD_INST
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ProdInst record);
}