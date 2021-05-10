package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.UserBean;
import com.springboot.core.bean.auto.UserBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserBeanMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user (id, create_time, ",
        "update_time, user_type, ",
        "email, org_name, ",
        "social_credit_code, url, ",
        "org_addr, current_addr, ",
        "user_pwd, user_name)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{userType,jdbcType=TINYINT}, ",
        "#{email,jdbcType=VARCHAR}, #{orgName,jdbcType=VARCHAR}, ",
        "#{socialCreditCode,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, ",
        "#{orgAddr,jdbcType=VARCHAR}, #{currentAddr,jdbcType=CHAR}, ",
        "#{userPwd,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR})"
    })
    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, user_type, email, org_name, social_credit_code, ",
        "url, org_addr, current_addr, user_pwd, user_name",
        "from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.UserBeanMapper.BaseResultMap")
    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    @Update({
        "update user",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "user_type = #{userType,jdbcType=TINYINT},",
          "email = #{email,jdbcType=VARCHAR},",
          "org_name = #{orgName,jdbcType=VARCHAR},",
          "social_credit_code = #{socialCreditCode,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "org_addr = #{orgAddr,jdbcType=VARCHAR},",
          "current_addr = #{currentAddr,jdbcType=CHAR},",
          "user_pwd = #{userPwd,jdbcType=VARCHAR},",
          "user_name = #{userName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserBean record);
}