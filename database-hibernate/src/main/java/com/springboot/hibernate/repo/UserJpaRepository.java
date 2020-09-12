package com.springboot.hibernate.repo;

import com.springboot.hibernate.bean.UserBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserJpaRepository extends BaseDao<UserBean, Integer> {

    UserBean findByUsername(String username);

    @Query(nativeQuery = true,
            value = "select count(1) " +
                    "from user " +
                    "where user.name=?1")
    public int countByName(String name);
//
//    @Query(nativeQuery = true, value = "select * " +
//            "from user " +
//            "where user.status=?1 limit ?2, ?3")
//    List<User> getPageByDeptId(Integer status, int pageOffset, int pageSize);
//
//    Integer countByStatus(Integer status);

    List<UserBean> findByStatus(Integer status);

    List<UserBean> findByHasLogin(Integer hasLogin);

    @Query(nativeQuery = true, value = "select count(1) from user_roles where FIND_IN_SET(?1,roles)")
    Integer countOepratorNum(Integer roleId);

    @Query(nativeQuery = true, value = "select count(1) from user u where u.has_login=?2 and FIND_IN_SET(?1,roles)")
    Integer countOepratorNum(Integer roleId, Integer hasLogin);

    @Query(nativeQuery = true, value = "select count(1) from user u where u.status=?2 and FIND_IN_SET(?1,roles)")
    Integer countStatusOepratorNum(Integer roleId, Integer status);


    @Query(nativeQuery = true, value = "select id from user where FIND_IN_SET(?1,roles)")
    List<Integer> getAllOepratorId(Integer roleId);

    /**
     * 根据角色类型和机构类型来查询值班员id列表,下面两个函数一个省级，一个地市级，机构类型标识，当前系统可以根据 用户表的dept_level字段长度来判断，省级长度为3，地市级大于3
     * @param rolesId 角色类型码
     * @return
     */
    @Query(value = "select u.id from user as u where FIND_IN_SET(:rolesId,roles) and length(u.dept_level) = 3",nativeQuery = true)
    List<Integer> getOperatorIdByRolesIdProvinceLevel(@Param("rolesId") Integer rolesId);

    /**
     * 见上一个接口
     * @param rolesId
     * @return
     */
    @Query(value = "select u.id from user as u where FIND_IN_SET(:rolesId,roles) and length(u.dept_level) > 3",nativeQuery = true)
    List<Integer> getOperatorIdByRolesIdCityLevel(@Param("rolesId") Integer rolesId);

    @Query(nativeQuery = true, value = "select * from user,user_roles where user.id = user_roles.user_id and roles = ?1")
    List<UserBean> findAllByRoleId(Integer roleId);

    List<UserBean> findAllByDeptId(Integer DeptId);

    /**
     * 查询某机构下，指定状态的用户
     * @param deptId
     * @param status
     * @return
     */
    List<UserBean> findByDeptIdAndStatus(int deptId,int status);

    List<UserBean> findAllByDeptIdIn(List<Integer> ids);

    /**
     * 查询user表中最新的一条数据的id值
     * @return
     */
    @Query(value = "select max(id) from user",nativeQuery = true)
    Integer findMaxId();

    @Query(value = "select count(address) from user where address = :address", nativeQuery = true)
    int countByAddress(@Param("address") String address);

    UserBean findByAddress(String address);
}
