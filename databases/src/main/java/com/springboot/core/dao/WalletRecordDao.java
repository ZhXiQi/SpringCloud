package com.springboot.core.dao;

import com.springboot.core.bean.auto.WalletRecordBean;
import com.springboot.core.dao.auto.WalletRecordBeanMapper;
import com.springboot.core.model.WalletUnusualVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/31 12:47
 */
@Mapper
public interface WalletRecordDao extends WalletRecordBeanMapper {

    /**
     * 插入数据带返回主键
     * @param recordBean
     * @return
     */
    int insertReturnId(WalletRecordBean recordBean);

    /**
     * 获取钱包列表
     * @param userId
     * @param status
     * @param offset
     * @param pageSize
     * @return
     */
    List<WalletUnusualVo> selectWalletUnusualList(@Param("userId") Long userId, @Param("status") Integer status, @Param("opType") int opType, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

//    List<WalletUnusualVo> selectWalletUnusualList(@Param("userId") Long userId, @Param("status") Integer status, @Param("opType") int opType, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
