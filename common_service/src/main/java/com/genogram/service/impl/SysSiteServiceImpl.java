package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.mapper.ProSysSiteMapper;
import com.genogram.service.ISysSiteService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Toxicant
 * @date: 2018-11-23
 */
@Service
public class SysSiteServiceImpl extends ServiceImpl<FanSysSiteMapper, FanSysSite> implements ISysSiteService {

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Autowired
    private ProSysSiteMapper proSysSiteMapper;

    @Override
    public FanSysSite insertFanSysSite(FanSysSite fanSysSite) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        fanSysSite.setCreateTime(timeStamp);
        fanSysSite.setUpdateTime(timeStamp);
        fanSysSite.setStatus(1);

        Integer result = fanSysSiteMapper.insert(fanSysSite);

        if (result == 1) {
            return fanSysSite;
        } else {
            return null;
        }
    }

    @Override
    public ProSysSite insertProSysSite(ProSysSite proSysSite) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        proSysSite.setCreateTime(timeStamp);
        proSysSite.setUpdateTime(timeStamp);
        proSysSite.setStatus(1);

        Integer result = proSysSiteMapper.insert(proSysSite);

        if (result == 1) {
            return proSysSite;
        } else {
            return null;
        }
    }

    @Override
    public List<FanSysSite> getFanSysSite(Integer familyCode,String parent) {

        Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
        wrapper.eq("family_code", familyCode);
        wrapper.eq("parent", parent);

        return  fanSysSiteMapper.selectList(wrapper);
    }

    @Override
    public List<ProSysSite> getProSysSite() {

        return proSysSiteMapper.selectList(null);
    }
}
