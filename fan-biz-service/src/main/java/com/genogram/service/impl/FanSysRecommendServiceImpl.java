package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.IFanSysRecommendService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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

    @Autowired
    private FanSysRecommendMapper fanSysRecommendMapper;

    /**
     * 联谊会后台点击推荐
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 9:51
     * @Param:
     * @return:
     * @Description:
     */
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
     * 联谊会后台设置个人推荐取消展示
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 10:06
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean deleteRecommend(Integer recommend) {
        boolean result = this.deleteById(recommend);
        return result;
    }

    /**
     * 县级后台设置手动推荐查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-19
     * @Time: 10:10
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public List<CommonRecommendVo> getManualRecommend(Map map) {
        List<CommonRecommendVo> commonRecommendVo = fanSysRecommendMapper.getCountyManualRecommend(map);
        return commonRecommendVo;
    }

    /**
     * 联谊会后台点击取消
     *
     * @Author: yuzhou
     * @Date: 2018-11-26
     * @Time: 17:14
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean recommendDelete(Wrapper<FanSysRecommend> entity) {
        //status 数据库删除一条数据
        Boolean aBoolean = this.delete(entity);
        return aBoolean;
    }

}
