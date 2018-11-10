package com.genogram.service.impl;

import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanSysSite;
import com.genogram.entityvo.FanIndexInfoVo;
import com.genogram.mapper.FanIndexInfoMapper;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.service.IFanIndexInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        BeanUtils.copyProperties(fanIndexInfo, fanIndexInfoVo);
        fanIndexInfoVo.setSiteName(fanSysSite.getName());

        return fanIndexInfoVo;
    }

    @Override
    public Boolean insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo) {

        if (fanIndexInfo.getId() != null) {
            fanIndexInfo.setUpdateTime(DateUtil.timestamp());
        } else {
            fanIndexInfo.setCreateTime(DateUtil.timestamp());
        }
        return this.insertOrUpdate(fanIndexInfo);
    }

    @Override
    public Boolean deleteFanIndexInfo(FanIndexInfo fanIndexInfo) {

        fanIndexInfo.setUpdateTime(DateUtil.timestamp());

        if ("".equals(fanIndexInfo.getTotemPicSrc())) {
            fanIndexInfo.setTotemPicSrc("");
        } else if ("".equals(fanIndexInfo.getTitle())) {
            fanIndexInfo.setTitle("");
        } else if ("".equals(fanIndexInfo.getDescription())){
            fanIndexInfo.setDescription("");
        }

        return this.updateById(fanIndexInfo);
    }
}
