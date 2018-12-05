package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserSays;
import com.genogram.mapper.AllUserSaysMapper;
import com.genogram.service.IAllUserSaysService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 个人说说 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@Service
public class AllUserSaysServiceImpl extends ServiceImpl<AllUserSaysMapper, AllUserSays> implements IAllUserSaysService {

    @Override
    public Page<AllUserSays> getAllUserSaysPage(Integer userId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<AllUserSays> wrapper = new EntityWrapper<>();
        wrapper.eq("create_user", userId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo,pageSize),wrapper);
    }
}
