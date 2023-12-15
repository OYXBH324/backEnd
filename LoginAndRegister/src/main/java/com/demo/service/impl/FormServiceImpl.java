package com.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.mapper.DepartmentDao;
import com.demo.mapper.FormMapper;
import com.demo.mapper.SSDPDao;
import com.demo.mapper.UserDao;
import com.demo.model.po.ProcessForm;
import com.demo.model.po.system.Department;
import com.demo.model.po.system.SSDP;
import com.demo.model.po.system.User;
import com.demo.service.FormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class FormServiceImpl {

    private static final int pageSize = 2000;
    @Autowired
    FormMapper formMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    SSDPDao ssdpDao;

    public boolean createForm(ProcessForm processForm) {
        processForm.setRoute(processForm.getHodId());
        int insert = formMapper.insert(processForm);
        return insert > 0;
    }


    public int updateSelfForm(ProcessForm processForm) {
        return formMapper.updateById(processForm);
    }


    public int deleteSelfForm(int id) {
        return formMapper.deleteById(id);
    }


    public Page<ProcessForm> getAllForm(int page){
        return formMapper.selectPage(new Page<>(page, pageSize), null);
    }

    public List<User> getAllStaff(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",2);
        List<User> users = userDao.selectList(queryWrapper);
        return users;
    }
    public Page<ProcessForm> searchForm(int page, Map<String, Object> params) {
        QueryWrapper<ProcessForm> wrapper =new QueryWrapper<>();
        if(params.containsKey("userId")){
            wrapper.eq("user_id",params.get("userId"));
        }
        if(params.containsKey("approve1")&&params.containsKey("approve2")){
            wrapper.and(o->o.eq("approve",1)
                    .or().eq("approve",0));
        }else if(params.containsKey("approve")){
            wrapper.eq("approve",params.get("approve"));
        }
        if(params.containsKey("performed")){
            wrapper.eq("performed",params.get("performed"));
        }
        if(params.containsKey("completed")){
            wrapper.eq("completed",params.get("completed"));
        }
        if(params.containsKey("route")){
            wrapper.eq("route",params.get("route"));
        }
        return formMapper.selectPage(new Page<>(page, pageSize), wrapper);
    }

    public int markAsCompleted(long id,long userId) {
        ProcessForm processForm = new ProcessForm();
        processForm.setId(id);
        processForm.setCompleted(1);
        return formMapper.updateById(processForm);
    }

    public int markAsPerformed(long id) {
        ProcessForm processForm = new ProcessForm();
        processForm.setId(id);
        processForm.setPerformed(1);
        processForm.setFinishTime(new Date(System.currentTimeMillis()));
        return formMapper.updateById(processForm);
    }

    public int approveOrRejectForms(Long id, int status,String feedback,Long route) {
        ProcessForm form1 = formMapper.selectById(id);
        if(status==-1){
            ProcessForm processForm = new ProcessForm();
            processForm.setId(id);
            processForm.setApprove(status);
            processForm.setFeedback(feedback);
            processForm.setRoute(form1.getUser().getId());
            return formMapper.updateById(processForm);
        }else{
            if(form1.getApprove()==0){
                Integer dpId = form1.getSsdp().getId();
                QueryWrapper<User>q = new QueryWrapper<>();
                q.eq("department_id",dpId);
                q.eq("role",4);
                List<User> users = userDao.selectList(q);
                if(users.size()>0){
                    form1.setRoute(users.get(0).getId());
                }else{
                    q=new QueryWrapper<>();
                    q.eq("role",5);
                    users = userDao.selectList(q);
                    if(users.size()>0) form1.setRoute(users.get(0).getId());
                    else form1.setRoute(form1.getUser().getId());
                }
                form1.setApprove(1);
            }
            else if(form1.getApprove()==1){
                form1.setApprove(2);
                form1.setRoute(route);
            }
            form1.setFeedback(feedback);
        }
        return formMapper.updateById(form1);
    }

    public JSONObject statistic(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("within",formMapper.selectWithin());
        jsonObject.put("later",formMapper.selectLater());
        jsonObject.put("total",formMapper.selectCount(null));
        return jsonObject;
    }

    public List<Department> getAllDepartment(){
        return departmentDao.selectList(null);
    }

    public List<SSDP> getAllSsdp(){
        return ssdpDao.selectList(null);
    }

    public User getHod(int id){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id",id);
        queryWrapper.eq("role",3);
        List<User> users = userDao.selectList(queryWrapper);
        if(users.size()<1){
            return new User();
        }else{
            return users.get(0);
        }
    }
}
