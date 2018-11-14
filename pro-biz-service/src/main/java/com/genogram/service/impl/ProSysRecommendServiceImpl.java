package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysRecommend;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.IProSysRecommendService;
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
public class ProSysRecommendServiceImpl extends ServiceImpl<FanSysRecommendMapper, FanSysRecommend> implements IProSysRecommendService {

    @Override
    public Boolean addRecommend(FanSysRecommend fanSysRecommend) {
        //是否全国显示(0:否;1是)
        fanSysRecommend.setIsCountry(0);
        //是否省显示(0:否;1是)
        fanSysRecommend.setIsProvince(0);
        //插入时间
        fanSysRecommend.setCreateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setUpdateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setCreateUser(null);
        fanSysRecommend.setUpdateUser(null);
        boolean result = this.insert(fanSysRecommend);
        return result;
    }
}
