package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsIndustry;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;

/**
 * <p>
 * 联谊会-家族产业 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsIndustryService extends IService<FanNewsIndustry> {

    /**
     *联谊会家族产业查询
     * @param showId 显示位置Id
     * @param status 状态
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @param type 每页显示条数
     * @return
     */
    Page<FamilyIndustryVo> getFamilyIndustryPage(Integer showId, List<Integer> status, Integer pageNo, Integer pageSize, Integer type);

    /**
     * 联谊会家族产业各个产业的详情
     * @param showId  显示位置Id
     * @param id  主键
     * @return
     */
    NewsDetailVo getFamilyIndustryDetail(Integer showId, Integer id);
}
