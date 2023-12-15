package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.system.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Results({
            @Result(property = "role",column = "role",one = @One(select="com.demo.mapper.RoleDao.selectById")),
            @Result(property = "department",column = "department_id",one =
            @One(select="com.demo.mapper.DepartmentDao.selectById")),
            @Result(property = "ssdp",column = "ssdp_id",one=@One(select="com.demo.mapper.SSDPDao.selectById"))
    })
    @Select("select * from sys_user where email = #{email}")
    User selectByEmail(String email);
}
