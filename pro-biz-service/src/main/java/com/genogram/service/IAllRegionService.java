package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllRegion;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IAllRegionService extends IService<AllRegion> {

    /**
     * 省级下属县级官网查询
     * @param siteId  省级网站Id
     */
    void getProvincialSubordinate(Integer siteId);
}
