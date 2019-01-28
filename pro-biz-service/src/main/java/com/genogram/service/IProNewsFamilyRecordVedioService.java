package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsFamilyRecordVedio;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVideoVo;
import com.genogram.entityvo.NewsDetailVo;

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
     *
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyRecordVideoVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

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
    FamilyRecordVideoVo getFamilyVedioDetilRecord(Integer id);

    /**
     * 联谊会记录家族后台视频新增
     *
     * @param proNewsFamilyRecordVedio
     * @param picfileName
     * @param picPath
     * @param vedioFileName
     * @param vedioPath
     * @return
     */
    boolean addOrUpdateVedioRecord(ProNewsFamilyRecordVedio proNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath);

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
