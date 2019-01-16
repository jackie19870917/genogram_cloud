package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFundDrowing;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.IndexFundDrowingVo;

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
     *
     * @param fanIndexFundDrowing
     * @return
     */
    Boolean insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing);

    /**
     * 修改线上提现状态
     *
     * @param fanIndexFundDrowing
     * @return
     */
    Boolean updateFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing);

    /**
     * 线上提现记录
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer siteId, Integer pageNo, Integer pageSize);

    /**
     * 线上提现记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer pageNo, Integer pageSize);
}
