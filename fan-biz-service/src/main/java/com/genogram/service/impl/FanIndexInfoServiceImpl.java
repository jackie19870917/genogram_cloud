package com.genogram.service.impl;

import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanSysSite;
import com.genogram.entityvo.FanIndexInfoVo;
import com.genogram.mapper.FanIndexInfoMapper;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.service.IFanIndexInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private FanIndexInfoMapper fanIndexInfoMapper;

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Override
    public FanIndexInfo getFanIndexInfo(Integer siteId) {
        return fanIndexInfoMapper.selectById(siteId);
    }

    @Override
    public FanIndexInfoVo getFanIndexInfoVo(Integer siteId) {
        FanIndexInfo fanIndexInfo = this.getFanIndexInfo(siteId);
        FanSysSite fanSysSite = fanSysSiteMapper.selectById(siteId);

        FanIndexInfoVo fanIndexInfoVo = new FanIndexInfoVo();
        fanIndexInfoVo.setId(fanIndexInfo.getId());
        fanIndexInfoVo.setSiteId(fanIndexInfo.getSiteId());
        fanIndexInfoVo.setTotemPicSrc(fanIndexInfo.getTotemPicSrc());
        fanIndexInfoVo.setTitle(fanIndexInfo.getTitle());
        fanIndexInfoVo.setDescription(fanIndexInfo.getDescription());
        fanIndexInfoVo.setCreateTime(fanIndexInfo.getCreateTime());
        fanIndexInfoVo.setCreateUser(fanIndexInfo.getCreateUser());
        fanIndexInfoVo.setUpdateTime(fanIndexInfo.getUpdateTime());
        fanIndexInfoVo.setUpdateUser(fanIndexInfo.getUpdateUser());
        fanIndexInfoVo.setSiteName(fanSysSite.getName());
        return fanIndexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo) {

        return this.insertOrUpdate(fanIndexInfo);
    }
}
