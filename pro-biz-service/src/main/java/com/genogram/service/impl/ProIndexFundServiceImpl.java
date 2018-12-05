package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ProIndexFund;
import com.genogram.mapper.ProIndexFundMapper;
import com.genogram.service.IProIndexFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 省级-首页-基金-前台后台 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProIndexFundServiceImpl extends ServiceImpl<ProIndexFundMapper, ProIndexFund> implements IProIndexFundService {

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
    public Boolean updateProIndexFund(ProIndexFund proIndexFund) {

        ProIndexFund proIndexFund1 = this.getProIndexFund(proIndexFund.getId());

        proIndexFund.setRemain(proIndexFund1.getRemain().subtract(proIndexFund.getPayOnline()));
        proIndexFund.setRemain(proIndexFund1.getRemain().subtract(proIndexFund.getPayUnderline()));
        proIndexFund.setPayOnline(proIndexFund1.getPayOnline().subtract(proIndexFund.getPayOnline()));
        proIndexFund.setPayUnderline(proIndexFund1.getPayUnderline().subtract(proIndexFund.getPayUnderline()));
        proIndexFund.setUpdateTime(DateUtil.getCurrentTimeStamp());
        proIndexFund.setUpdateUser(1);

        return this.updateProIndexFund(proIndexFund);
    }
}
