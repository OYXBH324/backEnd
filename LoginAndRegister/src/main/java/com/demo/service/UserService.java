package com.demo.service;

import com.demo.model.po.LoginUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<LoginUser> {

    LoginUser getByNameAndPassword(String name, String password);

    LoginUser getByName(String name);

    void addUser(LoginUser loginUser);
}
