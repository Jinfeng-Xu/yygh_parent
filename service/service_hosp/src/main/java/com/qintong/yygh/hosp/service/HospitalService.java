package com.qintong.yygh.hosp.service;

import com.qintong.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @author xujinfengxu
 */
public interface HospitalService {

    /**
     * 上传医院接口
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 根据医院编号进行查询
     * @param hoscode
     * @return
     */
    Hospital getByHoscode(String hoscode);
}
