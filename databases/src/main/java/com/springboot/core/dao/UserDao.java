package com.springboot.core.dao;

import com.springboot.core.dao.auto.UserBeanMapper;
import com.springboot.core.bean.auto.UserBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/6 19:10
 */
@Mapper
public interface UserDao extends UserBeanMapper {
    /**
     * 插入并映射主键
     * @param build
     * @return
     */
    int insertReturnId(UserBean build);
}
