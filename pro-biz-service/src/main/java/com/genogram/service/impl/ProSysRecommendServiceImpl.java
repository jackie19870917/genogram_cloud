package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamousPersonMapper;
import com.genogram.mapper.FanSysRecommendMapper;
import com.genogram.service.*;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private IFanNewsFamousPersonService fanNewsFamousPersonService;

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;


    /**
     *省级后台点击推荐
     *@Author: yuzhou
     *@Date: 2018-11-27
     *@Time: 14:23
     *@Param:
     *@return:
     *@Description:
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
     *省级后台设置个人推荐取消展示
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:38
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteRecommend(Wrapper<FanSysRecommend> entity, int status) {
        boolean result=false;
        //查询文章
        List<FanSysRecommend> list = this.selectList(entity);
        for (FanSysRecommend fanSysRecommend : list) {
            //修改状态
            fanSysRecommend.setStatus(status);
            //修改人
            fanSysRecommend.setUpdateUser(null);
            //修改时间
            fanSysRecommend.setUpdateTime(DateUtil.getCurrentTimeStamp());
            result = this.updateAllColumnById(fanSysRecommend);
        }
        return result;
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
        //得到所有文章Id
        List<Integer> ids= new ArrayList<>();
        //得到所有showId
        List<Integer> showIds= new ArrayList<>();
        for (IndustryDetailVo detailVo : industryDetailVo) {
            ids.add(detailVo.getId());
            showIds.add((detailVo.getShowId()));
        }
        //查询附件
        Wrapper<FanNewsUploadFile> entity=new EntityWrapper();
        entity.in("id",ids);
        entity.in("show_id",showIds);
        List<FanNewsUploadFile> fanNewsUploadFiles = fanNewsUploadFileService.selectList(entity);
        //封装附件
            for (IndustryDetailVo detailVo : industryDetailVo) {
                for (FanNewsUploadFile fanNewsUploadFile : fanNewsUploadFiles) {
                    if(detailVo.getId().equals(fanNewsUploadFile.getNewsId()) && detailVo.getShowId().equals(fanNewsUploadFile.getShowId())){
                        List<FanNewsUploadFile> list=new ArrayList<>();
                        list.add(fanNewsUploadFile);
                        detailVo.setFanNewsUploadFileList(list);
                    }
                }
            }
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
        //1代表家族文化 3 代表记录家族 2代表家族产业
        if(source==1){
            NewsDetailVo familyCultureDetail = fanNewsCultureNewsService.getFamilyCultureDetail(id);
            if(familyCultureDetail!=null){
                fanNewsCultureNewsService.addVisitNum(id);
            }
            return familyCultureDetail;
        }else if(source==3){
            NewsDetailVo familyRecord = fanNewsFamilyRecordService.getFamilyRecord(id);
            if(familyRecord!=null){
                fanNewsFamilyRecordService.addVisitNum(id);
            }
            return familyRecord;
        }else if(source==2){
            IndustryDetailVo familyIndustryDetail = fanNewsIndustryService.getFamilyIndustryDetail(id);
            if(familyIndustryDetail!=null){
                fanNewsIndustryService.addVisitNum(id);
            }
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
    public FamilyPersonVo getRecommendFigureParticulars(Integer id) {
        FamilyPersonVo familyFamilyDetail = fanNewsFamousPersonService.getFamilyFamilyDetail(id);
        //增加查看数
        if(familyFamilyDetail!=null){
            fanNewsFamousPersonService.addVisitNum(id);
        }
        return familyFamilyDetail;
    }

    /**
     *省级手动文章查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 15:40
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public List<CommonRecommendVo> getManualRecommend(Map map) {
        List<CommonRecommendVo> manualRecommend = fanSysRecommendMapper.getManualRecommend(map);
        return manualRecommend;
    }

    /**
     *省级后台设置手动推荐到全国
     *@Author: yuzhou
     *@Date: 2018-11-28
     *@Time: 15:15
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public List<CommonRecommendVo> getManuaRecommendNationwide(Map map) {
        List<CommonRecommendVo> commonRecommendVo=fanSysRecommendMapper.getManuaRecommendNationwide(map);
        return commonRecommendVo;
    }
}
