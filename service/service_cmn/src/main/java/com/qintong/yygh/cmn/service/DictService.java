package com.qintong.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qintong.yygh.model.cmn.Dict;
import com.qintong.yygh.model.hosp.HospitalSet;
import com.qintong.yygh.vo.order.SignInfoVo;

import java.util.List;

/**
 * @author xujinfengxu
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据数据id查询子数据列表
     * @param id
     * @return
     */
    List<Dict> findChildData(Long id);
}
