package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entity.FanNewsUploadVedio;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordVedioMapper;
import com.genogram.mapper.FanNewsUploadVedioMapper;
import com.genogram.service.IFanNewsFamilyRecordVedioService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录家族视频上传-视频概要 服务实现类
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamilyRecordVedioServiceImpl extends ServiceImpl<FanNewsFamilyRecordVedioMapper, FanNewsFamilyRecordVedio> implements IFanNewsFamilyRecordVedioService {
    @Autowired
    private FanNewsUploadVedioMapper fanNewsUploadVedioMapper;

    @Override
    public Page<FamilyRecordVedioVo> getFamilyRecordVedioVo(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyRecordVedioVo> familyRecordVedioVoList=new ArrayList<>();

        Wrapper<FanNewsFamilyRecordVedio> entity = new EntityWrapper<FanNewsFamilyRecordVedio>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询视频主表
        Page<FanNewsFamilyRecordVedio> fanNewsFamilyRecordVedio =this.selectPage(new Page<FanNewsFamilyRecordVedio>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsFamilyRecordVedio> list = fanNewsFamilyRecordVedio.getRecords();
        if(list.size()==0){
            return null;
        }
        //得到所有视频id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });
        //查询视频文字
        Wrapper<FanNewsUploadVedio> uploadentity = new EntityWrapper<FanNewsUploadVedio>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadVedio> files =fanNewsUploadVedioMapper.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyRecordVedioVo familyRecordVedioVo=new FamilyRecordVedioVo();
            familyRecordVedioVo.setId(news.getId());
            familyRecordVedioVo.setShowId(news.getShowId());
            familyRecordVedioVo.setVedioType(news.getVedioType());
            familyRecordVedioVo.setTitle(news.getTitle());
            familyRecordVedioVo.setAuth(news.getAuth());
            familyRecordVedioVo.setStatus(news.getStatus());
            familyRecordVedioVo.setCreateTime(news.getCreateTime());
            familyRecordVedioVo.setCreateUser(news.getCreateUser());
            familyRecordVedioVo.setUpdateTime(news.getUpdateTime());
            familyRecordVedioVo.setUpdateUser(news.getUpdateUser());

            //判断改文章id是否一样
            List<FanNewsUploadVedio> fanNewsUploadVedio=new ArrayList<>();
            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    fanNewsUploadVedio.add(data);
                }
            });
            //存储视频list集合
            familyRecordVedioVo.setFanUploadVedioList(fanNewsUploadVedio);
            //存储到新的集合中
            familyRecordVedioVoList.add(familyRecordVedioVo);
        });
        //重新设置page对象
        Page<FamilyRecordVedioVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyRecordVedioVoList);
        mapPage.setSize(fanNewsFamilyRecordVedio.getSize());
        mapPage.setTotal(fanNewsFamilyRecordVedio.getTotal());

        return mapPage;
    }

    @Override
    public NewsDetailVo getFamilyVedioRecord(Integer id) {
        return null;
    }

    @Override
    public boolean addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String VedioPath) {
        return false;
    }

    @Override
    public Boolean deleteVedioRecordById(Integer id, int status) {
        return null;
    }
}
