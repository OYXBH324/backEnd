package com.demo.filter;


import com.demo.model.po.system.User;


public class SecurityContext {
    private User user;

    public SecurityContext() {
    }

    public SecurityContext(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
