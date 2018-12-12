package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserVideos;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个人视频 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface AllUserVideosMapper extends BaseMapper<AllUserVideos> {

    /**
     * 个人视频
     *
     * @param mapPage
     * @param map
     * @return
     */
    List<AllUserVideos> getAllUserVideos(Page<AllUserVideos> mapPage, Map map);
}
