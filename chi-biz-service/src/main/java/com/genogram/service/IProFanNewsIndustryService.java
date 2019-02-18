package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;

/**
 * <p>
 * 联谊会-家族产业 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IProFanNewsIndustryService extends IService<FanNewsIndustry> {

    /**
     * 联谊会家族产业各个产业的详情
     *
     * @param id 主键
     * @return
     */
    IndustryDetailVo getFamilyIndustryDetail(Integer id);

    /**
     * 联谊会家族产业前台增加查看数
     *
     * @param id 主键
     */
    void addVisitNum(Integer id);
}
