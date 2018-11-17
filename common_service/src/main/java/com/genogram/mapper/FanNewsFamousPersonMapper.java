package com.genogram.mapper;

import com.genogram.entity.FanNewsFamousPerson;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.FamilyPersonVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface FanNewsFamousPersonMapper extends BaseMapper<FanNewsFamousPerson> {

    /**
     * 省级首页县级推荐人物查询
     * @param map 查询条件
     * @return
     */
    List<FamilyPersonVo> getRecommendFigure(Map map);
}
