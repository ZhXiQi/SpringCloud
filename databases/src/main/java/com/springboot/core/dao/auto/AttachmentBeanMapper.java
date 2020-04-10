package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.AttachmentBean;
import com.springboot.core.bean.auto.AttachmentBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AttachmentBeanMapper {
    long countByExample(AttachmentBeanExample example);

    int deleteByExample(AttachmentBeanExample example);

    @Delete({
        "delete from attachment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into attachment (id, create_time, ",
        "update_time, relate_id, ",
        "attach_type, url, ",
        "op_type)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{relateId,jdbcType=BIGINT}, ",
        "#{attachType,jdbcType=INTEGER}, #{url,jdbcType=VARCHAR}, ",
        "#{opType,jdbcType=INTEGER})"
    })
    int insert(AttachmentBean record);

    int insertSelective(AttachmentBean record);

    List<AttachmentBean> selectByExample(AttachmentBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, relate_id, attach_type, url, op_type",
        "from attachment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.AttachmentBeanMapper.BaseResultMap")
    AttachmentBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AttachmentBean record, @Param("example") AttachmentBeanExample example);

    int updateByExample(@Param("record") AttachmentBean record, @Param("example") AttachmentBeanExample example);

    int updateByPrimaryKeySelective(AttachmentBean record);

    @Update({
        "update attachment",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "relate_id = #{relateId,jdbcType=BIGINT},",
          "attach_type = #{attachType,jdbcType=INTEGER},",
          "url = #{url,jdbcType=VARCHAR},",
          "op_type = #{opType,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AttachmentBean record);
}