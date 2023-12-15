package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.po.system.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PrevilegeDao extends BaseMapper<Privilege> {
    @Select("select page_id from sys_p_page where previlege_id = #{previlegeId} ")
    List<Integer> selectPageIdListByPrevilege(Integer previlegeId);
}
