package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.*;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.mapper.ProNewsFamousPersonMapper;
import com.genogram.mapper.ProNewsUploadFileMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级-家族名人-祖先-名人精英-组织架构 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-17
 */
@Service
public class ProNewsFamousPersonServiceImpl extends ServiceImpl<ProNewsFamousPersonMapper, ProNewsFamousPerson> implements IProNewsFamilyPersionService {
    @Autowired
    private IAllUserLoginService allUserLoginService;
    @Autowired
    private IProSysRecommendService proSysRecommendService;

    @Autowired
    private IProNewsUploadFileService iProNewsUploadFileService;
    @Autowired
    private ProNewsUploadFileMapper ProNewsUploadFileMapper;

    @Override
    public Page<ProFamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<ProFamilyPersonVo> familyPersonVoList=new ArrayList<>();

        Wrapper<ProNewsFamousPerson> entity = new EntityWrapper<ProNewsFamousPerson>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询文章主表
        Page<ProNewsFamousPerson> proNewsFamousPerson =this.selectPage(new Page<ProNewsFamousPerson>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ProNewsFamousPerson> list = proNewsFamousPerson.getRecords();
        if(list.size()==0){
            return null;
        }

        //得到所有文章id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<ProNewsUploadFile>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files =  ProNewsUploadFileMapper.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            ProFamilyPersonVo proFamilyPersonVo=new ProFamilyPersonVo();
            proFamilyPersonVo.setId(news.getId());
            proFamilyPersonVo.setShowId(news.getShowId());
            proFamilyPersonVo.setPersonName(news.getPersonName());
            proFamilyPersonVo.setPersonSummary(news.getPersonSummary());
            proFamilyPersonVo.setVisitNum(news.getVisitNum());
            proFamilyPersonVo.setStatus(news.getStatus());
            proFamilyPersonVo.setCreateTime(news.getCreateTime());
            proFamilyPersonVo.setCreateUser(news.getCreateUser());
            proFamilyPersonVo.setUpdateTime(news.getUpdateTime());
            proFamilyPersonVo.setUpdateUser(news.getUpdateUser());


            //判断改图片文章id是否一样
            List<ProNewsUploadFile> proNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    proNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            proFamilyPersonVo.setProNewsUploadFilesList(proNewsUploadFile);


            //存储到新的集合中
            familyPersonVoList.add(proFamilyPersonVo);

        });
        //重新设置page对象
        Page<ProFamilyPersonVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyPersonVoList);
        mapPage.setSize(proNewsFamousPerson.getSize());
        mapPage.setTotal(proNewsFamousPerson.getTotal());
        return mapPage;
    }

    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        ProNewsFamousPerson fanNewsFamousPerson = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsFamousPerson.getVisitNum()+1;
        fanNewsFamousPerson.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsFamousPerson);
        if(visitNum >200 || visitNum==200){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsFamousPerson.getShowId());
            fanSysRecommend.setNewsId(fanNewsFamousPerson.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
    /**
     *联谊会家族名人详情查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:22
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public ProFamilyPersonVo getFamilyFamilyDetail(Integer id) {

        //根据Id查出文章详情
        ProNewsFamousPerson proNewsFamousPerson=  this.selectById(id);

        if (proNewsFamousPerson==null){
            return null;
        }

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<ProNewsUploadFile>();
        uploadentity.eq("show_id", proNewsFamousPerson.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files =  iProNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(proNewsFamousPerson.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(proNewsFamousPerson.getCreateUser());

        //返回新VO的集合赋值新对象vo
        ProFamilyPersonVo proFamilyPersonVo=new ProFamilyPersonVo();

        //调用方法封装集合
        BeanUtils.copyProperties(proNewsFamousPerson,proFamilyPersonVo);
        //存储图片list集合
        proFamilyPersonVo.setProNewsUploadFilesList(files);
        //存储作者名称时间
        proFamilyPersonVo.setUpdateTimeLong(proNewsFamousPerson.getUpdateTime().getTime());
        proFamilyPersonVo.setCreateTimeLong(proNewsFamousPerson.getCreateTime().getTime());
        proFamilyPersonVo.setCreateUserName(null);
        proFamilyPersonVo.setCreateUserName(null);
        return proFamilyPersonVo;
    }
}
