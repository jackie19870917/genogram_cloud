package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.ProFamilyPersonVo;

public interface IProNewsFamilyPersionService extends IService<ProNewsFamousPerson> {
    /**
     * 前台查询
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProFamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
    /**
     * 省级家族名人增加查看数
     * @param id
     */
    void addVisitNum(Integer id);
    /**
     *省级家族名人详情查询
     * @param id  文章ID
     * @return
     */
    ProFamilyPersonVo getFamilyFamilyDetail(Integer id);
}
