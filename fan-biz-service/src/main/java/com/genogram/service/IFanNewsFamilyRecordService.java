package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;

/**
 * <p>
 * 联谊会-记录家族-家族动态,家族通告文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsFamilyRecordService extends IService<FanNewsFamilyRecord> {
    /**
     * 前台查询
     * @param showId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyRecordVo> getFamilyRecordPage(Integer showId, List<Integer> list, Integer pageNo, Integer pageSize);

    /**
     * 联谊会记录家族详情
     * @param id  主键
     * @return
     */
    NewsDetailVo getFamilyRecord(Integer id);

    /**
     * 联谊会记录家族后台新增
     * @param fanNewsRecord
     * @param fileName 文件列表
     * @param filePath 文件路径
     * @param
     * @return
     */
    boolean addOrUpdateRecord(FanNewsFamilyRecord fanNewsRecord, String fileName,String filePath);

    /**
     * 记录家族删除
     * @param id
     * @param status
     * @return
     */
    Boolean deleteRecordById(Integer id, int status);
    /**
     *联谊会记录家族详情查询
     * @param id  文章ID
     * @return
     */
    NewsDetailVo getFamilyRecordDetail(Integer id);

    /**
     * 联谊会家族名人增加查看数
     * @param id
     */
    void addVisitNum(Integer id);
}

