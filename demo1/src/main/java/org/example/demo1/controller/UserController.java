package org.example.demo1.controller;

import org.example.demo1.entity.User;
import org.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("code", 200);
            response.put("message", "成功");
            response.put("data", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "用户不存在");
            return ResponseEntity.status(404).body(response);
        }
    }

    /**
     * 创建用户
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        try {
            boolean result = userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("code", 200);
                response.put("message", "创建成功");
                response.put("data", user.getId());
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 500);
                response.put("message", "创建失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            boolean result = userService.updateUser(user);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("code", 200);
                response.put("message", "更新成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 500);
                response.put("message", "更新失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            boolean result = userService.deleteUser(id);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("code", 200);
                response.put("message", "删除成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 500);
                response.put("message", "删除失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 搜索用户
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer age) {

        User searchCondition = new User();
        searchCondition.setUsername(username);
        searchCondition.setEmail(email);
        searchCondition.setAge(age);

        List<User> users = userService.searchUsers(searchCondition);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getUsersByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<User> users = userService.getUsersByPage(page, size);
        int total = userService.getUserCount();

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", users);
        response.put("pagination", Map.of(
                "page", page,
                "size", size,
                "total", total,
                "pages", (int) Math.ceil((double) total / size)
        ));
        return ResponseEntity.ok(response);
    }
}