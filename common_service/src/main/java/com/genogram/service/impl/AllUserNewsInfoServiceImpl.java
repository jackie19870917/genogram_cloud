package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserNewsInfo;
import com.genogram.entity.FanSysSite;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.AllUserNewsInfoMapper;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.service.IAllUserNewsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Autowired
    private AllUserLoginMapper allUserLoginMapper;

    @Autowired
    private AllUserNewsInfoMapper allUserNewsInfoMapper;

    @Override
    public Page<AllUserNewsInfo> getAllUserNewsInfoPage(Integer userId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<AllUserNewsInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("create_user", userId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public AllUserNewsInfo insertOrUpdateAllUserNewsInfo(AllUserNewsInfo allUserNewsInfo) {

        AllUserLogin userLogin = allUserLoginMapper.selectById(allUserNewsInfo.getUserId());

        allUserNewsInfo.setRegionId(userLogin.getRegionCode());

        this.insertOrUpdate(allUserNewsInfo);

        return allUserNewsInfo;
    }

    @Override
    public Boolean deleteAllUserNewsInfo(Integer id, Integer userId) {

        AllUserNewsInfo allUserNewsInfo = new AllUserNewsInfo();
        allUserNewsInfo.setId(id);
        allUserNewsInfo.setUpdateTime(DateUtil.getCurrentTimeStamp());
        allUserNewsInfo.setStatus(0);
        allUserNewsInfo.setUpdateUser(userId);

        return this.updateById(allUserNewsInfo);
    }

    @Override
    public AllUserNewsInfo getAllUserNewsInfoById(Integer id) {

        return this.selectById(id);
    }

    @Override
    public Page<AllUserNewsInfo> getAllUserNewsInfoList(Integer siteId, Integer status, Integer pageNo, Integer pageSize) {

        FanSysSite fanSysSite = fanSysSiteMapper.selectById(siteId);

        Wrapper<AllUserNewsInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("region_id", fanSysSite.getRegionCode());
        wrapper.eq("status", status);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public Page<AllUserNewsInfo> getAllUserNewsInfoList(Page<AllUserNewsInfo> mapPage, Map map) {

        List<AllUserNewsInfo> userNewsInfoList = allUserNewsInfoMapper.getAllUserNewsInfo(mapPage, map);

        if (userNewsInfoList.size() == 0) {
            return null;
        }

        Page<AllUserNewsInfo> page = new Page<>(mapPage.getCurrent(), mapPage.getSize());
        page.setRecords(userNewsInfoList);
        page.setTotal(mapPage.getTotal());

        return page;
    }
}
