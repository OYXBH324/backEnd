package com.demo.filter;


import com.demo.Utils.JwtUtil;
import com.demo.model.po.system.User;
import com.demo.result.R;

import com.demo.service.impl.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 11214
 * @since 2022/11/23 17:26
 */
@Aspect
@Component
@Slf4j
public class PrivilegeAspect {
    @Autowired
    RoleService roleService;

    @Around("@annotation(com.demo.filter.Privilege)&&args(request,..)")
    public Object checkAuthor(ProceedingJoinPoint point,HttpServletRequest request) throws Throwable {
        System.out.println("拦截到了");
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Privilege privilege = method.getAnnotation(Privilege.class);
        Set<String> requiredPrivileges = Arrays.stream(privilege.value()).collect(Collectors.toSet());

        String token = request.getHeader("token");
        User user = roleService.getUserByEmail(JwtUtil.parseUser(token).getEmail());
        if(user.getPrivilege().containsAll(requiredPrivileges)){
            return point.proceed();
        }
        else{
            return R.error(503,"無權限");
        }
    }
//    @Pointcut("@annotation(com.demo.filter.Privilege)")
//    public void privilegePointCut() {
//    }
//
//    @Around("privilegePointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        Method method = ((MethodSignature) point.getSignature()).getMethod();
//        Privilege privilege = method.getAnnotation(Privilege.class);
//        Set<String> requiredPrivileges = Arrays.stream(privilege.value()).collect(Collectors.toSet());
//        User user = roleService
//        Set<String> privileges = user.getRole().getPrivileges().stream().map(com.demo.model.po.system.Privilege::getOperationName)
//                .collect(Collectors.toSet());
//        if (privileges.containsAll(requiredPrivileges)) {
//            return point.proceed();
//        } else {
//            log.warn(user.getUsername() + " 无权限访问接口 " + method.getName());
//            return R.ok("no auth");
//        }
//    }
}
