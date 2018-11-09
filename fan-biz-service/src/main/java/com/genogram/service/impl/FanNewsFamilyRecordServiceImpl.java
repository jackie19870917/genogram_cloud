package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-记录家族-家族动态,家族通告文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamilyRecordServiceImpl extends ServiceImpl<FanNewsFamilyRecordMapper, FanNewsFamilyRecord> implements IFanNewsFamilyRecordService {
    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;

    @Autowired
    private FanNewsFamilyRecordMapper fanNewsFamilyRecordMapper;

    @Override
    public Page<FamilyRecordVo> getFamilyRecordPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyRecordVo> familyRecordVoList=new ArrayList<>();

        Wrapper<FanNewsFamilyRecord> entity = new EntityWrapper<FanNewsFamilyRecord>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询文章主表
        Page<FanNewsFamilyRecord> fanNewsFamilyRecord =this.selectPage(new Page<FanNewsFamilyRecord>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsFamilyRecord> list = fanNewsFamilyRecord.getRecords();
        if(list.size()==0){
            return null;
        }

        //得到所有文章id
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
            FamilyRecordVo familyRecordVo=new FamilyRecordVo();
            familyRecordVo.setId(news.getId());
            familyRecordVo.setShowId(news.getShowId());
            familyRecordVo.setNewsTitle(news.getNewsTitle());
            familyRecordVo.setNewsText(news.getNewsText());
            familyRecordVo.setVisitNum(news.getVisitNum());
            familyRecordVo.setStatus(news.getStatus());
            familyRecordVo.setCreateTime(news.getCreateTime());
            familyRecordVo.setCreateUser(news.getCreateUser());
            familyRecordVo.setUpdateTime(news.getUpdateTime());
            familyRecordVo.setUpdateUser(news.getUpdateUser());


            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    fanNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyRecordVo.setFanNewsUploadFileList(fanNewsUploadFile);


            //存储到新的集合中
            familyRecordVoList.add(familyRecordVo);


        });
        //重新设置page对象
        Page<FamilyRecordVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyRecordVoList);
        mapPage.setSize(fanNewsFamilyRecord.getSize());
        mapPage.setTotal(fanNewsFamilyRecord.getTotal());

        return mapPage;
    }
}
