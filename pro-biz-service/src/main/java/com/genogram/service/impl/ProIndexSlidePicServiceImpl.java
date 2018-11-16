package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.mapper.ProIndexSlidePicMapper;
import com.genogram.service.IProIndexSlidePicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 省级网站轮播图 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProIndexSlidePicServiceImpl extends ServiceImpl<ProIndexSlidePicMapper, ProIndexSlidePic> implements IProIndexSlidePicService {

    @Override
    public List<ProIndexSlidePic> getProIndexSlidePic(Integer siteId, List list) {

        Wrapper<ProIndexSlidePic> wrapper = new EntityWrapper<ProIndexSlidePic>();
        wrapper.eq("site_id", siteId);
        wrapper.in("status", list);

        return this.selectList(wrapper);

    }
}
