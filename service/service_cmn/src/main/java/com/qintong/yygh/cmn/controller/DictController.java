package com.qintong.yygh.cmn.controller;

import com.qintong.yygh.cmn.service.DictService;
import com.qintong.yygh.common.result.Result;
import com.qintong.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xujinfengxu
 */
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    private DictService dictService;

    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    /**
     * 根据数据id查询子数据列表
     */
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }
}
