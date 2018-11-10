package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entityvo.FanIndexFamilySummarysVo;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.FanIndexFamilySummarysMapper;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private AllUserLoginMapper allUserLoginMapper;

    @Override
    public Page<FanIndexFamilySummarysVo> getFanIndexFamilySummarysPage(Integer siteId, List status, Integer pageNo, Integer pageSize) {

        Wrapper<FanIndexFamilySummarys> entity = new EntityWrapper<FanIndexFamilySummarys>();
        entity.eq("site_id", siteId);
        entity.in("status", status);

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = this.selectPage(new Page<FanIndexFamilySummarys>(pageNo, pageSize), entity);

        List<FanIndexFamilySummarys> fanIndexFamilySummarysList = fanIndexFamilySummarysPage.getRecords();

        List list = new ArrayList();
        for (FanIndexFamilySummarys fanIndexFamilySummarys : fanIndexFamilySummarysList) {
            list.add(fanIndexFamilySummarys.getLeader());
        }

        Wrapper<AllUserLogin> wrapper = new EntityWrapper();
        wrapper.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginMapper.selectList(wrapper);

        List<FanIndexFamilySummarysVo> fanIndexFamilySummarysVoList = new ArrayList<>();
        for (FanIndexFamilySummarys fanIndexFamilySummarys : fanIndexFamilySummarysList) {
            for (AllUserLogin allUserLogin : allUserLoginList) {
                if (fanIndexFamilySummarys.getLeader().equals(allUserLogin.getId())) {

                    FanIndexFamilySummarysVo fanIndexFamilySummarysVo = new FanIndexFamilySummarysVo();
                    BeanUtils.copyProperties(fanIndexFamilySummarys, fanIndexFamilySummarysVo);
                    fanIndexFamilySummarysVo.setLeaderName(allUserLogin.getUserName());

                    fanIndexFamilySummarysVoList.add(fanIndexFamilySummarysVo);
                }
            }
        }

        Page<FanIndexFamilySummarysVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(fanIndexFamilySummarysVoList);
        // mapPage.setSize(fanIndexFamilySummarysPage.getSize());
        mapPage.setTotal(fanIndexFamilySummarysPage.getTotal());

        return mapPage;
    }

    @Override
    public Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarysVo fanIndexFamilySummarysVo) {

        if (fanIndexFamilySummarysVo.getId() != null) {
            fanIndexFamilySummarysVo.setUpdateTime(new Date());
        } else {
            fanIndexFamilySummarysVo.setCreateTime(new Date());
        }

        // return  this.insertOrUpdate(fanIndexFamilySummarysVo);
        return null;
    }

    @Override
    public FanIndexFamilySummarysVo getFanIndexFamilySummarys(Integer id) {

        FanIndexFamilySummarys fanIndexFamilySummarys = this.selectById(id);

        AllUserLogin allUserLogin = allUserLoginMapper.selectById(id);

        FanIndexFamilySummarysVo fanIndexFamilySummarysVo = new FanIndexFamilySummarysVo();

        BeanUtils.copyProperties(fanIndexFamilySummarys, fanIndexFamilySummarysVo);
        fanIndexFamilySummarysVo.setLeaderName(allUserLogin.getUserName());

        return fanIndexFamilySummarysVo;
    }

    @Override
    public Boolean deleteFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        return this.updateById(fanIndexFamilySummarys);
    }
}
