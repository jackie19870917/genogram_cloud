package com.genogram.mapper;

import com.genogram.entity.FanSysRecommend;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会文章推荐表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface FanSysRecommendMapper extends BaseMapper<FanSysRecommend> {

    /**
     * 省级首页县级推荐文章查询
     * @param map 查询条件
     * @return
     */
    List<IndustryDetailVo> getIndexRecommend(Map map);
}
