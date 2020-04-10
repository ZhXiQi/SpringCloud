package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.WalletRecordBean;
import com.springboot.core.bean.auto.WalletRecordBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface WalletRecordBeanMapper {
    long countByExample(WalletRecordBeanExample example);

    int deleteByExample(WalletRecordBeanExample example);

    @Delete({
        "delete from wallet_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into wallet_record (id, create_time, ",
        "update_time, wallet_id, ",
        "op_name, op_status, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{walletId,jdbcType=BIGINT}, ",
        "#{opName,jdbcType=VARCHAR}, #{opStatus,jdbcType=TINYINT}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(WalletRecordBean record);

    int insertSelective(WalletRecordBean record);

    List<WalletRecordBean> selectByExample(WalletRecordBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, wallet_id, op_name, op_status, remark",
        "from wallet_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.WalletRecordBeanMapper.BaseResultMap")
    WalletRecordBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WalletRecordBean record, @Param("example") WalletRecordBeanExample example);

    int updateByExample(@Param("record") WalletRecordBean record, @Param("example") WalletRecordBeanExample example);

    int updateByPrimaryKeySelective(WalletRecordBean record);

    @Update({
        "update wallet_record",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "wallet_id = #{walletId,jdbcType=BIGINT},",
          "op_name = #{opName,jdbcType=VARCHAR},",
          "op_status = #{opStatus,jdbcType=TINYINT},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(WalletRecordBean record);
}