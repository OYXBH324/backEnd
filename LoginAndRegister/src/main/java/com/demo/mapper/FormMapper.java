package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.ProcessForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FormMapper extends BaseMapper<ProcessForm> {
    @Results(id="fromMap",value={
            @Result(property = "hod",column = "hod_id",one=@One(select="com.demo.mapper.UserDao.selectById")),
            @Result(property = "route",column = "route"),
            @Result(property = "routeUser",column = "route",one=@One(select="com.demo.mapper.UserDao.selectById")),
            @Result(property = "user",column = "user_id",one=@One(select="com.demo.mapper.UserDao.selectById")),
            @Result(property = "ssdp",column = "ssdp_id",one=@One(select="com.demo.mapper.SSDPDao.selectById")),
            @Result(property = "department",column="department_id",
            one=@One(select="com.demo.mapper.DepartmentDao.selectById"))
    })
    @Select("select * from tb_from")
    List<ProcessForm> selectForm();

    @Select("select count(*) from tb_form where target_time < finish_time and completed = 1")
    public Integer selectLater();

    @Select("select count(*) from tb_form where target_time >finish_time and completed = 1")
    public Integer selectWithin();
}
