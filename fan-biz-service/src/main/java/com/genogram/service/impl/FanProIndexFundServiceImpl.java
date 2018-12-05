package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.ProIndexFund;
import com.genogram.mapper.ProIndexFundMapper;
import com.genogram.service.IFanProIndexFundService;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 省级-首页-基金-前台后台 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanProIndexFundServiceImpl extends ServiceImpl<ProIndexFundMapper, ProIndexFund> implements IFanProIndexFundService {

    @Override
    public ProIndexFund getProIndexFund(Integer siteId) {
        Wrapper<ProIndexFund> wrapper = new EntityWrapper<>();
        wrapper.eq("site_id", siteId);

        ProIndexFund proIndexFund = this.selectOne(wrapper);
        if (StringUtils.isEmpty(proIndexFund)) {
            return null;
        } else {
            return proIndexFund;
        }
    }

    @Override
    public Boolean insertProIndexFund(ProIndexFund proIndexFund) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        ProIndexFund fund = this.getProIndexFund(proIndexFund.getSiteId());

        if (StringUtils.isEmpty(fund)) {

            proIndexFund.setCreateTime(timeStamp);
            proIndexFund.setUpdateTime(timeStamp);
            proIndexFund.setRemain(new BigDecimal("0"));
            proIndexFund.setUnuseAmount(new BigDecimal("0"));
            proIndexFund.setPayOnline(new BigDecimal("0"));
            proIndexFund.setPayUnderline(new BigDecimal("0"));
            proIndexFund.setPayGenogram(new BigDecimal("0"));

            return this.insert(proIndexFund);
        } else {
            return null;
        }
    }
}
