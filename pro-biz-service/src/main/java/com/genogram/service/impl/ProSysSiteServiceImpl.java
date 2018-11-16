package com.genogram.service.impl;

import com.genogram.entity.ProSysSite;
import com.genogram.mapper.ProSysSiteMapper;
import com.genogram.service.IProSysSiteService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 开通省级网站表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProSysSiteServiceImpl extends ServiceImpl<ProSysSiteMapper, ProSysSite> implements IProSysSiteService {

    @Override
    public ProSysSite getProSysSite(Integer siteId) {

        ProSysSite proSysSite = this.selectById(siteId);
        if (StringUtils.isEmpty(proSysSite)) {
            return null;
        } else {
            return proSysSite;
        }
    }

}
