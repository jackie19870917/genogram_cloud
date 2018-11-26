package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllRegion;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.mapper.AllRegionMapper;
import com.genogram.service.IAllRegionService;
import com.genogram.service.IFanSysSiteService;
import com.genogram.service.IProSysSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private IAllRegionService allRegionService;

    @Autowired
    private IProSysSiteService proSysSiteService;

    /**
     * 省级下属县级官网查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 19:00
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public List<AllRegion> getProvincialSubordinate(Integer siteId) {
        //查询省级下属开通的县级
        Wrapper<FanSysSite> entity = new EntityWrapper<FanSysSite>();
        entity.eq("parent", siteId);
        //状态(0:删除1:已开通;2:未开通;3:待开通)
        entity.eq("status", 1);
        List<FanSysSite> fanSysSites = fanSysSiteService.selectList(entity);
        //根据县级Id查出名称
        //判断改集合是否为空,如果是直接返回结果
        if (fanSysSites.size() == 0) {
            return null;
        }

        //得到所有以开通地区的ID
        List regionCode = new ArrayList<>();
        fanSysSites.forEach((news) -> {
            regionCode.add(news.getRegionCode());
        });

        Wrapper<AllRegion> regionEntity = new EntityWrapper<AllRegion>();
        regionEntity.in("code",regionCode);
        List<AllRegion> allRegions = this.selectList(regionEntity);
        return allRegions;
    }

    /**
     * 省级下属地图查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 19:00
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public List<AllRegion> getRegion(Wrapper<AllRegion> entity) {
        List<AllRegion> allRegions = this.selectList(entity);
        return allRegions;
    }

    /**
     * 省级下属地图联谊会查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 19:00
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<FanSysSite> getSodalityRegion(String parentCode, Integer siteId, Integer pageNo, Integer pageSize) {
        //查询姓氏
        Wrapper<ProSysSite> entity=new EntityWrapper<>();
        entity.eq("id",siteId);
        ProSysSite proSysSite = proSysSiteService.selectOne(entity);
        //字符串转换集合
        List<String> result = Arrays.asList(parentCode.split(","));
        //查询地区联谊会
        Wrapper<FanSysSite> entitySite=new EntityWrapper<>();
        entitySite.eq("family_code",proSysSite.getFamilyCode());
        entitySite.in("region_code",result);
        Page<FanSysSite> fanSysSitePage = fanSysSiteService.selectPage(new Page<FanSysSite>(pageNo, pageSize), entitySite);
        return fanSysSitePage;
    }
}
