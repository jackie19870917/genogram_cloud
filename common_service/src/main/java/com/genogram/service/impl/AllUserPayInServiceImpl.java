package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.mapper.FanNewsCharityPayInMapper;
import com.genogram.mapper.ProNewsCharityPayInMapper;
import com.genogram.service.IAllUserPayInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class AllUserPayInServiceImpl extends ServiceImpl<FanNewsCharityPayInMapper, FanNewsCharityPayIn> implements IAllUserPayInService {

    @Autowired
    private FanNewsCharityPayInMapper fanNewsCharityPayInMapper;

    @Autowired
    private ProNewsCharityPayInMapper proNewsCharityPayInMapper;

    @Override
    public List<FanNewsCharityPayIn> getFanNewsCharityPayInListByUserId(Integer userId) {

        Wrapper<FanNewsCharityPayIn> wrapper = new EntityWrapper<>();
        wrapper.eq("pay_usr_id", userId);
        wrapper.eq("status", 1);
        wrapper.orderBy("pay_time", false);

        return fanNewsCharityPayInMapper.selectList(wrapper);
    }

    @Override
    public List<ProNewsCharityPayIn> getProNewsCharityPayInListByUserId(Integer userId) {

        Wrapper<ProNewsCharityPayIn> wrapper = new EntityWrapper<>();
        wrapper.eq("pay_usr_id", userId);
        wrapper.eq("status", 1);
        wrapper.orderBy("pay_time", false);

        return proNewsCharityPayInMapper.selectList(wrapper);
    }

    @Override
    public Page getPayInPageByUserId(Integer userId, Integer pageNo, Integer pageSize) {

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = this.getFanNewsCharityPayInListByUserId(userId);
        List<ProNewsCharityPayIn> proNewsCharityPayInList = this.getProNewsCharityPayInListByUserId(userId);

        List list = new ArrayList();
        list.addAll(fanNewsCharityPayInList);
        list.addAll(proNewsCharityPayInList);

        Page page = new Page(pageNo, pageSize);
        page.setRecords(list);
        page.setTotal(fanNewsCharityPayInList.size() + proNewsCharityPayInList.size());

        return page;
    }
}
