package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProIndexSlidePic;

/**
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public interface IFanProIndexSlidePicService extends IService<ProIndexSlidePic> {

    /**
     * 新增  轮播图
     * @param proIndexSlidePic
     * @return
     */
    Boolean insertProIndexSlidePic(ProIndexSlidePic proIndexSlidePic);
}
