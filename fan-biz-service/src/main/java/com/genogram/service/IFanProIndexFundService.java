package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProIndexFund;

/**
 * @author keriezhang
 * @date 2016/10/31
 */
public interface IFanProIndexFundService extends IService<ProIndexFund> {

    /**
     * 查询基金金额
     *
     * @param siteId 网站ID
     * @return
     */
    ProIndexFund getProIndexFund(Integer siteId);

    /**
     * 新增 基金金额
     *
     * @param proIndexFund
     * @return
     */
    Boolean insertProIndexFund(ProIndexFund proIndexFund);
}
