package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsUploadFile;

import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IUploadFileService iuploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 前后台查询
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
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
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

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

    /**
     * 记录家族详情
     * @param id  主键
     * @return
     */
    @Override
    public NewsDetailVo getFamilyRecord(Integer id) {
        //根据Id查出记录家族详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);

        if(fanNewsFamilyRecord==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamilyRecord.getShowId());
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsFamilyRecord.getCreateUser());
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsFamilyRecord.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo=new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecord,newsDetailVo);
        //存储图片list集合
        newsDetailVo.setFanNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTimeLong(fanNewsFamilyRecord.getUpdateTime().getTime());
        newsDetailVo.setCreateTimeLong(fanNewsFamilyRecord.getCreateTime().getTime());
        newsDetailVo.setCreateUserName(createUser.getRealName());
        newsDetailVo.setCreateUserName(updateUser.getRealName());
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
    public boolean addOrUpdateRecord(FanNewsFamilyRecord fanNewsRecord, String fileName,String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(fanNewsRecord.getId()==null){
            //存入创建时间
            fanNewsRecord.setCreateTime(format);
            fanNewsRecord.setCreateUser(null);
            //插入修改时间
            fanNewsRecord.setUpdateTime(format);
            fanNewsRecord.setUpdateUser(null);
        }else{
            //存入修改时间
            fanNewsRecord.setUpdateTime(format);
            fanNewsRecord.setUpdateUser(null);
        }
        boolean result = this.insertOrUpdate(fanNewsRecord);
        //存储图片
        if(result && StringUtils.isNotEmpty(filePath)){
            iuploadFileService.storageFanFile(fileName,filePath,fanNewsRecord.getId(),fanNewsRecord.getShowId());
        }
        return result;
    }

    /**
     * 记录家族删除
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean deleteRecordById(Integer id, int status) {
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);
        fanNewsFamilyRecord.setStatus(status);
        fanNewsFamilyRecord.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人 待写
        boolean result = this.updateAllColumnById(fanNewsFamilyRecord);
        return result;
    }
}
