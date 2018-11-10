package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanIndexSlidePic;
import com.genogram.mapper.FanIndexSlidePicMapper;
import com.genogram.service.IFanIndexSlidePicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 联谊网站轮播图 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexSlidePicServiceImpl extends ServiceImpl<FanIndexSlidePicMapper, FanIndexSlidePic> implements IFanIndexSlidePicService {

    @Override
    public List<FanIndexSlidePic> getFanIndexSlidePicListBySiteId(Integer siteId, List status) {

        Wrapper<FanIndexSlidePic> wrapper = new EntityWrapper<FanIndexSlidePic>();
        wrapper.eq("site_id",siteId);
        wrapper.in("status", status);

        return this.selectList(wrapper);
    }

    @Override
    public Boolean insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Timestamp format = DateUtil.format(new Date());
        if (fanIndexSlidePic.getId() != null) {
            fanIndexSlidePic.setUpdateTime(format);
        } else {
            fanIndexSlidePic.setCreateTime(format);
        }

        return this.insertOrUpdate(fanIndexSlidePic);
    }

    @Override
    public Boolean deleteFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        fanIndexSlidePic.setUpdateTime(DateUtil.format(new Date()));

        return this.updateById(fanIndexSlidePic);
    }
}
