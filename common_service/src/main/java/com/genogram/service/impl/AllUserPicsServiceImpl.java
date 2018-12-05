package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserPics;
import com.genogram.mapper.AllUserPicsMapper;
import com.genogram.service.IAllUserPicsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 个人照片 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@Service
public class AllUserPicsServiceImpl extends ServiceImpl<AllUserPicsMapper, AllUserPics> implements IAllUserPicsService {

    @Override
    public Page<AllUserPics> getAllUserPicsPage(Integer userId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<AllUserPics> wrapper = new EntityWrapper<>();
        wrapper.eq("create_user", userId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo,pageSize),wrapper);
    }
}
