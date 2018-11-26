package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllRegion;
import com.genogram.entity.FanSysSite;

import java.util.List;

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
     * @return
     */
    List<AllRegion> getProvincialSubordinate(Integer siteId);

    /**
     * 省级下属地图查询
     * @param entity 地区ID
     * @return
     */
    List<AllRegion> getRegion(Wrapper<AllRegion> entity);

    /**
     * 省级下属地图联谊会查询
     * @param parentCode
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanSysSite> getSodalityRegion(String parentCode, Integer siteId, Integer pageNo, Integer pageSize);
}
