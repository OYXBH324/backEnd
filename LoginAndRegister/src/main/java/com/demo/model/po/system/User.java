package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 11214
 * @since 2022/11/18 19:16
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("sys_user")
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Boolean enable;
    private Integer departmentId;
    @TableField(exist = false)
    private Department department;

    @JsonIgnore
    private Role role;
    private String email;

    @JsonIgnore
    public Set<String> getPrivilege(){
        Set<String> ps = new HashSet<>();
        for(Privilege item : role.getPrivileges()){
            ps.add(item.getPrevilege());
        }
        return ps;
    }

}
