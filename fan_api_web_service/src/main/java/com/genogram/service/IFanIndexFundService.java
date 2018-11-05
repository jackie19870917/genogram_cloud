package com.genogram.service;

import com.genogram.entity.FanIndexFund;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 联谊会-首页-基金-前台后台 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexFundService extends IService<FanIndexFund> {

    //查询基金金额
    FanIndexFund selectBySiteId(Integer siteId);
}
