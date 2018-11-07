package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVedioVo;

/**
 * <p>
 * 记录家族视频上传-视频概要 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsFamilyRecordVedioService extends IService<FanNewsFamilyRecordVedio> {
    Page<FamilyRecordVedioVo> getFamilyVeidoPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
}
