package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.UserChangeBean;
import com.springboot.core.bean.auto.UserChangeBeanExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserChangeBeanMapper {
    long countByExample(UserChangeBeanExample example);

    int deleteByExample(UserChangeBeanExample example);

    @Delete({
        "delete from user_change",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_change (id, create_time, ",
        "update_time, user_id, ",
        "email, org_name, ",
        "social_credit_code, url, ",
        "org_addr)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{userId,jdbcType=BIGINT}, ",
        "#{email,jdbcType=VARCHAR}, #{orgName,jdbcType=VARCHAR}, ",
        "#{socialCreditCode,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, ",
        "#{orgAddr,jdbcType=VARCHAR})"
    })
    int insert(UserChangeBean record);

    int insertSelective(UserChangeBean record);

    List<UserChangeBean> selectByExample(UserChangeBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, user_id, email, org_name, social_credit_code, ",
        "url, org_addr",
        "from user_change",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.UserChangeBeanMapper.BaseResultMap")
    UserChangeBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserChangeBean record, @Param("example") UserChangeBeanExample example);

    int updateByExample(@Param("record") UserChangeBean record, @Param("example") UserChangeBeanExample example);

    int updateByPrimaryKeySelective(UserChangeBean record);

    @Update({
        "update user_change",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "email = #{email,jdbcType=VARCHAR},",
          "org_name = #{orgName,jdbcType=VARCHAR},",
          "social_credit_code = #{socialCreditCode,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "org_addr = #{orgAddr,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserChangeBean record);
}