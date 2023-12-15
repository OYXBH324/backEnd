package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_department")
public class Department {
    private Integer id;
    private String departmentName;
}
