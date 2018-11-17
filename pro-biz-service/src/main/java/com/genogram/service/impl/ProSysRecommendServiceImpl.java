package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamousPersonMapper;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.service.IProSysRecommendService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class ProSysRecommendServiceImpl extends ServiceImpl<FanSysRecommendMapper, FanSysRecommend> implements IProSysRecommendService {

    @Autowired
    private FanSysRecommendMapper fanSysRecommendMapper;

    @Autowired
    private FanNewsFamousPersonMapper fanNewsFamousPersonMapper;

    @Autowired
    private IFanNewsCultureNewsService fanNewsCultureNewsService;

    @Autowired
    private IFanNewsFamilyRecordService fanNewsFamilyRecordService;

    @Autowired
    private IFanNewsIndustryService fanNewsIndustryService;

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

    /**
     *省级首页县级推荐文章查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 15:18
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public List<IndustryDetailVo> getRecommendArticle(Map map) {
        List<IndustryDetailVo> industryDetailVo=fanSysRecommendMapper.getIndexRecommend(map);
        return industryDetailVo;
    }

    /**
     *省级首页县级推荐人物查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 18:18
     *@Param: 
     *@return:
     *@Description:
    */
    @Override
    public List<FamilyPersonVo> getRecommendFigure(Map map) {
        List<FamilyPersonVo> familyPersonVo=fanNewsFamousPersonMapper.getRecommendFigure(map);
        return familyPersonVo;
    }

    /**
     *省级首页文章推荐详情查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 19:10
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Object getRecommendParticulars(Integer id, Integer source) {
        //1代表家族文化 2 代表记录家族 3代表家族产业
        if(source==1){
            NewsDetailVo familyCultureDetail = fanNewsCultureNewsService.getFamilyCultureDetail(id);
            return familyCultureDetail;
        }else if(source==2){
            NewsDetailVo familyRecord = fanNewsFamilyRecordService.getFamilyRecord(id);
            return familyRecord;
        }else if(source==3){
            IndustryDetailVo familyIndustryDetail = fanNewsIndustryService.getFamilyIndustryDetail(id);
            return familyIndustryDetail;
        }else{
            return null;
        }
    }

    /**
     *省级首页人物推荐详情查询
     *@Author: yuzhou
     *@Date: 2018-11-17
     *@Time: 11:34
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public FamilyPersonVo getRecommendFigureParticulars(Integer id, Integer source) {

        return null;
    }
}
