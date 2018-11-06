package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.mapper.FanNewsCultureNewsMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsCultureNewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族文化文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCultureNewsServiceImpl extends ServiceImpl<FanNewsCultureNewsMapper, FanNewsCultureNews> implements IFanNewsCultureNewsService {

    @Autowired
    private FanNewsCultureNewsMapper fanNewsCultureNewsMapper;

    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;

    @Override
    public Page<FamilyCultureVo> familyCulture(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //要返回的集合
        List<FamilyCultureVo> familyCultureVoList=new ArrayList<>();

        Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        Page<FanNewsCultureNews> fanNewsCultureNews =this.selectPage(new Page<FanNewsCultureNews>(pageNo, pageSize), entity);
        List<FanNewsCultureNews> list = fanNewsCultureNews.getRecords();
        if(list.size()==0){
            return null;
        }
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });
        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        List<FanNewsUploadFile> files =  fanNewsUploadFileMapper.selectList(uploadentity);


        //遍历主表文章集合
        list.forEach(( news)->{
            FamilyCultureVo familyCultureVo=new FamilyCultureVo();
            familyCultureVo.setId(news.getId());
            familyCultureVo.setShowId(news.getShowId());
            familyCultureVo.setNewsTitle(news.getNewsTitle());
            familyCultureVo.setNewsText(news.getNewsText());
            familyCultureVo.setVisitNum(news.getVisitNum());
            familyCultureVo.setStatus(news.getStatus());
            familyCultureVo.setCreateTime(news.getCreateTime());
            familyCultureVo.setCreateUser(news.getCreateUser());
            familyCultureVo.setUpdateTime(news.getUpdateTime());
            familyCultureVo.setUpdateUser(news.getUpdateUser());
            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile=new ArrayList<>();
            files.forEach(( data)->{
                if(news.getId()==data.getNewsId()){
                    fanNewsUploadFile.add(data);
                }
            });
            //存储图片集合
            familyCultureVo.setFanNewsUploadFileList(fanNewsUploadFile);
            //存储到集合中
            familyCultureVoList.add(familyCultureVo);
        });
        Page<FamilyCultureVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(fanNewsCultureNews.getSize());
        mapPage.setTotal(fanNewsCultureNews.getTotal());
        /*Page<FamilyCultureVo> mapPage = new Page<>(pageNo,pageSize);
        Map requestParam = new HashMap<>();
        requestParam.put("showId",showId);
        requestParam.put("status",status);
        List<FamilyCultureVo> myItems =fanNewsCultureNewsMapper.queryMyItems(mapPage, requestParam);
        mapPage.setRecords(myItems);*/
        return mapPage;
    }
}
