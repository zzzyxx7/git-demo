package org.example.demo1.mapper;

import org.example.demo1.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper  // 重要：告诉 Spring 这是 MyBatis 的 Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据ID查询用户
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     */
    User findByUsername(String username);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 根据ID删除用户
     */
    int deleteById(Long id);

    /**
     * 统计用户数量
     */
    int count();

    /**
     * 分页查询用户
     */
    List<User> findByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据条件查询用户（动态SQL）
     */
    List<User> findByCondition(User user);

    /**
     * 批量插入用户
     */
    int batchInsert(List<User> users);

    /**
     * 更新用户状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}