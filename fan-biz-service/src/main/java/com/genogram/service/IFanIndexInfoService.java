package com.genogram.service;

import com.genogram.entity.FanIndexInfo;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FanIndexInfoVo;

/**
 * <p>
 * 联谊会网站:图腾;简介;宣言 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexInfoService extends IService<FanIndexInfo> {

    //联谊会图腾,宣言,公告
    FanIndexInfo getFanIndexInfo(Integer siteId);

    //联谊会信息
    FanIndexInfoVo getFanIndexInfoVo(Integer siteId);

    //新增/修改  联谊会图腾,宣言,公告
    Boolean insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo);

}
