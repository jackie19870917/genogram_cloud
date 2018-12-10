package com.genogram.service;

import com.genogram.entity.ProIndexFund;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 省级-首页-基金-前台后台 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProIndexFundService extends IService<ProIndexFund> {

    /**
     * 查询基金金额
     *
     * @param siteId 网站ID
     * @return
     */
    ProIndexFund getProIndexFund(Integer siteId);

    /**
     * 修改 基金金额
     *
     * @param proIndexFund
     * @return
     */
    Boolean updateProIndexFund(ProIndexFund proIndexFund);
}
