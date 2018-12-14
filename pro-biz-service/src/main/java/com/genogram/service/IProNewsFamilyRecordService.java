package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.ProNewsFamilyRecord;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProFamilyRecordVo;

/**
 * 省级-记录家族-家族动态,家族通告文章表 服务类
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsFamilyRecordService extends IService<ProNewsFamilyRecord> {
    /**
     * 前台查询
     *
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProFamilyRecordVo> getProFamilyRecordPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 省级记录家族详情
     *
     * @param id 主键
     * @return
     */
    NewsDetailVo getProFamilyRecord(Integer id);

    /**
     * 省级后台新增
     *
     * @param proNewsFamilyRecord
     * @param fileName
     * @param filePath
     * @return
     */
    boolean addOrUpdateRecord(ProNewsFamilyRecord proNewsFamilyRecord, String fileName, String filePath);

    /**
     * 记录家族删除
     *
     * @param id
     * @param status
     * @return
     */
    Boolean deleteProRecordById(Integer id, int status, Integer userId);

    /**
     * 增加查看数
     *
     * @param id
     */
    void addVisitNum(Integer id);



}
