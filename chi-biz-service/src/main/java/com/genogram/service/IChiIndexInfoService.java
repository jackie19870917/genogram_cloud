package com.genogram.service;

import com.genogram.entity.ChiIndexInfo;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.IndexInfoVo;

/**
 * <p>
 * 省级网站:图腾;简介;宣言 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
public interface IChiIndexInfoService extends IService<ChiIndexInfo> {

    /**
     * 全国信息
     *
     * @param siteId
     * @return
     */
    IndexInfoVo getIndexInfoVoBySiteId(Integer siteId);

    /**
     * 新增 修改 基本信息
     *
     * @param indexInfoVo
     * @return
     */
    Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo);

    /**
     * 删除 基本信息
     *
     * @param chiIndexInfo
     * @return
     */
    Boolean deleteChiIndexInfo(ChiIndexInfo chiIndexInfo);
}
