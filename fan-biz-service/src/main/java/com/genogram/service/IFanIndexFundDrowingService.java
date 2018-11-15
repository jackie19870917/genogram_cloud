package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFundDrowing;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 线上提现 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexFundDrowingService extends IService<FanIndexFundDrowing> {

    /**
     * 新增线上提现记录
     * @param fanIndexFundDrowing
     * @return
     */
    Boolean insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing);

    /**
     * 线上提现记录
     * @param siteId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanIndexFundDrowing> getFanIndexFundDrowingPage(Integer siteId, List list,Integer pageNo,Integer pageSize);
}
