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

import java.sql.Timestamp;

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
        indexInfoVo.setName(fanSysSite.getName());

        return indexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo) {

        Wrapper<FanIndexInfo> wrapper = new EntityWrapper<FanIndexInfo>();
        wrapper.eq("site_id", indexInfoVo.getSiteId());

        FanIndexInfo fanIndexInfo = this.selectById(indexInfoVo.getId());

        Timestamp format = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(fanIndexInfo)) {
            fanIndexInfo.setCreateTime(format);
            indexInfoVo.setCreateTime(format);
        }
        fanIndexInfo.setUpdateTime(format);
        indexInfoVo.setUpdateTime(format);

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(fanIndexInfo.getSiteId());

        if (StringUtils.isEmpty(fanSysSite)) {
            fanSysSite.setCreateTime(DateUtil.format(format));
        }
        fanSysSite.setUpdateTime(DateUtil.format(format));

        BeanUtils.copyProperties(indexInfoVo,fanIndexInfo);
        BeanUtils.copyProperties(indexInfoVo,fanSysSite);

        return this.insertOrUpdate(fanIndexInfo) & fanSysSiteService.insertOrUpdate(fanSysSite);

    }

    @Override
    public Boolean deleteFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Timestamp format = DateUtil.getCurrentTimeStamp();

        fanIndexInfo.setUpdateTime(format);

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
