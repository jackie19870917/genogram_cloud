package com.genogram.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.mapper.FanNewsCultureNewsMapper;
import com.genogram.service.IFanNewsCultureNewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族文化文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCultureNewsServiceImpl extends ServiceImpl<FanNewsCultureNewsMapper, FanNewsCultureNews> implements IFanNewsCultureNewsService {

    @Autowired
    private FanNewsCultureNewsMapper fanNewsCultureNewsMapper;
    @Override
    public Page<FamilyCultureVo> familyCulture(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        Page<FamilyCultureVo> mapPage = new Page<>(pageNo,pageSize);
        Map requestParam = new HashMap<>();
        requestParam.put("showId",showId);
        requestParam.put("status",status);
        List<FamilyCultureVo> myItems =fanNewsCultureNewsMapper.queryMyItems(mapPage, requestParam);
        mapPage.setRecords(myItems);
        return mapPage;
    }
}
