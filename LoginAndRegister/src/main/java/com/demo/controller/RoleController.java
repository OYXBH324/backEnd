package com.demo.controller;

import com.demo.Utils.JwtUtil;
import com.demo.filter.UserHolder;
import com.demo.model.po.system.Page;
import com.demo.model.po.system.User;
import com.demo.result.R;
import com.demo.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserHolder userHolder;

    //暂时不加权限
    @PostMapping("/userInfo")
    public R getUserInfo(HttpServletRequest request, @RequestBody String token){
        User user = JwtUtil.parseUser(token);
        if(user==null){
            return R.error("get token unsuccessfully");
        }
        User userByEmail = roleService.getUserByEmail(user.getEmail());
        if(userByEmail==null){
            User user1 = userHolder.getUserByToken(token);
            if(user1==null){
                return R.error("见了鬼了");
            }
            return R.ok("OK").setData(user1);
        }
        return R.ok("OK").setData(userByEmail);
    }

    //@Privilege("self")
    @PostMapping("/pages")
    public R getUserInfo(HttpServletRequest request,@RequestBody Map<String,String> map){
        List<Page> pages = roleService.getUserPages(map.get("email"));
        return R.ok("OK").setData(pages);
    }
}
