package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;

public interface IProNewFamilyPersionServices extends IService<ProNewsFamousPerson> {

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
    FamilyPersonVo getFamilyFamilyDetail(Integer id);
}
