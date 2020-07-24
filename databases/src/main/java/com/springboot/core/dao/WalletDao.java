package com.springboot.core.dao;

import com.springboot.core.bean.auto.WalletBeanExample;
import com.springboot.core.dao.auto.WalletBeanMapper;
import com.springboot.core.bean.auto.WalletBean;
import com.springboot.core.model.WalletListVo;
import com.springboot.core.model.WalletVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/6 18:53
 */
@Mapper
public interface WalletDao extends WalletBeanMapper {
    /**
     * 插入并映射主键
     * @param record
     * @return
     */
    int insertWithReturn(WalletBean record);

    /**
     * 多条件查询钱包列表
     * @param walletBean
     * @return
     */
    List<WalletListVo> selectWalletListByExample(@Param("walletBean") WalletBean walletBean);

    /**
     * 统计某用户的钱包数量和总交易量
     * @param userId
     * @return
     */
    Map<String,Long> countAndSumByUserId(@Param("userId") long userId);

    /**
     * 根据address获取贸易往来商户列表
     * @param address
     * @return
     */
    List<Object> getHistoryOrg(String address);

    /**
     * 多条件求钱包余额总和
     * @param example
     * @return
     */
    BigDecimal sumBalanceByExample(WalletBeanExample example);

    /**
     * 根据钱包地址查询钱包信息
     * @param fromAddress
     * @return
     */
    WalletBean selectByAddress(@Param("fromAddress") String fromAddress);

    /**
     * 根据钱包ID查询钱包部分信息
     * @param walletId
     * @return
     */
    WalletVo selectById(@Param("walletId") long walletId);
}
