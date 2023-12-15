package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("sys_page")
public class Page {
    private Integer id;
    private String name;
    private String url;
    private String desc;
    private Integer order;
}
