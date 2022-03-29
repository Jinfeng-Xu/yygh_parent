package com.qintong.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qintong.yygh.common.utils.MD5;
import com.qintong.yygh.hosp.service.HospitalSetService;
import com.qintong.yygh.model.hosp.Hospital;
import com.qintong.yygh.model.hosp.HospitalSet;
import com.qintong.yygh.vo.hosp.HospitalSetQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.qintong.yygh.common.result.*;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * @author xujinfengxu
 */
@RestController
@ComponentScan(basePackages = "com.qintong")
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    private HospitalSetService hospitalSetService;

    @Autowired
    public HospitalSetController(HospitalSetService hospitalSetService) {
        this.hospitalSetService = hospitalSetService;
    }

    /**
     * 查询医院设置表里所有信息
     */
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 逻辑删除操作
     */
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable("id") Long id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 条件查询带分页
     */
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname", hosname);
        }
        if (!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode", hoscode);
        }
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);

        return Result.ok(hospitalSetPage);
    }

    /**
     * 添加医院设置
     */
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        // 设置状态 1 可用 0 不可用
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey( MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));

        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id获取医院设置
     */
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     */
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean update = hospitalSetService.updateById(hospitalSet);
        if (update){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 批量删除医院设置
     */
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    /**
     * 医院设置锁定和解锁
     */
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status){
        // 根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    /**
     * 发送签名密钥
     */
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
        // 根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        // TODO 发送短信
        return Result.ok();
    }
}
