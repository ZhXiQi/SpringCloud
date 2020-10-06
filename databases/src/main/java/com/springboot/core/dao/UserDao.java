package com.springboot.core.dao;

import com.springboot.core.dao.auto.UserBeanMapper;
import com.springboot.core.bean.auto.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 批量插入
     * Oracle批量插入和mysql不同：
     *         INSERT ALL
     *         INTO my_table(field_1,field_2) VALUES (value_1,value_2)
     *         INTO my_table(field_1,field_2) VALUES (value_3,value_4)
     *         INTO my_table(field_1,field_2) VALUES (value_5,value_6)
     *         SELECT 1 FROM DUAL;
     * or        
     *         insert into table_name (column1,column2,...) select value1,value2,... from table_name2 where condition
     * @param x
     * @return
     */
    int insertBatch(List x);
}
