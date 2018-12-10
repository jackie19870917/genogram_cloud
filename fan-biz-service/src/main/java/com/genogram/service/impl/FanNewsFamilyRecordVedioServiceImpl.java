package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.*;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordVedioMapper;
import com.genogram.mapper.FanNewsUploadVedioMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    private IFanNewsUploadVedioService fanNewsUploadVedioService;

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IUploadFileService iuploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public Page<FamilyRecordVedioVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
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
        //查询所有文章id下的视频附件
        List<FanNewsUploadVedio> files =fanNewsUploadVedioService.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyRecordVedioVo familyRecordVedioVo=new FamilyRecordVedioVo();
            //copy bean
            BeanUtils.copyProperties(news,familyRecordVedioVo);

            //判断文章id是否一样
            List<FanNewsUploadVedio> fanNewsUploadVedio=new ArrayList<>();
            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    fanNewsUploadVedio.add(data);
                }
            });
            //存储视频list集合
            familyRecordVedioVo.setFanUploadVedioList(fanNewsUploadVedio);

            //设置封面file
            this.getPicIndex(familyRecordVedioVo,news.getId(),showId);

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

    private void getPicIndex(FamilyRecordVedioVo vo,int newsId,int showId){
        Wrapper<FanNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        entity.eq("status", 1);
        entity.eq("pic_index", 1);
        FanNewsUploadFile file = fanNewsUploadFileService.selectOne(entity);
        List list = new ArrayList();
        list.add(file);
        vo.setFanNewsUploadFile(list);
    }


    @Override
    public NewsDetailVo getFamilyVedioRecord(Integer id) {
        //根据Id查出记录家族详情
        FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio = this.selectById(id);

        if(fanNewsFamilyRecordVedio==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamilyRecordVedio.getShowId());
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo=new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecordVedio,newsDetailVo);
        //存储图片list集合
        newsDetailVo.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTimeLong(fanNewsFamilyRecordVedio.getUpdateTime().getTime());
        newsDetailVo.setCreateTimeLong(fanNewsFamilyRecordVedio.getCreateTime().getTime());
        newsDetailVo.setCreateUserName(null);
        newsDetailVo.setCreateUserName(null);
        return newsDetailVo;
    }

    @Override
    public FamilyRecordVedioVo getFamilyVedioDetilRecord(Integer id) {
        //根据Id查出记录家族详情
        FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio = this.selectById(id);

        if(fanNewsFamilyRecordVedio==null){
            return null;
        }

        //查询视频
        Wrapper<FanNewsUploadVedio> uploadentity = new EntityWrapper<FanNewsUploadVedio>();
        uploadentity.eq("news_id",fanNewsFamilyRecordVedio.getId());
        uploadentity.eq("show_id", fanNewsFamilyRecordVedio.getShowId());
        uploadentity.eq("status", 1);
        //查询所有文章id下的视频附件
        List<FanNewsUploadVedio> files =fanNewsUploadVedioService.selectList(uploadentity);



        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        FamilyRecordVedioVo familyRecordVedioVo=new FamilyRecordVedioVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecordVedio,familyRecordVedioVo);
        //存储图片list集合
        //设置封面file
        this.getPicIndex(familyRecordVedioVo,familyRecordVedioVo.getId(),familyRecordVedioVo.getShowId());
        familyRecordVedioVo.setFanUploadVedioList(files);
        //存储作者名称时间
        familyRecordVedioVo.setUpdateTimeLong(fanNewsFamilyRecordVedio.getUpdateTime().getTime());
        familyRecordVedioVo.setCreateTimeLong(fanNewsFamilyRecordVedio.getCreateTime().getTime());
        familyRecordVedioVo.setCreateUserName(null);
        familyRecordVedioVo.setCreateUserName(null);
        return familyRecordVedioVo;
    }

    @Override
    public boolean addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(fanNewsFamilyRecordVedio.getId()==null){
            //存入创建时间
            fanNewsFamilyRecordVedio.setCreateTime(format);
            fanNewsFamilyRecordVedio.setCreateUser(null);
            //插入修改时间
            fanNewsFamilyRecordVedio.setUpdateTime(format);
            fanNewsFamilyRecordVedio.setUpdateUser(null);
        }else{
            //存入修改时间
            fanNewsFamilyRecordVedio.setUpdateTime(format);
            fanNewsFamilyRecordVedio.setUpdateUser(null);
        }
        boolean result = this.insertOrUpdate(fanNewsFamilyRecordVedio);
        //存储图片
        if(result){
            iuploadFileService.storageFanFile(picfileName,picPath,fanNewsFamilyRecordVedio.getId(),fanNewsFamilyRecordVedio.getShowId());
            iuploadFileService.storageFanVedio(vedioFileName,vedioPath,fanNewsFamilyRecordVedio.getId(),fanNewsFamilyRecordVedio.getShowId());
        }
        return result;
    }

    @Override
    public Boolean deleteVedioRecordById(Integer id, int status) {
        FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio = this.selectById(id);
        fanNewsFamilyRecordVedio.setStatus(status);
        fanNewsFamilyRecordVedio.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人 待写
        boolean result = this.updateAllColumnById(fanNewsFamilyRecordVedio);
        return result;
    }
}
