package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.OrganizationBean;
import com.springboot.core.bean.auto.OrganizationBeanExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrganizationBeanMapper {
    long countByExample(OrganizationBeanExample example);

    int deleteByExample(OrganizationBeanExample example);

    @Delete({
        "delete from organization",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into organization (id, create_time, ",
        "update_time, address, ",
        "card_num, bank_name, ",
        "bank_money, chain_money, ",
        "org_name, org_desc, ",
        "account_json)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{address,jdbcType=VARCHAR}, ",
        "#{cardNum,jdbcType=VARCHAR}, #{bankName,jdbcType=VARCHAR}, ",
        "#{bankMoney,jdbcType=DECIMAL}, #{chainMoney,jdbcType=DECIMAL}, ",
        "#{orgName,jdbcType=VARCHAR}, #{orgDesc,jdbcType=VARCHAR}, ",
        "#{accountJson,jdbcType=VARCHAR})"
    })
    int insert(OrganizationBean record);

    int insertSelective(OrganizationBean record);

    List<OrganizationBean> selectByExample(OrganizationBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, address, card_num, bank_name, bank_money, chain_money, ",
        "org_name, org_desc, account_json",
        "from organization",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.springboot.core.dao.auto.OrganizationBeanMapper.BaseResultMap")
    OrganizationBean selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrganizationBean record, @Param("example") OrganizationBeanExample example);

    int updateByExample(@Param("record") OrganizationBean record, @Param("example") OrganizationBeanExample example);

    int updateByPrimaryKeySelective(OrganizationBean record);

    @Update({
        "update organization",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "address = #{address,jdbcType=VARCHAR},",
          "card_num = #{cardNum,jdbcType=VARCHAR},",
          "bank_name = #{bankName,jdbcType=VARCHAR},",
          "bank_money = #{bankMoney,jdbcType=DECIMAL},",
          "chain_money = #{chainMoney,jdbcType=DECIMAL},",
          "org_name = #{orgName,jdbcType=VARCHAR},",
          "org_desc = #{orgDesc,jdbcType=VARCHAR},",
          "account_json = #{accountJson,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrganizationBean record);
}