package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;

/**
 * @author 11214
 * @since 2022/11/18 19:19
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TableName("sys_previlege")
public class Privilege {
    private Integer id;
    private String previlege;
    private String description;

    @TableField(exist = false)
    private LinkedHashSet<Page> pages;
}
