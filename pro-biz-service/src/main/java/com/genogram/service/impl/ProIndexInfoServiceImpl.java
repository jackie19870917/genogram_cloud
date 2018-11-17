package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ProIndexInfo;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.mapper.ProIndexInfoMapper;
import com.genogram.service.IProIndexInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IProSysSiteService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * <p>
 * 省级网站:图腾;简介;宣言 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProIndexInfoServiceImpl extends ServiceImpl<ProIndexInfoMapper, ProIndexInfo> implements IProIndexInfoService {

    @Autowired
    private IProSysSiteService proSysSiteService;

    @Override
    public ProIndexInfo getProIndexInfo(Integer siteId) {
        return this.selectById(siteId);
    }

    @Override
    public IndexInfoVo getFanIndexInfoVo(Integer siteId) {

        ProIndexInfo proIndexInfo = this.selectById(siteId);
        ProSysSite proSysSite = proSysSiteService.getProSysSite(siteId);

        IndexInfoVo indexInfoVo = new IndexInfoVo();
        BeanUtils.copyProperties(proIndexInfo,indexInfoVo);
        indexInfoVo.setSiteName(proSysSite.getName());
        indexInfoVo.setRegionCode(proSysSite.getRegionCode());

        return indexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateIndexInfoVo(IndexInfoVo indexInfoVo) {

        Wrapper<ProIndexInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("site_id", indexInfoVo.getSiteId());

        ProIndexInfo proIndexInfo = this.selectById(indexInfoVo.getId());

        Timestamp format = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(proIndexInfo)) {

            proIndexInfo = new ProIndexInfo();
            proIndexInfo.setCreateTime(format);
            indexInfoVo.setCreateTime(format);

        }

        proIndexInfo.setUpdateTime(format);
        indexInfoVo.setUpdateTime(format);

        ProSysSite proSysSite = proSysSiteService.getProSysSite(proIndexInfo.getId());

        if (StringUtils.isEmpty(proSysSite)) {
            proSysSite=new ProSysSite();
            proSysSite.setCreateTime(DateUtil.format(format));
        }
        proSysSite.setUpdateTime(DateUtil.format(format));

        BeanUtils.copyProperties(indexInfoVo,proIndexInfo);

        proSysSite.setName(indexInfoVo.getSiteName());
        BeanUtils.copyProperties(indexInfoVo,proSysSite);

        this.insertOrUpdate(proIndexInfo);

        proSysSiteService.insertOrUpdate(proSysSite);

        return true;
    }

    @Override
    public Boolean deleteProIndexInfo(ProIndexInfo proIndexInfo) {

        Timestamp format = DateUtil.getCurrentTimeStamp();

        proIndexInfo.setUpdateTime(format);

        if ("".equals(proIndexInfo.getTotemPicSrc())) {
            proIndexInfo.setTotemPicSrc("");
        } else if ("".equals(proIndexInfo.getTitle())) {
            proIndexInfo.setTitle("");
        } else if ("".equals(proIndexInfo.getDescription())) {
            proIndexInfo.setDescription("");
        }

        return this.updateById(proIndexInfo);
    }
}
