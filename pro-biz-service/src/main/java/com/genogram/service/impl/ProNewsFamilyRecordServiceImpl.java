package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.*;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProFamilyRecordVo;
import com.genogram.mapper.ProNewsFamilyRecordMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProNewsFamilyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IProNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级-记录家族-家族动态,家族通告文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsFamilyRecordServiceImpl extends ServiceImpl<ProNewsFamilyRecordMapper, ProNewsFamilyRecord> implements IProNewsFamilyRecordService {
    @Autowired
    private IProNewsUploadFileService proNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUploadFileService iUploadFileService;
    /**
     * 前后台查询
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<ProFamilyRecordVo> getProFamilyRecordPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<ProFamilyRecordVo> proFamilyRecordVoList=new ArrayList<>();

        Wrapper<ProNewsFamilyRecord> entity = new EntityWrapper<ProNewsFamilyRecord>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("is_top",false);
        entity.orderBy("create_time", false);
        //分页查询文章主表
        Page<ProNewsFamilyRecord> proNewsFamilyRecord =this.selectPage(new Page<ProNewsFamilyRecord>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ProNewsFamilyRecord> list = proNewsFamilyRecord.getRecords();
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
        List<ProNewsUploadFile> files =  proNewsUploadFileService.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            ProFamilyRecordVo proFamilyRecordVo=new ProFamilyRecordVo();
            proFamilyRecordVo.setId(news.getId());
            proFamilyRecordVo.setShowId(news.getShowId());
            proFamilyRecordVo.setNewsTitle(news.getNewsTitle());
            proFamilyRecordVo.setNewsText(news.getNewsText());
            proFamilyRecordVo.setVisitNum(news.getVisitNum());
            proFamilyRecordVo.setStatus(news.getStatus());
            proFamilyRecordVo.setCreateTime(news.getCreateTime());
            proFamilyRecordVo.setCreateUser(news.getCreateUser());
            proFamilyRecordVo.setUpdateTime(news.getUpdateTime());
            proFamilyRecordVo.setUpdateUser(news.getUpdateUser());

            //判断改图片文章id是否一样
            List<ProNewsUploadFile> proNewsUploadFiles=new ArrayList<>();
            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    proNewsUploadFiles.add(data);
                }
            });
            //存储图片list集合
            proFamilyRecordVo.setProNewsUploadFiles(proNewsUploadFiles);
            //存储到新的集合中
            proFamilyRecordVoList.add(proFamilyRecordVo);
        });
        //重新设置page对象
        Page<ProFamilyRecordVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(proFamilyRecordVoList);
        mapPage.setSize(proNewsFamilyRecord.getSize());
        mapPage.setTotal(proNewsFamilyRecord.getTotal());

        return mapPage;
    }

    /**
     * 详情
     * @param id  主键
     * @return
     */
    @Override
    public NewsDetailVo getProFamilyRecord(Integer id) {
        //根据Id查出记录家族详情
        ProNewsFamilyRecord proNewsFamilyRecord = this.selectById(id);

        if(proNewsFamilyRecord==null){
            return null;
        }

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<ProNewsUploadFile>();
        uploadentity.eq("show_id", proNewsFamilyRecord.getShowId());
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files =  proNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo=new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(proNewsFamilyRecord,newsDetailVo);
        //存储图片list集合
        newsDetailVo.setProNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTimeLong(proNewsFamilyRecord.getUpdateTime().getTime());
        newsDetailVo.setCreateTimeLong(proNewsFamilyRecord.getCreateTime().getTime());
        newsDetailVo.setCreateUserName(null);
        newsDetailVo.setCreateUserName(null);
        return newsDetailVo;
    }

    /**
     *联谊会记录家族后台新增 修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public boolean addOrUpdateRecord(ProNewsFamilyRecord proNewsFamilyRecord, String fileName, String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(proNewsFamilyRecord.getId()==null){
            //存入创建时间
            proNewsFamilyRecord.setCreateTime(format);
            proNewsFamilyRecord.setCreateUser(null);
            //插入修改时间
            proNewsFamilyRecord.setUpdateTime(format);
            proNewsFamilyRecord.setUpdateUser(null);
        }else{
            //存入修改时间
            proNewsFamilyRecord.setUpdateTime(format);
            proNewsFamilyRecord.setUpdateUser(null);
        }
        boolean result = this.insertOrUpdate(proNewsFamilyRecord);
        //存储图片
        if(result && StringsUtils.isNotEmpty(filePath)){
            iUploadFileService.storageFanFiles(fileName,proNewsFamilyRecord.getId(),proNewsFamilyRecord.getShowId());
        }
        return result;
    }

    /**
     * 删除
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean deleteProRecordById(Integer id, int status) {
        ProNewsFamilyRecord proNewsFamilyRecord = this.selectById(id);
        proNewsFamilyRecord.setStatus(status);
        proNewsFamilyRecord.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人 待写
        boolean result = this.updateAllColumnById(proNewsFamilyRecord);
        return result;
    }

}
