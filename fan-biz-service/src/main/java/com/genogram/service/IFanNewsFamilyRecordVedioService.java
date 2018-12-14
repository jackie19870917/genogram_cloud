package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.NewsDetailVo;

/**
 * 记录家族视频上传-视频概要 服务类
 *
 * @author Administrator
 */
public interface IFanNewsFamilyRecordVedioService extends IService<FanNewsFamilyRecordVedio> {
    /**
     * 前台查询
     *
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyRecordVedioVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 联谊会记录家族视频详情
     *
     * @param id 主键
     * @return
     */
    NewsDetailVo getFamilyVedioRecord(Integer id);

    /**
     * 联谊会记录家族视频详情
     *
     * @param id 主键
     * @return
     */
    FamilyRecordVedioVo getFamilyVedioDetilRecord(Integer id);

    /**
     * 联谊会记录家族后台视频新增
     *
     * @param fanNewsFamilyRecordVedio
     * @param picfileName
     * @param picPath
     * @param vedioFileName
     * @param vedioPath
     * @return
     */
    boolean addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath);

    /**
     * 记录家族视屏删除
     *
     * @param id
     * @param userId
     * @param status
     * @return
     */
    Boolean deleteVedioRecordById(Integer id, int status, Integer userId);
}
