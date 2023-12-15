package com.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.filter.Privilege;
import com.demo.model.po.ProcessForm;
import com.demo.model.po.system.Department;
import com.demo.model.po.system.SSDP;
import com.demo.model.po.system.User;
import com.demo.result.R;
import com.demo.service.impl.FormServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    FormServiceImpl formService;


    @Privilege("self")
    @PostMapping("/statistics")
    public R statistic(HttpServletRequest request,@RequestBody String body){
        return R.ok().setData(formService.statistic());
    }

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

//    @PostMapping("/upload")
//    public R handleFileUpload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return R.error("Please select a file to upload.");
//        }
//
//        try {
//            Path rootPath = Paths.get(resourceLoader.getResource("classpath:").getURI());
//            String relativePath = "uploads" + File.separator + file.getOriginalFilename();
//            Path destinationPath = rootPath.resolve(relativePath);
//            Files.createDirectories(destinationPath.getParent());
//            file.transferTo(destinationPath.toFile());
//            String filePath = destinationPath.toString();
//            String[] split = filePath.split("/");
//            String fileName = split[split.length-1];
//            return R.ok(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return R.error(500,"Failed to upload the file.");
//        }
//    }
//
//    @PostMapping("/download")
//    public R downloadFile(@RequestBody JSONObject object) {
//        String fileName = object.getString("fileName");
//        try {
//            Path rootPath = Paths.get(resourceLoader.getResource("classpath:").getURI());
//            String relativePath = "uploads" + File.separator + fileName;
//            Path destinationPath = rootPath.resolve(relativePath);
//
//            Resource resource = new UrlResource(destinationPath.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                byte[] fileContent = IOUtils.toByteArray(resource.getInputStream());
//                String base64Content = Base64.getEncoder().encodeToString(fileContent);
//                return R.ok(base64Content);
//            } else {
//                return R.error(HttpStatus.NOT_EXTENDED.value(), "文件不存在");
//            }
//        } catch (IOException e) {
//            log.error("Failed to download file: " + fileName, e);
//            return R.error();
//        }
//    }
    @PostMapping("/upload")
    public R handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("Please select a file to upload.");
        }

        try {
            // 获取 uploads 文件夹的绝对路径
            Path rootPath = Paths.get("/Users/shenning/Desktop/app/LoginAndRegister/target/classes");

            // 构建相对路径
            String relativePath = "uploads"+ File.separator + file.getOriginalFilename();

            // 构建目标路径
            Path destinationPath = rootPath.resolve(relativePath);

            // 判断目标路径是否存在，不存在则创建
            if (!Files.exists(destinationPath.getParent())) {
                Files.createDirectories(destinationPath.getParent());
            }

            // 将文件保存到目标路径
            file.transferTo(destinationPath.toFile());

            // 获取保存后的文件名
            String fileName = destinationPath.getFileName().toString();

            return R.ok(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(500, "Failed to upload the file.");
        }
    }

    @PostMapping("/download")
    public R downloadFile(@RequestBody JSONObject object) {
        String fileName = object.getString("fileName");
        try {
            // 获取 uploads 文件夹的绝对路径
            Path rootPath = Paths.get("/Users/shenning/Desktop/app/LoginAndRegister/target/classes");

            // 构建相对路径
            String relativePath = "uploads"+File.separator + fileName;

            // 构建目标路径
            Path destinationPath = rootPath.resolve(relativePath);

            // 创建资源对象
            Resource resource = new UrlResource(destinationPath.toUri());

            // 判断资源是否存在且可读
            if (resource.exists() && resource.isReadable()) {
                // 读取文件内容并转换为Base64编码
                byte[] fileContent = IOUtils.toByteArray(resource.getInputStream());
                String base64Content = Base64.getEncoder().encodeToString(fileContent);
                return R.ok(base64Content);
            } else {
                return R.error(HttpStatus.NOT_FOUND.value(), "文件不存在");
            }
        } catch (IOException e) {
            log.error("Failed to download file: " + fileName, e);
            return R.error();
        }
    }

    /**
     * 我提交的表单
     */
    @Privilege("self")
    @PostMapping("/getSelfForm")
    public R selectSelfForm(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        Page<ProcessForm> processFormPage = formService.searchForm(0, params);
        return R.ok("OK").setData(processFormPage);
    }

    @Privilege("self")
    @PostMapping("/updateSelfForm")
    public R updateSelfForm(HttpServletRequest request,@RequestBody String body) {
        ProcessForm processForm = JSONObject.parseObject(body, ProcessForm.class);
        processForm.setUserId(null);
        int i = formService.updateSelfForm(processForm);
        if (i == 1) {
            return R.ok("success");
        } else {
            return R.error("failed");
        }
    }

    @Privilege("self")
    @PostMapping("/deleteSelfForm")
    public R deleteSelfForm(HttpServletRequest request,@RequestBody String body) {
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
        int count = formService.markAsCompleted(json.getLong("id"),json.getObject("user",JSONObject.class)
                .getLong("id"));
        if (count == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @Privilege("self")
    @PostMapping("/getAllForm")
    public R getAllForms(HttpServletRequest request,@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        Page<ProcessForm> page = formService.getAllForm(json.getInteger("page"));
        return R.ok().setData(page);
    }

    @Privilege("self")
    @PostMapping("/getAllStaff")
    public R getStaff(HttpServletRequest request,@RequestBody(required = false) String body){
        List<User> allStaff = formService.getAllStaff();
        return R.ok().setData(allStaff);
    }


    @Privilege("performed")
    @PostMapping("/markAsPerformed")
    public R markAsPerformed(HttpServletRequest request,@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        int count = formService.markAsPerformed(json.getInteger("id"));
        if (count == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * approve reject forms
     *
     * @param body
     * @return
     */
    @Privilege("approve")
    @PostMapping("/approve")
    public R approveForms(HttpServletRequest request,@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        int count = formService.approveOrRejectForms(json.getLong("id"), 1,
                json.getString("feedback"),json.getLong("route"));
        if (count == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @Privilege("approve")
    @PostMapping("/reject")
    public R rejectForms(HttpServletRequest request,@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        int count = formService.approveOrRejectForms(json.getLong("id"),
                -1,json.getString("feedback"),0l);
        if (count == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @Privilege("self")
    @PostMapping("/getAllDepartment")
    public R getAllDepartMent(HttpServletRequest request) {
        List<Department> allDepartment = formService.getAllDepartment();
        return R.ok("ok了").setData(allDepartment);
    }

    @Privilege("self")
    @PostMapping("/getAllSsdp")
    public R getAllSsdp(HttpServletRequest request) {
        List<SSDP> ssdps = formService.getAllSsdp();
        return R.ok("ok").setData(ssdps);
    }

    @Privilege("self")
    @PostMapping("/getHod")
    public R getHod(HttpServletRequest request,@RequestBody String requestBody){
        JSONObject json = JSON.parseObject(requestBody);
        User department = formService.getHod(json.getInteger("departmentId"));
        return R.ok("ok").setData(department);
    }


}
