package com.springboot.core.dao.auto;

import com.springboot.core.bean.auto.WalletBean;
import com.springboot.core.bean.auto.WalletBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface WalletBeanMapper {
    long countByExample(WalletBeanExample example);

    int deleteByExample(WalletBeanExample example);

    @Delete({
        "delete from wallet",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into wallet (id, create_time, ",
        "update_time, wallet_num, ",
        "org_name, address, balance, ",
        "card_num, user_id, wallet_status, ",
        "card_balance, account_json, ",
        "wallet_type, bank_name)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{walletNum,jdbcType=VARCHAR}, ",
        "#{orgName,jdbcType=VARCHAR}, #{address,jdbcType=CHAR}, #{balance,jdbcType=DECIMAL}, ",
        "#{cardNum,jdbcType=CHAR}, #{userId,jdbcType=BIGINT}, #{walletStatus,jdbcType=TINYINT}, ",
        "#{cardBalance,jdbcType=DECIMAL}, #{accountJson,jdbcType=VARCHAR}, ",
        "#{walletType,jdbcType=TINYINT}, #{bankName,jdbcType=VARCHAR})"
    })
    int insert(WalletBean record);

    int insertSelective(WalletBean record);

    List<WalletBean> selectByExample(WalletBeanExample example);

    @Select({
        "select",
        "id, create_time, update_time, wallet_num, org_name, address, balance, card_num, ",
        "user_id, wallet_status, card_balance, account_json, wallet_type, bank_name",
        "from wallet",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.springboot.core.dao.auto.WalletBeanMapper.BaseResultMap")
    WalletBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WalletBean record, @Param("example") WalletBeanExample example);

    int updateByExample(@Param("record") WalletBean record, @Param("example") WalletBeanExample example);

    int updateByPrimaryKeySelective(WalletBean record);

    @Update({
        "update wallet",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "wallet_num = #{walletNum,jdbcType=VARCHAR},",
          "org_name = #{orgName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=CHAR},",
          "balance = #{balance,jdbcType=DECIMAL},",
          "card_num = #{cardNum,jdbcType=CHAR},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "wallet_status = #{walletStatus,jdbcType=TINYINT},",
          "card_balance = #{cardBalance,jdbcType=DECIMAL},",
          "account_json = #{accountJson,jdbcType=VARCHAR},",
          "wallet_type = #{walletType,jdbcType=TINYINT},",
          "bank_name = #{bankName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(WalletBean record);
}