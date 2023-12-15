package com.demo.service.impl;

import com.demo.mapper.PageDao;
import com.demo.mapper.PrevilegeDao;
import com.demo.mapper.RoleDao;
import com.demo.mapper.UserDao;
import com.demo.model.po.system.Page;
import com.demo.model.po.system.Privilege;
import com.demo.model.po.system.Role;
import com.demo.model.po.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;


@Service
public class RoleService {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PrevilegeDao previlegeDao;
    @Autowired
    PageDao pageDao;
    public User getUserByEmail(String email){
        User user = userDao.selectByEmail(email);
        if(user==null){
            return null;
        }
        user.getRole().setPrivileges(getPrivelegeByRole(user.getRole()));
        return user;
    }

    public List<Page> getUserPages(String email){
        User userByEmail = getUserByEmail(email);
        LinkedHashSet<Page> pages = new LinkedHashSet<>();
        for(Privilege privilege:userByEmail.getRole().getPrivileges()){
            pages.addAll(privilege.getPages());
        }
        ArrayList<Page> resPages = new ArrayList<>();
        resPages.addAll(pages);
        resPages.sort(new Comparator<Page>() {
            @Override
            public int compare(Page o1, Page o2) {
                return o1.getOrder()-o2.getOrder();
            }
        });
        return resPages;
    }

    public Integer addUser(User user){
        return userDao.insert(user);
    }

    private List<Privilege> getPrivelegeByRole(Role role){
        List<Integer> privelegeIds = roleDao.selectPIds(role.getId());
        List<Privilege> privileges = new ArrayList<>();
        for(Integer id : privelegeIds){
            Privilege privilege = previlegeDao.selectById(id);
            privilege.setPages(getPagesByP(id));
            privileges.add(privilege);
        }
        return privileges;
    }

    private LinkedHashSet<Page> getPagesByP(Integer id){
        List<Integer> integers = previlegeDao.selectPageIdListByPrevilege(id);
        LinkedHashSet<Page> pages = new LinkedHashSet<>();
        for(Integer pageId: integers){
            Page page = pageDao.selectById(pageId);
            pages.add(page);
        }
        return pages;
    }

}
