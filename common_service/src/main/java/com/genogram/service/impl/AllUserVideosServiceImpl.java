package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserVideos;
import com.genogram.mapper.AllUserVideosMapper;
import com.genogram.service.IAllUserVideosService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public AllUserVideos insertOrUpdateAllUserVideos(AllUserVideos allUserVideos) {

        this.insertOrUpdate(allUserVideos);

        return allUserVideos;
    }

    @Override
    public Boolean deleteAllUserVideos(Integer id, Integer userId) {

        AllUserVideos allUserVideos = new AllUserVideos();
        allUserVideos.setId(id);
        allUserVideos.setStatus(0);
        allUserVideos.setUpdateTime(DateUtil.getCurrentTimeStamp());
        allUserVideos.setUpdateUser(userId);

        return this.updateById(allUserVideos);
    }

    @Override
    public AllUserVideos getAllUserVideosById(Integer id) {

        return this.selectById(id);
    }
}
