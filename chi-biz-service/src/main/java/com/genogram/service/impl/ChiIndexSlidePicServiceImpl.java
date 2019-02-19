package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ChiIndexSlidePic;
import com.genogram.mapper.ChiIndexSlidePicMapper;
import com.genogram.service.IChiIndexSlidePicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 省级网站轮播图 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiIndexSlidePicServiceImpl extends ServiceImpl<ChiIndexSlidePicMapper, ChiIndexSlidePic> implements IChiIndexSlidePicService {

    @Override
    public List<ChiIndexSlidePic> getChiIndexSlidePicBySiteId(Integer siteId, Integer status) {

        Wrapper<ChiIndexSlidePic> wrapper = new EntityWrapper<ChiIndexSlidePic>();
        wrapper.eq("site_id", siteId);
        wrapper.eq("status", status);

        return this.selectList(wrapper);
    }

    @Override
    public Boolean chiIndexSlidePic(Integer id, Integer userId) {

        ChiIndexSlidePic chiIndexSlidePic = new ChiIndexSlidePic();

        chiIndexSlidePic.setId(id);
        chiIndexSlidePic.setStatus(0);
        chiIndexSlidePic.setUpdateUser(userId);
        chiIndexSlidePic.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(chiIndexSlidePic);
    }

    @Override
    public Boolean insertOrUpdateChiIndexSlidePic(ChiIndexSlidePic chiIndexSlidePic) {

        return this.insertOrUpdate(chiIndexSlidePic);
    }
}
