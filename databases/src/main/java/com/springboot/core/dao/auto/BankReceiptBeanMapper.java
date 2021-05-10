package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.BankReceiptBean;
import com.springboot.core.bean.auto.BankReceiptBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BankReceiptBeanMapper {
    long countByExample(BankReceiptBeanExample example);

    int deleteByExample(BankReceiptBeanExample example);

    @Delete({
        "delete from bank_receipt",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into bank_receipt (id, create_time, ",
        "update_time, tx_id, ",
        "money, remark, handle_time, ",
        "tx_hash, hash, block_num, ",
        "status, serial_num, ",
        "from_card_num, to_card_num, ",
        "type)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{txId,jdbcType=BIGINT}, ",
        "#{money,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR}, #{handleTime,jdbcType=INTEGER}, ",
        "#{txHash,jdbcType=VARCHAR}, #{hash,jdbcType=VARCHAR}, #{blockNum,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{serialNum,jdbcType=VARCHAR}, ",
        "#{fromCardNum,jdbcType=CHAR}, #{toCardNum,jdbcType=CHAR}, ",
        "#{type,jdbcType=INTEGER})"
    })
    int insert(BankReceiptBean record);

    int insertSelective(BankReceiptBean record);

    List<BankReceiptBean> selectByExample(BankReceiptBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, tx_id, money, remark, handle_time, tx_hash, hash, ",
        "block_num, status, serial_num, from_card_num, to_card_num, type",
        "from bank_receipt",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.BankReceiptBeanMapper.BaseResultMap")
    BankReceiptBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BankReceiptBean record, @Param("example") BankReceiptBeanExample example);

    int updateByExample(@Param("record") BankReceiptBean record, @Param("example") BankReceiptBeanExample example);

    int updateByPrimaryKeySelective(BankReceiptBean record);

    @Update({
        "update bank_receipt",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "tx_id = #{txId,jdbcType=BIGINT},",
          "money = #{money,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "handle_time = #{handleTime,jdbcType=INTEGER},",
          "tx_hash = #{txHash,jdbcType=VARCHAR},",
          "hash = #{hash,jdbcType=VARCHAR},",
          "block_num = #{blockNum,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "serial_num = #{serialNum,jdbcType=VARCHAR},",
          "from_card_num = #{fromCardNum,jdbcType=CHAR},",
          "to_card_num = #{toCardNum,jdbcType=CHAR},",
          "type = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(BankReceiptBean record);
}