package com.genogram.service;

import com.genogram.entity.ProSysSite;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 开通省级网站表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProSysSiteService extends IService<ProSysSite> {

    /**
     * 网站信息
     *
     * @param siteId 网站ID
     * @return
     */
    ProSysSite getProSysSite(Integer siteId);
}
