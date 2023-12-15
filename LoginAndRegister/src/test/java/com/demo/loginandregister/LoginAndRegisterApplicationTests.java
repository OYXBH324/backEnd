package com.demo.loginandregister;

import com.demo.model.po.system.Page;
import com.demo.model.po.system.User;
import com.demo.service.impl.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashSet;
import java.util.List;

@SpringBootTest
class LoginAndRegisterApplicationTests {

    @Autowired
    RoleService roleService;
    @Test
    void contextLoads() {
        User userByEmail = roleService.getUserByEmail("86153883@qq.com");
        System.out.println(userByEmail);
    }

    @Test
    void getPages(){
        List<Page> userPages = roleService.getUserPages("86153883@qq.com");
        System.out.println(userPages);
    }

}
