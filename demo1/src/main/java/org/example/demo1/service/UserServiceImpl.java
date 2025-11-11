package org.example.demo1.service;



import org.example.demo1.entity.User;
import org.example.demo1.mapper.UserMapper;
import org.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional  // 添加事务管理
    public boolean createUser(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        int result = userMapper.insert(user);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        // 检查用户是否存在
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        int result = userMapper.update(user);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        // 检查用户是否存在
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        int result = userMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public List<User> searchUsers(User user) {
        return userMapper.findByCondition(user);
    }

    @Override
    public List<User> getUsersByPage(Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;

        int offset = (page - 1) * size;
        return userMapper.findByPage(offset, size);
    }

    @Override
    public int getUserCount() {
        return userMapper.count();
    }

    @Override
    @Transactional
    public boolean batchCreateUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            return false;
        }

        int result = userMapper.batchInsert(users);
        return result == users.size();
    }

    @Override
    @Transactional
    public boolean updateUserStatus(Long id, Integer status) {
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        int result = userMapper.updateStatus(id, status);
        return result > 0;
    }
}