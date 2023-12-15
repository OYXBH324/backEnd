package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.filter.Privilege;
import com.demo.model.po.ProcessForm;
import com.demo.model.po.system.Department;
import com.demo.model.po.system.User;
import com.demo.result.R;
import com.demo.service.FormService;
import com.demo.service.impl.FormServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 这里存储的是自己操作自己表单的接口
 */
@RestController
@RequestMapping("/form2")
public class SelfFromController {
    @Autowired
    FormServiceImpl formService;

    @Privilege("self")
    @PostMapping("/createForm")
    public R createForm(HttpServletRequest request, @RequestBody String body) {
        ProcessForm processForm = JSONObject.parseObject(body, ProcessForm.class);
        processForm.setApprove(0);
        processForm.setCompleted(0);
        processForm.setPerformed(0);
        formService.createForm(processForm);
        return R.ok("OK");
    }

    /**
     * 我提交的表单
     */
    @Privilege("self")
    @PostMapping("/getSelfForm")
    public R selectSelfForm(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Page<ProcessForm> processFormPage = formService.searchForm(0, params);
        return R.ok("OK").setData(processFormPage);
    }

    @Privilege("self")
    @PostMapping("/updateSelfForm")
    public R updateSelfForm(HttpServletRequest request, @RequestBody String body) {
        ProcessForm processForm = JSONObject.parseObject(body, ProcessForm.class);
        int i = formService.updateSelfForm(processForm);
        if (i == 1) {
            return R.ok("successfully");
        } else {
            return R.error("failed");
        }
    }

    @Privilege("self")
    @PostMapping("/deleteSelfForm")
    public R deleteSelfForm(HttpServletRequest request, @RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        int i = formService.deleteSelfForm(json.getInteger("id"));
        if (i == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @Privilege("self")
    @PostMapping("/markAsCompleted")
    public R markAsCompleted(HttpServletRequest request, @RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        int count = formService.markAsCompleted(json.getInteger("id"), json.getInteger("userId"));
        if (count == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}
