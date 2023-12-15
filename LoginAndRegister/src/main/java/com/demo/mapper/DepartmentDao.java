package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.system.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentDao extends BaseMapper<Department> {
    @Select("select * from tb_department where id = #{id}")
    List<Department> selectById(Integer id);
}
