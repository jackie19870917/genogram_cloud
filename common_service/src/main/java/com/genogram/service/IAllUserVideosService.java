package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserVideos;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个人视频 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserVideosService extends IService<AllUserVideos> {

    /**
     * 根据用户查询视频
     *
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserVideos> getAllUserVideosPage(Integer userId, List list, Integer pageNo, Integer pageSize);

    /**
     * 新增   视频
     *
     * @param allUserVideos
     * @return
     */
    AllUserVideos insertOrUpdateAllUserVideos(AllUserVideos allUserVideos);

    /**
     * 删除视频
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteAllUserVideos(Integer id, Integer userId);

    /**
     * 视频 详情
     *
     * @param id
     * @return
     */
    AllUserVideos getAllUserVideosById(Integer id);

    /**
     * 个人视频
     *
     * @param mapPage
     * @param map
     * @return
     */
    Page<AllUserVideos> getAllUserVideosList(Page<AllUserVideos> mapPage, Map map);

    /**
     * 个人视频
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<AllUserVideos> getAllUserVideosList(Page<AllUserVideos> page, Wrapper<AllUserVideos> wrapper);
}
