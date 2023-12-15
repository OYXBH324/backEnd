package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.po.ProcessForm;

import java.util.List;
import java.util.Map;

public interface FormService {

    boolean createForm(ProcessForm processForm);

    int updateSelfForm(ProcessForm processForm);

    int deleteSelfForm(int id);

    Page<ProcessForm> getAllForm(int page);

    Page<ProcessForm> searchForm(int page, Map<String,Object> param);


    int markAsCompleted(int id,int userId);

    int markAsPerformed(int id);

    /**
     * approve reject forms
     * @param body
     * @return
     */
    int approveOrRejectForms(int id, int status);
}
