package com.genogram.mapper;

import com.genogram.entity.FanSysRecommend;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.CommonRecommendVo;
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
     * 省级首页县级自动推荐文章查询
     *
     * @param map 查询条件
     * @return
     */
    List<IndustryDetailVo> getIndexRecommend(Map map);

    /**
     * 省级首页县级手动推荐文章查询
     *
     * @param map
     * @return
     */
    List<CommonRecommendVo> getManualRecommend(Map map);

    /**
     * 县级手动联谊会推荐
     *
     * @param map
     * @return
     */
    List<CommonRecommendVo> getCountyManualRecommend(Map map);

    /**
     * 省级后台设置手动推荐到全国
     *
     * @param map
     * @return
     */
    List<CommonRecommendVo> getManuaRecommendNationwide(Map map);
}
