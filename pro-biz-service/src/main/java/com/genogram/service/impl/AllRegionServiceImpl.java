package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllRegion;
import com.genogram.entity.FanSysSite;
import com.genogram.mapper.AllRegionMapper;
import com.genogram.service.IAllRegionService;
import com.genogram.service.IFanSysSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class AllRegionServiceImpl extends ServiceImpl<AllRegionMapper, AllRegion> implements IAllRegionService {

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    /**
     *省级下属县级官网查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 19:00
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public void getProvincialSubordinate(Integer siteId) {
        //查询省级下属开通的县级
        Wrapper<FanSysSite>  entity = new EntityWrapper<FanSysSite>();
        entity.eq("parent",siteId);
    }
}
