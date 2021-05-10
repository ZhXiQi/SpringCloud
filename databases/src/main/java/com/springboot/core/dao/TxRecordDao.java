package com.springboot.core.dao;

import com.springboot.core.bean.auto.TxRecordBean;
import com.springboot.core.bean.auto.TxRecordBeanExample;
import com.springboot.core.dao.auto.TxRecordBeanMapper;
import com.springboot.core.model.TransListVo;
import com.springboot.core.model.TxRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/11/30 12:55
 */
@Mapper
public interface TxRecordDao extends TxRecordBeanMapper {

//    List<TransListVo> getTransListByExample(@Param("address") String address);

//    List<TransListVo> getTransListByAddressAndType(@Param("address") String address, @Param("type") Integer type);

    /**
     * 获取交易列表
     * @param address
     * @param type
     * @param from
     * @return
     */
    List<TransListVo> getList(@Param("address") String address, @Param("type") Integer type, @Param("from") Boolean from);

    /**
     * 统计总交易金额
     * @param example
     * @return
     */
    BigDecimal sumMoneyByExample(TxRecordBeanExample example);

    /**
     * 通过序列化查询交易信息
     * @param serialNum
     * @return
     */
    TxRecordBean selectBySerialNum(@Param("serialNum") long serialNum);

    /**
     * 插入并映射主键ID
     * @param recordBean
     * @return
     */
    int insertReturnId(TxRecordBean recordBean);

    /**
     * 根据序列化和类型查询交易信息
     * @param serialNum
     * @param type
     * @return
     */
    TxRecordVo selectTxRecordBySerialNumAndType(@Param("serialNum") String serialNum, @Param("type") Integer type);
}
