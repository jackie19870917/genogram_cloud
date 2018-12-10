package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.mapper.ProIndexSlidePicMapper;
import com.genogram.service.IFanProIndexSlidePicService;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author keriezhang
 * @date 2016/10/31
 */
@Service
public class FanProIndexSlidePicServiceImpl extends ServiceImpl<ProIndexSlidePicMapper, ProIndexSlidePic> implements IFanProIndexSlidePicService {

    @Override
    public Boolean insertProIndexSlidePic(ProIndexSlidePic proIndexSlidePic) {

        Timestamp timestamp = DateUtil.getCurrentTimeStamp();

        proIndexSlidePic.setCreateTime(timestamp);
        proIndexSlidePic.setUpdateTime(timestamp);

        proIndexSlidePic.setSort(1);
        proIndexSlidePic.setStatus(1);
        proIndexSlidePic.setPicUrl("00/05/default_lunbo_1.jpg");


        this.insert(proIndexSlidePic);

        proIndexSlidePic.setSort(2);
        proIndexSlidePic.setStatus(1);
        proIndexSlidePic.setPicUrl("00/05/default_lunbo_2.png");

        this.insert(proIndexSlidePic);

        proIndexSlidePic.setSort(3);
        proIndexSlidePic.setStatus(1);
        proIndexSlidePic.setPicUrl("00/05/default_lunbo_3.png");

        this.insert(proIndexSlidePic);

        return true;
    }
}
