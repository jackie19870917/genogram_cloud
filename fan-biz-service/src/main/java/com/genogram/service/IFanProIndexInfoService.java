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
public interface IFanProIndexInfoService extends IService<ProIndexInfo> {

    /**
     * 省级图腾,宣言,公告
     *
     * @param siteId 网店ID
     * @return
     */
    ProIndexInfo getProIndexInfo(Integer siteId);

    /**
     * 新增  省级信息
     * @param proIndexInfo
     * @return
     */
    Boolean insertProIndexInfo(ProIndexInfo proIndexInfo);
}
