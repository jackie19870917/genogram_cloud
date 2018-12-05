package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserVideos;
import com.genogram.mapper.AllUserVideosMapper;
import com.genogram.service.IAllUserVideosService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 个人视频 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@Service
public class AllUserVideosServiceImpl extends ServiceImpl<AllUserVideosMapper, AllUserVideos> implements IAllUserVideosService {

    @Override
    public Page<AllUserVideos> getAllUserVideosPage(Integer userId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<AllUserVideos> wrapper = new EntityWrapper<>();
        wrapper.eq("create_user", userId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo,pageSize),wrapper);
    }
}
