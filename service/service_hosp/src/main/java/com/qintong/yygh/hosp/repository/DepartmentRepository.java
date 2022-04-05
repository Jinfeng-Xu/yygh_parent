package com.qintong.yygh.hosp.repository;

import com.qintong.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xujinfengxu
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    /**
     * 条件查询
     * @param hoscode
     * @param depcode
     * @return
     */
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
