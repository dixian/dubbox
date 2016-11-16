package com.telecomjs.mapper;

import com.telecomjs.entities.ProdInst;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

//@EnableCaching
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

    /**
     * 根据号码查询产品信息
     * @param accNbr 接入号码
     * @return 产品信息bean
     */

    //@Cacheable(value = "product",key = "'accnbr_'.concat( #accNbr.toString() )")
    @Cacheable(value = "product" )
    public ProdInst selectByAccNbr(String accNbr);

    List<ProdInst> queryByPartyId(String partyId);

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