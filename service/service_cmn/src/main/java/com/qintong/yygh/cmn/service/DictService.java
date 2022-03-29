package com.qintong.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qintong.yygh.model.cmn.Dict;
import com.qintong.yygh.model.hosp.HospitalSet;
import com.qintong.yygh.vo.order.SignInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 导出数据字典
     * @param response
     */
    void exportDictData(HttpServletResponse response);

    /**
     * 导入数据字典
     * @param file
     */
    void importDictData(MultipartFile file);
}
