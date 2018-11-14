package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanSysRecommend;

/**
 * <p>
 * 联谊会文章推荐表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IProSysRecommendService extends IService<FanSysRecommend> {


    /**
     * 省级后台点击推荐
     * @param fanSysRecommend  推荐实体类
     * @return
     */
    Boolean addRecommend(FanSysRecommend fanSysRecommend);

    /**
     * 省级后台点击取消
     * @param entity
     * @param status
     * @return
     */
    Boolean deleteRecommend(Wrapper<FanSysRecommend> entity, int status);

    /**
     * 省级后台设置推荐查询
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanSysRecommend> getRecommendPage(Wrapper<FanSysRecommend> entity, Integer pageNo, Integer pageSize);
}
