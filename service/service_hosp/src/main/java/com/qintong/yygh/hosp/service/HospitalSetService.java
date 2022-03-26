package com.qintong.yygh.hosp.service;

import com.qintong.yygh.model.hosp.HospitalSet;
import com.qintong.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xujinfengxu
 */
public interface HospitalSetService extends IService<HospitalSet> {

    /**
     * 根据传递过来医院编码，查询数据库，查询签名
     * @param hoscode
     * @return
     */
    String getSignKey(String hoscode);

    /**
     * 获取医院签名信息
     * @param hoscode
     * @return
     */
    SignInfoVo getSignInfoVo(String hoscode);
}
