package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.ProNewsFamilyRecordVedio;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entity.ProNewsUploadVedio;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.ProFamilyRecordVedioVo;
import com.genogram.mapper.ProNewsFamilyRecordVedioMapper;
import com.genogram.service.IProNewsFamilyRecordVedioService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IProNewsUploadFileService;
import com.genogram.service.IProNewsUploadVedioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级记录家族视频上传-视频概要 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-17
 */
@Service
public class ProNewsFamilyRecordVedioServiceImpl extends ServiceImpl<ProNewsFamilyRecordVedioMapper, ProNewsFamilyRecordVedio> implements IProNewsFamilyRecordVedioService {

    @Autowired
    private IProNewsUploadFileService iProNewsUploadFileService;
    @Autowired
    private IProNewsUploadVedioService iProNewsUploadVedioService;
    @Override
    public Page<FamilyRecordVedioVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyRecordVedioVo> familyRecordVedioVoList=new ArrayList<>();

        Wrapper<ProNewsFamilyRecordVedio> entity = new EntityWrapper<ProNewsFamilyRecordVedio>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询视频主表
        Page<ProNewsFamilyRecordVedio> proNewsFamilyRecordVedioPage =this.selectPage(new Page<ProNewsFamilyRecordVedio>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ProNewsFamilyRecordVedio> list = proNewsFamilyRecordVedioPage.getRecords();
        if(list.size()==0){
            return null;
        }
        //得到所有视频id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });
        //查询视频文字
        Wrapper<ProNewsUploadVedio> uploadentity = new EntityWrapper<ProNewsUploadVedio>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的视频附件
        List<ProNewsUploadVedio> files =iProNewsUploadVedioService.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            ProFamilyRecordVedioVo familyRecordVedioVo=new ProFamilyRecordVedioVo();
            //copy bean
            BeanUtils.copyProperties(news,familyRecordVedioVo);

            //判断文章id是否一样
            List<ProNewsUploadVedio> proNewsUploadVedio=new ArrayList<>();
            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    proNewsUploadVedio.add(data);
                }
            });
            //存储视频list集合
            familyRecordVedioVo.setProNewsFamilyRecordVedios(list);

            //设置封面file
            this.getPicIndex(familyRecordVedioVo,news.getId(),showId);

            //存储到新的集合中
            familyRecordVedioVoList.add(familyRecordVedioVo);
        });
        //重新设置page对象
        Page<FamilyRecordVedioVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyRecordVedioVoList);
        mapPage.setSize(proNewsFamilyRecordVedioPage.getSize());
        mapPage.setTotal(proNewsFamilyRecordVedioPage.getTotal());

        return mapPage;
    }
    private void getPicIndex(ProFamilyRecordVedioVo vo, int newsId, int showId){
        Wrapper<ProNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        entity.eq("status", 1);
        entity.eq("pic_index", 1);
        ProNewsUploadFile file = iProNewsUploadFileService.selectOne(entity);
        List list = new ArrayList();
        list.add(file);
        vo.setProNewsUploadFile(list);
    }
}
