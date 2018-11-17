package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsFamilyRecordVedio;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVedioVo;

/**
 * <p>
 * 省级记录家族视频上传-视频概要 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-17
 */
public interface IProNewsFamilyRecordVedioService extends IService<ProNewsFamilyRecordVedio> {
    /**
     * 前台查询
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyRecordVedioVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
}
