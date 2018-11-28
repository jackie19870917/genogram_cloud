package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.SysSiteVo;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.mapper.ProSysSiteMapper;
import com.genogram.service.ISysSiteService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 *@author: Toxicant
 *@date: 2018-11-23
 */
@Service
public class SysSiteServiceImpl extends ServiceImpl<FanSysSiteMapper, FanSysSite> implements ISysSiteService {

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Autowired
    private ProSysSiteMapper proSysSiteMapper;

    @Override
    public Boolean insertFanSysSite(FanSysSite fanSysSite) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        fanSysSite.setCreateTime(timeStamp);
        fanSysSite.setUpdateTime(timeStamp);
        fanSysSite.setStatus(1);

        Integer result = fanSysSiteMapper.insert(fanSysSite);

        System.out.println(fanSysSite.getId());
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean insertProSysSite(ProSysSite proSysSite) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        proSysSite.setCreateTime(timeStamp);
        proSysSite.setUpdateTime(timeStamp);
        proSysSite.setStatus(1);

        Integer result = proSysSiteMapper.insert(proSysSite);

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FanSysSite getFanSysSite() {
        Wrapper<FanSysSite> fanSysSiteWrapper = new EntityWrapper<>();
        fanSysSiteWrapper.orderBy("create_time", false);

        List<FanSysSite> fanSysSiteList = fanSysSiteMapper.selectList(fanSysSiteWrapper);
        return fanSysSiteList.get(0);
    }

    @Override
    public ProSysSite getProSysSite() {
        Wrapper<ProSysSite> proSysSiteWrapper = new EntityWrapper<>();
        proSysSiteWrapper.orderBy("create_time", false);

        List<ProSysSite> proSysSiteList = proSysSiteMapper.selectList(proSysSiteWrapper);
        return proSysSiteList.get(0);
    }
}
