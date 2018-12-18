package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanSysSite;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.service.IFanSysSiteService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * <p>
 * 开通联谊会表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanSysSiteServiceImpl extends ServiceImpl<FanSysSiteMapper, FanSysSite> implements IFanSysSiteService {

    @Override
    public FanSysSite getFanSysSite(Integer siteId) {

        FanSysSite fanSysSite = this.selectById(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return null;
        } else {
            return fanSysSite;
        }
    }

    @Override
    public FanSysSite getFanSysSiteByCode(String code) {

        Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
        wrapper.eq("fan_url_code", code);

        return this.selectOne(wrapper);
    }

}
