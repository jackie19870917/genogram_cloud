package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.AllFamily;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanSysSite;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.mapper.FanIndexInfoMapper;
import com.genogram.service.IAllFamilyService;
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

    @Autowired
    private IAllFamilyService allFamilyService;

    @Override
    public FanIndexInfo getFanIndexInfo(Integer siteId) {

        Wrapper<FanIndexInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("site_id", siteId);
        return this.selectOne(wrapper);
    }

    @Override
    public IndexInfoVo getFanIndexInfoVo(Integer siteId) {

        FanIndexInfo fanIndexInfo = this.getFanIndexInfo(siteId);
        FanSysSite fanSysSite = fanSysSiteService.selectById(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return null;
        }

        IndexInfoVo indexInfoVo = new IndexInfoVo();
        BeanUtils.copyProperties(fanIndexInfo, indexInfoVo);
        indexInfoVo.setSiteName(fanSysSite.getName());
        indexInfoVo.setFamilyCode(fanSysSite.getFamilyCode());
        indexInfoVo.setRegionCode(fanSysSite.getRegionCode());
        AllFamily family = allFamilyService.getAllFamilyById(fanSysSite.getFamilyCode());
        indexInfoVo.setFamilyName(family.getValue());

        return indexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo) {

        FanIndexInfo fanIndexInfo = this.selectById(indexInfoVo.getId());

        Timestamp format = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(fanIndexInfo)) {

            fanIndexInfo.setCreateTime(format);
            indexInfoVo.setCreateTime(format);
        }

        FanIndexInfo indexInfo = new FanIndexInfo();
        BeanUtils.copyProperties(indexInfoVo, indexInfo);
        indexInfo.setUpdateTime(format);

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(fanIndexInfo.getSiteId());


        fanSysSite.setUpdateTime(DateUtil.format(format));
        fanSysSite.setName(indexInfoVo.getSiteName());
        fanSysSite.setUpdateUser(indexInfoVo.getUpdateUser());

        return this.insertOrUpdate(indexInfo) & fanSysSiteService.updateById(fanSysSite);

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

    @Override
    public Boolean insertFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Timestamp format = DateUtil.getCurrentTimeStamp();

        FanIndexInfo indexInfo = this.getFanIndexInfo(fanIndexInfo.getSiteId());

        if (StringUtils.isEmpty(indexInfo)) {
            fanIndexInfo.setUpdateTime(format);
            fanIndexInfo.setUpdateTime(format);

            return this.insert(fanIndexInfo);
        } else {
            return null;
        }
    }
}
