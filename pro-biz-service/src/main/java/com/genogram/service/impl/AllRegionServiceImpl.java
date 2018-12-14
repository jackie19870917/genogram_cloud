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
import com.genogram.service.IProFanSysSiteService;
import com.genogram.service.IProSysSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    private IProFanSysSiteService fanSysSiteService;

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
        regionEntity.in("code", regionCode);
        List<AllRegion> allRegions = this.selectList(regionEntity);
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
    public Page<FanSysSite> getSodalityRegion(Integer siteId, Integer pageNo, Integer pageSize) {
        //查询姓氏 和省级地区ID
        Wrapper<ProSysSite> entity = new EntityWrapper<>();
        entity.eq("id", siteId);
        ProSysSite proSysSite = proSysSiteService.selectOne(entity);

        //判断是否有省级网站
        if (StringUtils.isEmpty(proSysSite)) {
            return null;
        }

        //根据省级的地区Id查询出所有开通的县级的ID
        Wrapper<AllRegion> allEntity = new EntityWrapper<>();
        allEntity.eq("parent_code", proSysSite.getRegionCode());
        List<AllRegion> allRegions = this.selectList(allEntity);

        //省级下县级的地区Id
        List<Integer> list = new ArrayList();

        //判断地区下是否还有地区县级
        List<AllRegion> regionsAll = new ArrayList();
        for (AllRegion allRegion : allRegions) {
            if (allRegion.getParentCode() % 10000 == 0) {
                Wrapper<AllRegion> allEntity2 = new EntityWrapper<>();
                allEntity2.eq("parent_code", allRegion.getCode());
                List<AllRegion> allRegions2 = this.selectList(allEntity2);
                //合并list
                regionsAll.addAll(allRegions2);
            }
            //存储地区Id
            list.add(allRegion.getParentCode());
        }

        if (regionsAll != null && regionsAll.size() != 0) {
            for (AllRegion allRegion : regionsAll) {
                list.add(allRegion.getParentCode());
            }
        }

        //去重  list集合
        list = new ArrayList<Integer>(new LinkedHashSet<>(list));

        //查询出开通县级的id
        Wrapper<FanSysSite> entitySite = new EntityWrapper<>();
        entitySite.eq("family_code", proSysSite.getFamilyCode());
        entitySite.in("region_code", list);
        Page<FanSysSite> fanSysSitePage = fanSysSiteService.selectPage(new Page<FanSysSite>(pageNo, pageSize), entitySite);
        return fanSysSitePage;
    }
}
