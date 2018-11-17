package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;

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
     * 联谊会记录家族详情
     * @param id  主键
     * @return
     */
    NewsDetailVo getFamilyRecord(Integer id);

}

