package com.qintong.yygh.cmn.service.impl;

//import com.qintong.yygh.common.exception.YyghException;
//import com.qintong.yygh.common.result.ResultCodeEnum;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qintong.yygh.cmn.mapper.DictMapper;
import com.qintong.yygh.cmn.service.DictService;
import com.qintong.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xujinfengxu
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private DictMapper dictMapper;

    @Autowired
    public DictServiceImpl(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    /**
     * 根据数据id查询子数据列表
     * @param id
     * @return
     */
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = dictMapper.selectList(wrapper);
        // 向集合每个dict设置hasChildren
        for (Dict dict : dictList){
            Long dictId =  dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    /**
     * 判断id下面是否有子节点
     */
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
