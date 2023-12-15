package com.demo.model.po.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 后勤部门
 */
@Data
@TableName("tb_ssdp")
public class SSDP {
    private Integer id;
    private String ssdpName;
    private String work;
}
