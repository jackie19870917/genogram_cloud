package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.RecommendVo;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.IFanSysRecommendService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        fanSysRecommend.setShowId(showId);
        fanSysRecommend.setStatus(status);
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

    /**
     *联谊会后台设置推荐查询
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 11:39
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<RecommendVo> getRecommendPage(Wrapper<FanSysRecommend> entity,Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<RecommendVo> recommendVoList=new ArrayList<>();

        //查询分页
        Page<FanSysRecommend> fanSysRecommendPage = this.selectPage(new Page<FanSysRecommend>(pageNo, pageSize), entity);
        //去除list集合
        List<FanSysRecommend> list = fanSysRecommendPage.getRecords();
        if(list.size()==0){
            return null;
        }
        list.forEach(( news)->{
            RecommendVo recommendVo=new RecommendVo();

            //存储新对象
            BeanUtils.copyProperties(news,recommendVo);

            //转换时间为long
            recommendVo.setCreateTimeLong(news.getCreateTime().getTime());
            recommendVo.setUpdateTimeLong(news.getUpdateTime().getTime());

            //存储到新的集合中
            recommendVoList.add(recommendVo);
        });
        //重新设置page对象
        Page<RecommendVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(recommendVoList);
        mapPage.setSize(fanSysRecommendPage.getSize());
        mapPage.setTotal(fanSysRecommendPage.getTotal());

        return mapPage;
    }

}
