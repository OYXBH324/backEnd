package com.demo.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.model.po.system.Department;
import com.demo.model.po.system.SSDP;
import com.demo.model.po.system.User;
import lombok.Data;

import java.sql.Date;

@Data
@TableName(value = "tb_form", resultMap = "fromMap")
public class ProcessForm {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    @TableField(exist = false)
    private User user;
    private String title;
    private String content;
    private String feedback;
    private Integer approve;
    private Integer performed;
    private Integer completed;
    private Long route;
    @TableField(exist = false)
    private SSDP ssdp;
    private Integer ssdpId;
    private Date createTime;
    private Date targetTime;
    private Date finishTime;
    private String hodName;
    @TableField(exist = false)
    private User hod;
    private Long hodId;
    @TableField(exist = false)
    private Department department;
    private Integer departmentId;
    private Integer type;
    @TableField(exist = false)
    private User routeUser;

    private String fileName;
}
