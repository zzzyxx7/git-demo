package org.example.demo1.service;


import org.example.demo1.entity.User;
import java.util.List;

public interface UserService {

    /**
     * 获取所有用户
     */
    List<User> getAllUsers();

    /**
     * 根据ID获取用户
     */
    User getUserById(Long id);

    /**
     * 根据用户名获取用户
     */
    User getUserByUsername(String username);

    /**
     * 创建用户
     */
    boolean createUser(User user);

    /**
     * 更新用户
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 搜索用户
     */
    List<User> searchUsers(User user);

    /**
     * 分页查询用户
     */
    List<User> getUsersByPage(Integer page, Integer size);

    /**
     * 获取用户总数
     */
    int getUserCount();

    /**
     * 批量创建用户
     */
    boolean batchCreateUsers(List<User> users);

    /**
     * 更新用户状态
     */
    boolean updateUserStatus(Long id, Integer status);
}
