package com.genogram.service;

import com.genogram.entity.FanSysRecommend;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 联谊会文章推荐表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanSysRecommendService extends IService<FanSysRecommend> {


    void addRecommend(Integer showId, Integer id);
}
