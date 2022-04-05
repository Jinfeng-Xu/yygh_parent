package com.qintong.yygh.hosp.service;

import com.qintong.yygh.model.hosp.Department;
import com.qintong.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author xujinfengxu
 */
public interface DepartmentService {
    /**
     * 添加科室信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询科室列表
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    Page<Department> findPageDepartment(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo);

    /**
     * 删除科室
     * @param hoscode
     * @param depcode
     */
    void remove(String hoscode, String depcode);
}
