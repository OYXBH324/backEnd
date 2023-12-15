package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.system.Page;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;


@Mapper
public interface PageDao extends BaseMapper<Page> {
    @Select("select * from sys_page where id = #{id}")
    Page selectById(Integer id);
}
