package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 11214
 * @since 2022/11/18 19:20
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TableName("sys_role")
public class Role {
    private Integer id;
    private String name;
    private String description;
    @TableField(exist = false)
    private List<Privilege> privileges;
}
