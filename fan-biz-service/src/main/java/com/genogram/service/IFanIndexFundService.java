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

    /**
     * 查询基金金额
     *
     * @param siteId 网站ID
     * @return
     */
    FanIndexFund getFanIndexFund(Integer siteId);

    /**
     * 新增/修改 基金金额
     *
     * @param fanIndexFund
     * @return
     */
    Boolean insertOrUpdateFanIndexFund(FanIndexFund fanIndexFund);

    /**
     * 修改 基金金额
     *
     * @param fanIndexFund
     * @return
     */
    Boolean updateFanIndexFund(FanIndexFund fanIndexFund);

    /**
     * 新增 基金金额
     *
     * @param fanIndexFund
     * @return
     */
    Boolean insertFanIndexFund(FanIndexFund fanIndexFund);
}
