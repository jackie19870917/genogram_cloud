package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.mapper.FanIndexFamilySummarysMapper;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 联谊会首页-联谊堂 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFamilySummarysServiceImpl extends ServiceImpl<FanIndexFamilySummarysMapper, FanIndexFamilySummarys> implements IFanIndexFamilySummarysService {

    @Autowired
    private FanIndexFamilySummarysMapper fanIndexFamilySummarysMapper;

    @Override
    public Page<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(Integer siteId,Integer status,Integer pageNo,Integer pageSize) {
        Wrapper<FanIndexFamilySummarys> entity = new EntityWrapper<FanIndexFamilySummarys>();
        entity.eq("site_id", siteId);
        entity.eq("status", status);
        return this.selectPage(new Page<FanIndexFamilySummarys>(pageNo,pageSize),entity);
    }

    @Override
    public Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        if (fanIndexFamilySummarys.getId() != null) {
            fanIndexFamilySummarys.setUpdateTime(new Date());
        } else {
            fanIndexFamilySummarys.setCreateTime(new Date());
        }

        return  this.insertOrUpdate(fanIndexFamilySummarys);

    }

    @Override
    public FanIndexFamilySummarys getFanIndexFamilySummarys(Integer id) {

        return this.selectById(id);
    }
}
