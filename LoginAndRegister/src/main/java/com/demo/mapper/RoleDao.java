package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.system.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    @Select("select previlege_id from sys_r_p where role_id = #{id}")
    List<Integer> selectPIds(Integer id);

    @Select("select * from sys_role where id = #{id}")
    Role selectById(Integer id);
}
