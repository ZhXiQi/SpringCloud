package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.OrgBean;
import com.springboot.core.bean.auto.OrgBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrgBeanMapper {
    long countByExample(OrgBeanExample example);

    int deleteByExample(OrgBeanExample example);

    @Delete({
        "delete from org",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into org (id, create_time, ",
        "update_time, user_id, ",
        "org_name, bank_name, ",
        "wallet_num)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{userId,jdbcType=BIGINT}, ",
        "#{orgName,jdbcType=VARCHAR}, #{bankName,jdbcType=VARCHAR}, ",
        "#{walletNum,jdbcType=VARCHAR})"
    })
    int insert(OrgBean record);

    int insertSelective(OrgBean record);

    List<OrgBean> selectByExample(OrgBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, user_id, org_name, bank_name, wallet_num",
        "from org",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.OrgBeanMapper.BaseResultMap")
    OrgBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrgBean record, @Param("example") OrgBeanExample example);

    int updateByExample(@Param("record") OrgBean record, @Param("example") OrgBeanExample example);

    int updateByPrimaryKeySelective(OrgBean record);

    @Update({
        "update org",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "org_name = #{orgName,jdbcType=VARCHAR},",
          "bank_name = #{bankName,jdbcType=VARCHAR},",
          "wallet_num = #{walletNum,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OrgBean record);
}