package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanSysSite;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.mapper.FanIndexInfoMapper;
import com.genogram.service.IFanIndexInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanSysSiteService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 联谊会网站:图腾;简介;宣言 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexInfoServiceImpl extends ServiceImpl<FanIndexInfoMapper, FanIndexInfo> implements IFanIndexInfoService {

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    @Override
    public FanIndexInfo getFanIndexInfo(Integer siteId) {
        return this.selectById(siteId);
    }

    @Override
    public IndexInfoVo getFanIndexInfoVo(Integer siteId) {

        FanIndexInfo fanIndexInfo = this.selectById(siteId);
        FanSysSite fanSysSite = fanSysSiteService.selectById(siteId);

        IndexInfoVo indexInfoVo = new IndexInfoVo();
        BeanUtils.copyProperties(fanIndexInfo, indexInfoVo);
        indexInfoVo.setSiteName(fanSysSite.getName());

        return indexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo) {
        Wrapper<FanIndexInfo> wrapper = new EntityWrapper<FanIndexInfo>();
        wrapper.eq("site_id", indexInfoVo.getSiteId());

        FanIndexInfo fanIndexInfo = this.selectOne(wrapper);

        if (StringUtils.isEmpty(fanIndexInfo)) {
            fanIndexInfo.setCreateTime(DateUtil.format(new Date()));
        }
        fanIndexInfo.setUpdateTime(DateUtil.format(new Date()));

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(indexInfoVo.getSiteId());

        if (StringUtils.isEmpty(fanSysSite)) {
            fanSysSite.setCreateTime(DateUtil.format(new Date()));
        }
        fanSysSite.setUpdateTime(DateUtil.format(new Date()));

        return this.insertOrUpdate(fanIndexInfo) & fanSysSiteService.insertOrUpdate(fanSysSite);

    }

    @Override
    public Boolean deleteFanIndexInfo(FanIndexInfo fanIndexInfo) {

        fanIndexInfo.setUpdateTime(DateUtil.format(new Date()));

        if ("".equals(fanIndexInfo.getTotemPicSrc())) {
            fanIndexInfo.setTotemPicSrc("");
        } else if ("".equals(fanIndexInfo.getTitle())) {
            fanIndexInfo.setTitle("");
        } else if ("".equals(fanIndexInfo.getDescription())) {
            fanIndexInfo.setDescription("");
        }

        return this.updateById(fanIndexInfo);
    }
}
