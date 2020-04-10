package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.TxRecordBean;
import com.springboot.core.bean.auto.TxRecordBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TxRecordBeanMapper {
    long countByExample(TxRecordBeanExample example);

    int deleteByExample(TxRecordBeanExample example);

    @Delete({
        "delete from tx_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tx_record (id, create_time, ",
        "update_time, tx_hash, ",
        "hash, block_num, ",
        "to_address, from_address, ",
        "money, remark, handle_time, ",
        "status, type, serial_num, ",
        "from_card_num, to_card_num, ",
        "from_balance, to_balance)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{txHash,jdbcType=VARCHAR}, ",
        "#{hash,jdbcType=VARCHAR}, #{blockNum,jdbcType=INTEGER}, ",
        "#{toAddress,jdbcType=VARCHAR}, #{fromAddress,jdbcType=VARCHAR}, ",
        "#{money,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR}, #{handleTime,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{serialNum,jdbcType=BIGINT}, ",
        "#{fromCardNum,jdbcType=CHAR}, #{toCardNum,jdbcType=CHAR}, ",
        "#{fromBalance,jdbcType=DECIMAL}, #{toBalance,jdbcType=DECIMAL})"
    })
    int insert(TxRecordBean record);

    int insertSelective(TxRecordBean record);

    List<TxRecordBean> selectByExample(TxRecordBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, tx_hash, hash, block_num, to_address, from_address, ",
        "money, remark, handle_time, status, type, serial_num, from_card_num, to_card_num, ",
        "from_balance, to_balance",
        "from tx_record",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.TxRecordBeanMapper.BaseResultMap")
    TxRecordBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TxRecordBean record, @Param("example") TxRecordBeanExample example);

    int updateByExample(@Param("record") TxRecordBean record, @Param("example") TxRecordBeanExample example);

    int updateByPrimaryKeySelective(TxRecordBean record);

    @Update({
        "update tx_record",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "tx_hash = #{txHash,jdbcType=VARCHAR},",
          "hash = #{hash,jdbcType=VARCHAR},",
          "block_num = #{blockNum,jdbcType=INTEGER},",
          "to_address = #{toAddress,jdbcType=VARCHAR},",
          "from_address = #{fromAddress,jdbcType=VARCHAR},",
          "money = #{money,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "handle_time = #{handleTime,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "serial_num = #{serialNum,jdbcType=BIGINT},",
          "from_card_num = #{fromCardNum,jdbcType=CHAR},",
          "to_card_num = #{toCardNum,jdbcType=CHAR},",
          "from_balance = #{fromBalance,jdbcType=DECIMAL},",
          "to_balance = #{toBalance,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TxRecordBean record);
}