package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanSysRecommend;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.CommonRecommendVo;

import java.util.List;
import java.util.Map;

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
     * @param fanSysRecommend  推荐实体类
     * @return
     */
    Boolean addRecommend(FanSysRecommend fanSysRecommend);

    /**
     * 联谊会后台设置个人推荐取消展示
     * @param recommendId 推荐表主键
     * @return
     */
    Boolean deleteRecommend(Integer recommendId);

    /**
     * 县级后台设置手动推荐查询
     * @param map
     * @return
     */
    List<CommonRecommendVo> getManualRecommend(Map map);

    /**
     * 联谊会后台点击取消
     * @param entity
     * @return
     */
    Boolean recommendDelete(Wrapper<FanSysRecommend> entity);
}
