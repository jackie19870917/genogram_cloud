package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.mapper.ProIndexSlidePicMapper;
import com.genogram.service.IProIndexSlidePicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Boolean insertOrUpdateProIndexSlidePic(ProIndexSlidePic proIndexSlidePic) {

        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (proIndexSlidePic.getId() == null) {

            List list = new ArrayList();

            //状态   1-前后台显示    2-前台不显示      3-前后台都不显示
            list.add(1);
            list.add(2);

            List<ProIndexSlidePic> proIndexSlidePicList = this.getProIndexSlidePic(proIndexSlidePic.getSiteId(), list);

            proIndexSlidePic.setSort(proIndexSlidePicList.size() + 1);
            proIndexSlidePic.setStatus(1);
            proIndexSlidePic.setCreateTime(format);
        }
        proIndexSlidePic.setUpdateTime(format);

        return this.insertOrUpdate(proIndexSlidePic);

    }

    @Override
    public Boolean deleteProIndexSlidePic(Integer id,Integer userId) {

        ProIndexSlidePic proIndexSlidePic = this.selectById(id);

        List list = new ArrayList();

        //状态   1-前后台显示    2-前台不显示      3-前后台都不显示
        list.add(1);
        list.add(2);

        List<ProIndexSlidePic> proIndexSlidePicList = this.getProIndexSlidePic(proIndexSlidePic.getSiteId(), list);

        for (ProIndexSlidePic slidePic : proIndexSlidePicList) {
            if (proIndexSlidePic.getSort()<slidePic.getSort()) {
                slidePic.setSort(slidePic.getSort() - 1);
                this.updateById(slidePic);
            }
        }

        proIndexSlidePic = new ProIndexSlidePic();

        proIndexSlidePic.setId(id);
        proIndexSlidePic.setUpdateUser(userId);
        proIndexSlidePic.setStatus(0);
        proIndexSlidePic.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(proIndexSlidePic);
    }
}
