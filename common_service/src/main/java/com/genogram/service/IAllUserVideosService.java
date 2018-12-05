package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserVideos;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserVideos> getAllUserVideosPage(Integer userId, List list, Integer pageNo, Integer pageSize);
}
