package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

    /**
     *省级后台点击取消
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:38
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteRecommend(Wrapper<FanSysRecommend> entity, int status) {
        //查询文章
        FanSysRecommend fanSysRecommend = this.selectOne(entity);
        //修改状态
        fanSysRecommend.setStatus(status);
        //修改人
        fanSysRecommend.setUpdateUser(null);
        //修改时间
        fanSysRecommend.setUpdateTime(DateUtil.getCurrentTimeStamp());
        boolean result = this.updateAllColumnById(fanSysRecommend);
        return result;
    }

    /**
     *省级后台设置推荐查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:41
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<FanSysRecommend> getRecommendPage(Wrapper<FanSysRecommend> entity, Integer pageNo, Integer pageSize) {
        //查询分页
        Page<FanSysRecommend> fanSysRecommendPage = this.selectPage(new Page<FanSysRecommend>(pageNo, pageSize), entity);
        return fanSysRecommendPage;
    }
}
