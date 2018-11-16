package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族文化-字派表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface FanNewsCultureZipaiMapper extends BaseMapper<FanNewsCultureZipai> {

    /**
     * 省级查出各个地区的字派
     * @param mapPage
     * @param map
     * @return
     */
    List<FanNewsCultureZipai> getZipaiRegionPage(Page<FanNewsCultureZipai> mapPage, Map map);

}
