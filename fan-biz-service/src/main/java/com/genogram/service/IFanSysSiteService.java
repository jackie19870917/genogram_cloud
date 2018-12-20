package com.genogram.service;

import com.genogram.entity.FanSysSite;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 开通联谊会表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanSysSiteService extends IService<FanSysSite> {

    /**
     * 网站信息
     *
     * @param siteId
     * @return
     */
    FanSysSite getFanSysSite(Integer siteId);

    /**
     * 查询网站
     *
     * @param code
     * @return
     */
    FanSysSite getFanSysSiteByCode(String code);
}
