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
import java.util.ArrayList;
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
    public List<FanIndexSlidePic> getFanIndexSlidePicListBySiteId(Integer siteId, Integer classes, List status) {

        Wrapper<FanIndexSlidePic> wrapper = new EntityWrapper<FanIndexSlidePic>();
        wrapper.eq("site_id", siteId);
        wrapper.eq("classes", classes);
        wrapper.in("status", status);

        return this.selectList(wrapper);
    }

    @Override
    public Boolean insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (fanIndexSlidePic.getId() == null) {

            List list = new ArrayList();

            //状态   1-前后台显示    2-前台不显示      3-前后台都不显示
            list.add(1);
            list.add(2);

            List<FanIndexSlidePic> fanIndexSlidePicList = this.getFanIndexSlidePicListBySiteId(fanIndexSlidePic.getSiteId(), fanIndexSlidePic.getClasses(), list);

            fanIndexSlidePic.setSort(fanIndexSlidePicList.size() + 1);
            fanIndexSlidePic.setStatus(1);
            fanIndexSlidePic.setCreateTime(format);
        }
        fanIndexSlidePic.setUpdateTime(format);

        return this.insertOrUpdate(fanIndexSlidePic);
    }

    @Override
    public Boolean deleteFanIndexSlidePic(Integer id, Integer userId) {

        FanIndexSlidePic indexSlidePic = this.selectById(id);

        List list = new ArrayList();

        //状态   1-前后台显示    2-前台不显示      3-前后台都不显示
        list.add(1);
        list.add(2);

        List<FanIndexSlidePic> fanIndexSlidePicList = this.getFanIndexSlidePicListBySiteId(indexSlidePic.getSiteId(), indexSlidePic.getClasses(), list);

        for (FanIndexSlidePic slidePic : fanIndexSlidePicList) {
            if (indexSlidePic.getSort() < slidePic.getSort()) {
                slidePic.setSort(slidePic.getSort() - 1);
                this.updateById(slidePic);
            }
        }

        FanIndexSlidePic fanIndexSlidePic = new FanIndexSlidePic();

        fanIndexSlidePic.setId(id);
        fanIndexSlidePic.setUpdateUser(userId);
        fanIndexSlidePic.setStatus(0);
        fanIndexSlidePic.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(fanIndexSlidePic);
    }

    @Override
    public Boolean insertFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Timestamp timestamp = DateUtil.getCurrentTimeStamp();

        fanIndexSlidePic.setCreateTime(timestamp);
        fanIndexSlidePic.setUpdateTime(timestamp);

        fanIndexSlidePic.setSort(1);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(1);
        fanIndexSlidePic.setPicUrl("00/03/rB_QCFwYmhmAe5sjAAFJkW4fa_g851.jpg");

        this.insert(fanIndexSlidePic);

        fanIndexSlidePic.setSort(2);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(1);
        fanIndexSlidePic.setPicUrl("00/05/default_lunbo_2.png");

        this.insert(fanIndexSlidePic);

        fanIndexSlidePic.setSort(3);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(1);
        fanIndexSlidePic.setPicUrl("00/05/default_lunbo_3.png");

        this.insert(fanIndexSlidePic);



        fanIndexSlidePic.setSort(1);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(2);
        fanIndexSlidePic.setPicUrl("00/03/rB_QCFwYmhmAe5sjAAFJkW4fa_g851.jpg");

        this.insert(fanIndexSlidePic);

        fanIndexSlidePic.setSort(2);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(2);
        fanIndexSlidePic.setPicUrl("00/05/default_lunbo_2.png");

        this.insert(fanIndexSlidePic);

        fanIndexSlidePic.setSort(3);
        fanIndexSlidePic.setStatus(1);
        fanIndexSlidePic.setClasses(2);
        fanIndexSlidePic.setPicUrl("00/05/default_lunbo_3.png");

        this.insert(fanIndexSlidePic);

        return true;

    }
}
