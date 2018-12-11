package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserNewsInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个人日志 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface AllUserNewsInfoMapper extends BaseMapper<AllUserNewsInfo> {

    /**
     * 个人日志
     *
     * @param mapPage
     * @param map
     * @return
     */
    List<AllUserNewsInfo> getAllUserNewsInfo(Page<AllUserNewsInfo> mapPage, Map map);
}
