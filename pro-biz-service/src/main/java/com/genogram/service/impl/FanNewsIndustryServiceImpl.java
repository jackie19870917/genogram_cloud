package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.mapper.FanNewsIndustryMapper;
import com.genogram.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联谊会-家族产业 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsIndustryServiceImpl extends ServiceImpl<FanNewsIndustryMapper, FanNewsIndustry> implements IProFanNewsIndustryService {


    @Autowired
    private IProFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IProSysRecommendService proSysRecommendService;

    /**
     *联谊会家族产业各个产业的详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public IndustryDetailVo getFamilyIndustryDetail(Integer id) {
        //根据Id查出产业详情
        FanNewsIndustry fanNewsIndustry = this.selectById(id);

        if(fanNewsIndustry==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsIndustry.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsIndustry.getCreateUser());
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsIndustry.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        IndustryDetailVo industryDetailVo=new IndustryDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsIndustry,industryDetailVo);
        //存储图片list集合
        industryDetailVo.setFanNewsUploadFileList(files);
        //存储作者名称时间
        industryDetailVo.setUpdateTimeLong(fanNewsIndustry.getUpdateTime().getTime());
        industryDetailVo.setCreateTimeLong(fanNewsIndustry.getCreateTime().getTime());
        industryDetailVo.setCreateUserName(null);
        industryDetailVo.setCreateUserName(null);
        return industryDetailVo;
    }
    /**
     *联谊会家族产业前台增加查看数
     *@Author: yuzhou
     *@Date: 2018-11-12
     *@Time: 13:56
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsIndustry fanNewsIndustry = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsIndustry.getVisitNum()+1;
        fanNewsIndustry.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsIndustry);
        if(visitNum > Constants.PRO_VISIT_NUM || visitNum.equals(Constants.PRO_VISIT_NUM)){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsIndustry.getShowId());
            fanSysRecommend.setNewsId(fanNewsIndustry.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
}
