package com.genogram.service;

import com.genogram.entity.FanIndexInfo;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.IndexInfoVo;

/**
 * <p>
 * 联谊会网站:图腾;简介;宣言 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexInfoService extends IService<FanIndexInfo> {

    /**
     *             联谊会图腾,宣言,公告
     * @param siteId   网店ID
     * @return
     */
    FanIndexInfo getFanIndexInfo(Integer siteId);

    /**
     *             联谊会信息
     * @param siteId   网店ID
     * @return
     */
    IndexInfoVo getFanIndexInfoVo(Integer siteId);

    /**
     *        新增/修改  联谊会图腾,宣言,公告
     * @param indexInfoVo   实体类
     * @return
     */
    Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo);

    /**
     *        删除 联谊会图腾,宣言,公告
     * @param fanIndexInfo
     * @return
     */
    Boolean deleteFanIndexInfo(FanIndexInfo fanIndexInfo);
}
