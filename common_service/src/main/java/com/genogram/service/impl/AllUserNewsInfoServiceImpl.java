package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserNewsInfo;
import com.genogram.mapper.AllUserNewsInfoMapper;
import com.genogram.service.IAllUserNewsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 个人日志 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@Service
public class AllUserNewsInfoServiceImpl extends ServiceImpl<AllUserNewsInfoMapper, AllUserNewsInfo> implements IAllUserNewsInfoService {

    @Override
    public Page<AllUserNewsInfo> getAllUserNewsInfoPage(Integer userId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<AllUserNewsInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("create_user", userId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public AllUserNewsInfo insertAllUserNewsInfo(AllUserNewsInfo allUserNewsInfo) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        allUserNewsInfo.setCreateTime(timeStamp);
        allUserNewsInfo.setUpdateTime(timeStamp);

        this.insert(allUserNewsInfo);

        return allUserNewsInfo;
    }
}
