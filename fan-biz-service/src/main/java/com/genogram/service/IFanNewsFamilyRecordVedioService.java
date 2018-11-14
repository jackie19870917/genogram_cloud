package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVedioVo;

/**
 * 记录家族视频上传-视频概要 服务类
 */
public interface IFanNewsFamilyRecordVedioService extends IService<FanNewsFamilyRecordVedio> {
    /**
     *
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyRecordVedioVo> getFamilyVeidoPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
}
