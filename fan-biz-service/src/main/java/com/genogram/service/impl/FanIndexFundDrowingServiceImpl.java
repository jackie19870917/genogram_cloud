package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFundDrowing;
import com.genogram.mapper.FanIndexFundDrowingMapper;
import com.genogram.service.IFanIndexFundDrowingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 线上提现 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFundDrowingServiceImpl extends ServiceImpl<FanIndexFundDrowingMapper, FanIndexFundDrowing> implements IFanIndexFundDrowingService {

    @Override
    public Boolean insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing) {

        fanIndexFundDrowing.setCreateTime(DateUtil.getCurrentTimeStamp());
        fanIndexFundDrowing.setDrowStatus(1);

        return this.insert(fanIndexFundDrowing);
    }

    @Override
    public Page<FanIndexFundDrowing> getFanIndexFundDrowingPage(Integer siteId, List list,Integer pageNo,Integer pageSize) {

        Wrapper<FanIndexFundDrowing> wrapper = new EntityWrapper<FanIndexFundDrowing>();
        wrapper.eq("site_id", siteId);
        wrapper.in("approve_status",list);
        wrapper.orderBy("update_time", false);

        return  this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }
}
