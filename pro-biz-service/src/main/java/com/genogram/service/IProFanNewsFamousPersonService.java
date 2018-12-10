package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;

/**
 * <p>
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IProFanNewsFamousPersonService extends IService<FanNewsFamousPerson> {
    /**
     * 联谊会家族名人增加查看数
     *
     * @param id
     */
    void addVisitNum(Integer id);

    /**
     * 联谊会家族名人详情查询
     *
     * @param id 文章ID
     * @return
     */
    FamilyPersonVo getFamilyFamilyDetail(Integer id);
}
