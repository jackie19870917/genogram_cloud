package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
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


    /**
     * 联谊会后台点击推荐
     * @param showId  文章显示位置ID
     * @param id  主键
     * @param status  状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
     * @return
     */
    Boolean addRecommend(Integer showId, Integer id,Integer status);

    /**
     * 联谊会后台点击取消
     * @param entity  查询条件
     * @param status  状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
     * @return
     */
    Boolean deleteRecommend(Wrapper<FanSysRecommend> entity, int status);

}
