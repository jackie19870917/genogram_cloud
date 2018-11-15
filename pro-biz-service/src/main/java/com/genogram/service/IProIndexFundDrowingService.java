package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProIndexFundDrowing;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.IndexFundDrowingVo;

/**
 * <p>
 * 省级线上提现 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-15
 */
public interface IProIndexFundDrowingService extends IService<ProIndexFundDrowing> {

    /**
     * 新增线上提现记录
     * @param proIndexFundDrowing
     * @return
     */
    Boolean insertProIndexFundDrowing(ProIndexFundDrowing proIndexFundDrowing);

    /**
     * 线上提现记录
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer siteId, Integer pageNo, Integer pageSize);

}
