package com.genogram.service.impl;

import com.genogram.entity.ChiSysSite;
import com.genogram.mapper.ChiSysSiteMapper;
import com.genogram.service.IChiSysSiteService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级网站表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiSysSiteServiceImpl extends ServiceImpl<ChiSysSiteMapper, ChiSysSite> implements IChiSysSiteService {

    @Override
    public ChiSysSite getFanSysSite(Integer siteId) {

        return this.selectById(siteId);
    }
}
