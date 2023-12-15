package com.demo.filter;

import com.demo.Utils.JwtUtil;
import com.demo.model.po.system.User;
import com.demo.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserHolder {
    @Autowired
    RoleService roleService;
    private HashMap<String, User> userMap = new HashMap<>();

    public User getUserByToken(String token){
        if(userMap.containsKey(token)){
            return userMap.get(token);
        }
        User user = getEmailFromToken(token);
        userMap.put(token,user);
        return user;
    }

    private User getEmailFromToken(String token){
        User user = JwtUtil.parseUser(token);
        User haveUser = roleService.getUserByEmail(user.getEmail());
        if(haveUser!=null){
            return haveUser;
        }else{
            roleService.addUser(user);
            return roleService.getUserByEmail(user.getEmail());
        }
    }
}
