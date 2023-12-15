package com.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.UserMapper;
import com.demo.model.po.LoginUser;
import com.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, LoginUser> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public LoginUser getByNameAndPassword(String name, String password) {
        LambdaQueryWrapper<LoginUser> wrapper = new LambdaQueryWrapper<LoginUser>();
        wrapper.eq(LoginUser::getPassword,password);
        wrapper.eq(LoginUser::getUsername, name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public LoginUser getByName(String name) {
        LambdaQueryWrapper<LoginUser> wrapper = new LambdaQueryWrapper<LoginUser>();
        wrapper.eq(LoginUser::getUsername, name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void addUser(LoginUser loginUser) {
        userMapper.insert(loginUser);
    }
}
