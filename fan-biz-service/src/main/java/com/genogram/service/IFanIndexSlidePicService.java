package com.genogram.service;

import com.genogram.entity.FanIndexSlidePic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 联谊网站轮播图 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexSlidePicService extends IService<FanIndexSlidePic> {

    /**
     * 轮播图查询
     *
     * @param siteId 网站ID
     * @param status 状态
     * @return
     */
    List<FanIndexSlidePic> getFanIndexSlidePicListBySiteId(Integer siteId, List status);

    /**
     * 新增/修改  轮播图
     *
     * @param fanIndexSlidePic
     * @return
     */
    Boolean insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic);

    /**
     * 逻辑删除  轮播图
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteFanIndexSlidePic(Integer id, Integer userId);

    /**
     * 新增  轮播图
     *
     * @param fanIndexSlidePic
     * @return
     */
    Boolean insertFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic);
}
