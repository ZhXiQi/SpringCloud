package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.UserAddrBean;
import com.springboot.core.bean.auto.UserAddrBeanExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserAddrBeanMapper {
    long countByExample(UserAddrBeanExample example);

    int deleteByExample(UserAddrBeanExample example);

    @Delete({
        "delete from user_addr",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_addr (id, create_time, ",
        "update_time, user_id, ",
        "address)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{userId,jdbcType=BIGINT}, ",
        "#{address,jdbcType=CHAR})"
    })
    int insert(UserAddrBean record);

    int insertSelective(UserAddrBean record);

    List<UserAddrBean> selectByExample(UserAddrBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, user_id, address",
        "from user_addr",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.UserAddrBeanMapper.BaseResultMap")
    UserAddrBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserAddrBean record, @Param("example") UserAddrBeanExample example);

    int updateByExample(@Param("record") UserAddrBean record, @Param("example") UserAddrBeanExample example);

    int updateByPrimaryKeySelective(UserAddrBean record);

    @Update({
        "update user_addr",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "address = #{address,jdbcType=CHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserAddrBean record);
}