package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecordVideo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 省级记录家族视频上传-视频概要 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
public interface IChiNewsFamilyRecordVideoService extends IService<ChiNewsFamilyRecordVideo> {

    /**
     * 全国记录家族视频新增
     *
     * @param chiNewsFamilyRecordVideo
     * @param userLogin
     * @return
     */
    Boolean addOrUpdateRecordVideo(ChiNewsFamilyRecordVideo chiNewsFamilyRecordVideo, AllUserLogin userLogin);
}
