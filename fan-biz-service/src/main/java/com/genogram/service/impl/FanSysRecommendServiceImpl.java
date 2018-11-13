package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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


    /**
     *联谊会后台点击推荐
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 9:51
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean addRecommend(Integer showId, Integer id,Integer status) {
        //要插入的实体类
        FanSysRecommend fanSysRecommend=new FanSysRecommend();
        fanSysRecommend.setNewsId(id);
        fanSysRecommend.setFanSysWebNewsShowId(showId);
        fanSysRecommend.setStatus(status);
        //插入时间
        fanSysRecommend.setCreateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setUpdateTime(DateUtil.getCurrentTimeStamp());
        fanSysRecommend.setCreateUser(null);
        fanSysRecommend.setUpdateUser(null);
        boolean result = this.insert(fanSysRecommend);
        return result;
    }

    /**
     *联谊会后台点击取消推荐
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:06
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

}
