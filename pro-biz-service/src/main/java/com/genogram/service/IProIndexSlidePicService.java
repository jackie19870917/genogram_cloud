package com.genogram.service;

import com.genogram.entity.ProIndexSlidePic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 省级网站轮播图 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProIndexSlidePicService extends IService<ProIndexSlidePic> {

    /**
     * 轮播图查询
     * @param siteId 网站ID
     * @param list 状态
     * @return
     */
    List<ProIndexSlidePic> getProIndexSlidePic(Integer siteId, List list);

    /**
     * 新增/修改  轮播图
     * @param proIndexSlidePic
     * @return
     */
    Boolean insertOrUpdateProIndexSlidePic(ProIndexSlidePic proIndexSlidePic);

    /**
     * 逻辑删除  轮播图
     * @param id
     * @return
     */
    Boolean deleteProIndexSlidePic(Integer id);
}
