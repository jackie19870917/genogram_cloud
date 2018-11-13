package com.genogram.service.impl;

import com.genogram.entity.FanSysRecommend;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.IFanSysRecommendService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联谊会文章推荐表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanSysRecommendServiceImpl extends ServiceImpl<FanSysRecommendMapper, FanSysRecommend> implements IFanSysRecommendService {


    @Override
    public void addRecommend(Integer showId, Integer id) {
        FanSysRecommend fanSysRecommend=new FanSysRecommend();
        fanSysRecommend.setNewsId(id);
        fanSysRecommend.setFanSysWebNewsShowId(showId);
        //插入时间
        fanSysRecommend.setCreateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setUpdateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setCreateUser(null);
        fanSysRecommend.setUpdateUser(null);
        boolean orUpdate = this.insertOrUpdate(fanSysRecommend);
    }
}
