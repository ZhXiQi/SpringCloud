package com.springboot.core.dao;

import com.springboot.core.dao.auto.OrganizationBeanMapper;
import com.springboot.core.model.BaseOrgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhXiQi
 * @Title: 暂时废弃
 * @date 2019/11/30 12:54
 */
@Mapper
public interface OrganizationDao extends OrganizationBeanMapper {

    /**
     * 根据钱包地址获取商户列表
     * @param address
     * @return
     */
    List<Object> getHistoryOrg(@Param("address") String address);

    /**
     * 查询注册的商户信息
     * @return
     */
    List<BaseOrgVo> selectOrgList();
}
