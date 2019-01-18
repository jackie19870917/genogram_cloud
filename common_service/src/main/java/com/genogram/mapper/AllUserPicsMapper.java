package com.genogram.mapper;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllUserPics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entity.PuBaseInfo;

/**
 * <p>
 * 个人照片 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface AllUserPicsMapper extends BaseMapper<AllUserPics> {

    /**
     * <p>
     * 服务类
     * </p>
     *
     * @author yizx
     * @since 2019-01-16
     */
    interface IPuBaseInfoService extends IService<PuBaseInfo> {

    }
}
