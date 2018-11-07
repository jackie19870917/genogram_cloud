package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousPerson;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVo;

/**
 * <p>
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsFamousPersonService extends IService<FanNewsFamousPerson> {

    Page<FamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
}
