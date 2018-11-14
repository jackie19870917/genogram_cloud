package com.genogram.service.impl;

import com.genogram.entity.ProIndexFund;
import com.genogram.mapper.ProIndexFundMapper;
import com.genogram.service.IProIndexFundService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

        ProIndexFund proIndexFund = this.selectById(siteId);

        if (StringUtils.isEmpty(proIndexFund)) {
            return null;
        } else {
            return proIndexFund;
        }
    }
}
