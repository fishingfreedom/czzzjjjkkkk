package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.PermissionMapper;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据username查询User详细信息
     * @param username
     * @return User
     */
    @Override
    public User selectUserByUsername(String username) {
        //todo user详细信息
        User user = userMapper.selectByUsername(username);
        //1. 如果user为空
        if (user == null) {
            return null;
        }
        //2. 查询user对象的roles集合
        Set<Role> roles = roleMapper.selectRolesByUserId(user.getId());
        user.setRoles(roles);
        for(Role role : roles) {
            //3. 遍历roles，获得每一个role的permissions
            Set<Permission> permissions = permissionMapper.selectPermissionsByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        return user;
    }
}
