package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProIndexInfo;
import com.genogram.entityvo.IndexInfoVo;

/**
 * <p>
 * 省级网站:图腾;简介;宣言 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProIndexInfoService extends IService<ProIndexInfo> {

    /**
     * 省级图腾,宣言,公告
     *
     * @param siteId 网店ID
     * @return
     */
    ProIndexInfo getProIndexInfo(Integer siteId);

    /**
     * 省级信息
     *
     * @param siteId 网店ID
     * @return
     */
    IndexInfoVo getFanIndexInfoVo(Integer siteId);

    /**
     * 新增/修改  省级信息
     * @param indexInfoVo
     * @return
     */
    Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo);

    /**
     *  删除 省级信息
     * @param proIndexInfo
     * @return
     */
    Boolean deleteProIndexInfo(ProIndexInfo proIndexInfo);
}
