package com.qintong.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.qintong.yygh.hosp.repository.DepartmentRepository;
import com.qintong.yygh.hosp.service.DepartmentService;
import com.qintong.yygh.model.hosp.Department;
import com.qintong.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author xujinfengxu
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * 添加科室信息
     * @param paramMap
     */
    @Override
    public void save(Map<String, Object> paramMap) {
        // paramMap -> department object
        String jsonString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);

        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        // 判断
        if (!ObjectUtils.isEmpty(departmentExist)){
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        }else{
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }

    }

    /**
     * 查询科室列表
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    @Override
    public Page<Department> findPageDepartment(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {
        // 创建Pageable对象，设置当前页和记录数
        // 0是第一页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 创建Example对象，设置条件
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        Example<Department> example = Example.of(department, matcher);
        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    /**
     * 删除科室
     * @param hoscode
     * @param depcode
     */
    @Override
    public void remove(String hoscode, String depcode) {
        // 根据编号查询信息
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (!ObjectUtils.isEmpty(department)){
            departmentRepository.deleteById(department.getId());
        }
    }
}
