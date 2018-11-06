package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.mapper.FanNewsIndustryMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsIndustryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class FanNewsIndustryServiceImpl extends ServiceImpl<FanNewsIndustryMapper, FanNewsIndustry> implements IFanNewsIndustryService {

    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;

    /**
     *联谊会家族产业查询
     * @param showId 显示位置Id
     * @param status 状态
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @param type 每页显示条数
     * @return
     *
     */
    @Override
    public Page<FamilyIndustryVo> getFamilyIndustryPage(Integer showId, Integer status, Integer pageNo, Integer pageSize,Integer type) {
        //返回新VO的集合
        List<FamilyIndustryVo> familyIndustryVoList=new ArrayList<>();

        //查询文章信息的条件
        Wrapper<FanNewsIndustry> entity = new EntityWrapper<FanNewsIndustry>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.eq("type",type);
        entity.orderBy("create_time", false);
        //分页查询产业文章主表
        Page<FanNewsIndustry> fanNewsCultureNews =this.selectPage(new Page<FanNewsIndustry>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsIndustry> list = fanNewsCultureNews.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if(list.size()==0){
            return null;
        }

        //得到所有产业文章id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileMapper.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyIndustryVo familyIndustryVo=new FamilyIndustryVo();
            familyIndustryVo.setId(news.getId());
            familyIndustryVo.setShowId(news.getShowId());
            familyIndustryVo.setNewsTitle(news.getNewsTitle());
            familyIndustryVo.setNewsText(news.getNewsText());
            familyIndustryVo.setVisitNum(news.getVisitNum());
            familyIndustryVo.setStatus(news.getStatus());
            familyIndustryVo.setCreateTime(news.getCreateTime());
            familyIndustryVo.setCreateUser(news.getCreateUser());
            familyIndustryVo.setUpdateTime(news.getUpdateTime());
            familyIndustryVo.setUpdateUser(news.getUpdateUser());


            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    fanNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyIndustryVo.setFanNewsUploadFileList(fanNewsUploadFile);


            //存储到新的集合中
            familyIndustryVoList.add(familyIndustryVo);
        });

        //重新设置page对象
        Page<FamilyIndustryVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyIndustryVoList);
        mapPage.setSize(fanNewsCultureNews.getSize());
        mapPage.setTotal(fanNewsCultureNews.getTotal());
        return mapPage;
    }
}
