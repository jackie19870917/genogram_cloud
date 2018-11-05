package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entity.FanNewsCultureNews;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.FamilyCultureVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族文化文章表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface FanNewsCultureNewsMapper extends BaseMapper<FanNewsCultureNews> {

    List<FamilyCultureVo> queryMyItems(Page<FamilyCultureVo> mapPage, Map requestParam);
}
