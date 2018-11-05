package com.genogram.service.impl;

import com.genogram.entity.FanIndexFund;
import com.genogram.mapper.FanIndexFundMapper;
import com.genogram.service.IFanIndexFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联谊会-首页-基金-前台后台 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFundServiceImpl extends ServiceImpl<FanIndexFundMapper, FanIndexFund> implements IFanIndexFundService {

    @Autowired
    private FanIndexFundMapper fanIndexFundMapper;

    @Override
    public FanIndexFund selectBySiteId(Integer siteId) {
        return fanIndexFundMapper.selectById(siteId);
    }
}
