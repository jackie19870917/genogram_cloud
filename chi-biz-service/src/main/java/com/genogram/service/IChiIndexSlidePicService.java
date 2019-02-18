package com.genogram.service;

import com.genogram.entity.ChiIndexSlidePic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 省级网站轮播图 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
public interface IChiIndexSlidePicService extends IService<ChiIndexSlidePic> {

    /**
     * 轮播图查询
     * @param siteId
     * @param status
     * @return
     */
    List<ChiIndexSlidePic> getChiIndexSlidePicBySiteId(Integer siteId,Integer status);
}
