package com.qintong.yygh.hosp.service.impl;

import com.qintong.yygh.common.exception.YyghException;
import com.qintong.yygh.common.result.ResultCodeEnum;
import com.qintong.yygh.hosp.mapper.HospitalSetMapper;
import com.qintong.yygh.hosp.service.HospitalSetService;
import com.qintong.yygh.model.hosp.HospitalSet;
import com.qintong.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author xujinfengxu
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    /**
     * 根据传递过来医院编码，查询数据库，查询签名
     * @param hoscode
     * @return
     */
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }

    /**
     *
     * @param hoscode
     * @return
     */
    @Override
    public SignInfoVo getSignInfoVo(String hoscode) {
//        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
//        wrapper.eq("hoscode",hoscode);
//        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
//        if(null == hospitalSet) {
//            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);
//        }
//        SignInfoVo signInfoVo = new SignInfoVo();
//        signInfoVo.setApiUrl(hospitalSet.getApiUrl());
//        signInfoVo.setSignKey(hospitalSet.getSignKey());
//        return signInfoVo;
        return null;
    }

}
