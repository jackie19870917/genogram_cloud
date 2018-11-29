package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanIndexFund;
import com.genogram.mapper.FanIndexFundMapper;
import com.genogram.service.IFanIndexFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 联谊会-首页-基金-前台后台 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFundServiceImpl extends ServiceImpl<FanIndexFundMapper, FanIndexFund> implements IFanIndexFundService {

    @Override
    public FanIndexFund getFanIndexFund(Integer siteId) {

        Wrapper<FanIndexFund> wrapper = new EntityWrapper<>();
        wrapper.eq("site_id", siteId);

        FanIndexFund fanIndexFund = this.selectOne(wrapper);
        if (StringUtils.isEmpty(fanIndexFund)) {
            return null;
        } else {
            return fanIndexFund;
        }
    }

    @Override
    public Boolean insertOrUpdateFanIndexFund(FanIndexFund fanIndexFund) {

        FanIndexFund fanIndexFund1 = this.selectById(fanIndexFund.getSiteId());

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(fanIndexFund1)) {

            fanIndexFund.setCreateTime(timeStamp);
            fanIndexFund.setCreateUser(1);
            fanIndexFund.setRemain(new BigDecimal("0"));
            fanIndexFund.setUnuseAmount(new BigDecimal("0"));
            fanIndexFund.setPayOnline(new BigDecimal("0"));
            fanIndexFund.setPayUnderline(new BigDecimal("0"));
            fanIndexFund.setPayGenogram(new BigDecimal("0"));

        }/*else {

            fanIndexFund.setRemain(fanIndexFund1.getRemain().add(fanIndexFund1.getPayOnline()));
            fanIndexFund.setRemain(fanIndexFund1.getRemain().add(fanIndexFund1.getPayUnderline()));
            fanIndexFund.setRemain(fanIndexFund1.getRemain().add(fanIndexFund1.getPayGenogram()));

            fanIndexFund.setPayGenogram(fanIndexFund1.getPayGenogram().add(fanIndexFund1.getPayGenogram()));
            fanIndexFund.setPayOnline(fanIndexFund1.getPayOnline().add(fanIndexFund1.getPayOnline()));
            fanIndexFund.setPayUnderline(fanIndexFund1.getPayUnderline().add(fanIndexFund1.getPayUnderline()));
        }*/

        fanIndexFund.setUpdateTime(timeStamp);
        fanIndexFund.setUpdateUser(1);

        return this.insertOrUpdate(fanIndexFund);
    }

    @Override
    public Boolean updateFanIndexFund(FanIndexFund fanIndexFund) {

        FanIndexFund fanIndexFund1 = this.selectById(fanIndexFund.getSiteId());

        fanIndexFund.setRemain(fanIndexFund1.getRemain().subtract(fanIndexFund.getPayOnline()));
        fanIndexFund.setRemain(fanIndexFund1.getRemain().subtract(fanIndexFund.getPayUnderline()));
        fanIndexFund.setPayOnline(fanIndexFund1.getPayOnline().subtract(fanIndexFund.getPayOnline()));
        fanIndexFund.setPayUnderline(fanIndexFund1.getPayUnderline().subtract(fanIndexFund.getPayUnderline()));
        fanIndexFund.setUpdateTime(DateUtil.getCurrentTimeStamp());
        fanIndexFund.setUpdateUser(1);

        return this.updateFanIndexFund(fanIndexFund);
    }

    @Override
    public Boolean insertFanIndexFund(FanIndexFund fanIndexFund) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        fanIndexFund.setCreateTime(timeStamp);
        fanIndexFund.setUpdateTime(timeStamp);
        fanIndexFund.setRemain(new BigDecimal("0"));
        fanIndexFund.setUnuseAmount(new BigDecimal("0"));
        fanIndexFund.setPayOnline(new BigDecimal("0"));
        fanIndexFund.setPayUnderline(new BigDecimal("0"));
        fanIndexFund.setPayGenogram(new BigDecimal("0"));

        return this.insert(fanIndexFund);
    }
}
