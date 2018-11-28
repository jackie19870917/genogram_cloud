package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.ProIndexInfo;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.mapper.ProIndexInfoMapper;
import com.genogram.mapper.ProSysSiteMapper;
import com.genogram.service.IFanProIndexInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级网站:图腾;简介;宣言 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class FanProIndexInfoServiceImpl extends ServiceImpl<ProIndexInfoMapper, ProIndexInfo> implements IFanProIndexInfoService {

    @Autowired
    private ProSysSiteMapper proSysSiteMapper;

    @Autowired
    private ProIndexInfoMapper proIndexInfoMapper;

    @Override
    public ProIndexInfo getProIndexInfo(Integer siteId) {

        ProIndexInfo proIndexInfo = new ProIndexInfo();
        proIndexInfo.setSiteId(siteId);

        return proIndexInfoMapper.selectOne(proIndexInfo);
    }

    @Override
    public IndexInfoVo getFanIndexInfoVo(Integer siteId) {

        ProIndexInfo proIndexInfo = this.getProIndexInfo(siteId);

        ProSysSite proSysSite = proSysSiteMapper.selectById(siteId);

        IndexInfoVo indexInfoVo = new IndexInfoVo();
        BeanUtils.copyProperties(proIndexInfo,indexInfoVo);
        indexInfoVo.setSiteName(proSysSite.getName());
        indexInfoVo.setRegionCode(proSysSite.getRegionCode());

        return indexInfoVo;
    }

}
