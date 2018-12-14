package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.genogram.config.Constants;
import com.genogram.entity.*;

import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

    /**
     * 前后台查询
     *
     * @param showId
     * @param slist
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<FamilyRecordVo> getFamilyRecordPage(Integer showId, List<Integer> slist, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyRecordVo> familyRecordVoList = new ArrayList<>();

        Wrapper<FanNewsFamilyRecord> entity = new EntityWrapper<FanNewsFamilyRecord>();
        entity.eq("show_id", showId);
        entity.in("status", slist);
        entity.orderBy("is_top", false);
        entity.orderBy("create_time", false);
        //分页查询文章主表
        Page<FanNewsFamilyRecord> fanNewsFamilyRecord = this.selectPage(new Page<FanNewsFamilyRecord>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsFamilyRecord> list = fanNewsFamilyRecord.getRecords();
        if (list.size() == 0) {
            return null;
        }

        //得到所有文章id
        List newsids = new ArrayList<>();
        list.forEach((news) -> {
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", 1);
        uploadentity.in("news_id", newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach((news) -> {
            FamilyRecordVo familyRecordVo = new FamilyRecordVo();
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
            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

            files.forEach((data) -> {
                if (news.getId().equals(data.getNewsId())) {
                    fanNewsUploadFile.add(data);
                    //去除html标签
                    news.setNewsText(StringsUtils.removeTag(news.getNewsText()));
                }
            });

            //存储图片list集合
            familyRecordVo.setFanNewsUploadFileList(fanNewsUploadFile);


            //存储到新的集合中
            familyRecordVoList.add(familyRecordVo);


        });
        //重新设置page对象
        Page<FamilyRecordVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(familyRecordVoList);
        mapPage.setSize(fanNewsFamilyRecord.getSize());
        mapPage.setTotal(fanNewsFamilyRecord.getTotal());

        return mapPage;
    }

    /**
     * 记录家族详情
     *
     * @param id 主键
     * @return
     */
    @Override
    public NewsDetailVo getFamilyRecord(Integer id) {
        //根据Id查出记录家族详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);

        if (fanNewsFamilyRecord == null) {
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamilyRecord.getShowId());
        uploadentity.eq("news_id", id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecord, newsDetailVo);
        //存储图片list集合
        newsDetailVo.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTimeLong(fanNewsFamilyRecord.getUpdateTime().getTime());
        newsDetailVo.setCreateTimeLong(fanNewsFamilyRecord.getCreateTime().getTime());
        newsDetailVo.setCreateUserName(null);
        newsDetailVo.setCreateUserName(null);
        return newsDetailVo;
    }

    /**
     * 联谊会记录家族后台新增 修改
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:25
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public boolean addOrUpdateRecord(FanNewsFamilyRecord fanNewsRecord, String fileName, String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (fanNewsRecord.getId() == null) {
            //存入创建时间
            fanNewsRecord.setCreateTime(format);
            fanNewsRecord.setCreateUser(fanNewsRecord.getUpdateUser());

        }
        //存入修改时间
        fanNewsRecord.setUpdateTime(format);
        boolean result = this.insertOrUpdate(fanNewsRecord);
        //存储图片
        if (result && StringsUtils.isNotEmpty(filePath)) {
            iuploadFileService.storageFanFile(fileName, filePath, fanNewsRecord.getId(), fanNewsRecord.getShowId());
        }
        return result;
    }

    /**
     * 记录家族删除
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean deleteRecordById(Integer id, int status, Integer userId) {
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);
        fanNewsFamilyRecord.setStatus(status);
        fanNewsFamilyRecord.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人
        fanNewsFamilyRecord.setUpdateUser(userId);
        boolean result = this.updateAllColumnById(fanNewsFamilyRecord);
        return result;
    }

    @Override
    public NewsDetailVo getFamilyRecordDetail(Integer id) {

        //根据Id查出文章详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);
        if (fanNewsFamilyRecord == null) {
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamilyRecord.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index", 1);
        uploadentity.eq("news_id", id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsFamilyRecord.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsFamilyRecord.getCreateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();

        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecord, newsDetailVo);
        //存储图片list集合
        newsDetailVo.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTime(fanNewsFamilyRecord.getUpdateTime());
        newsDetailVo.setCreateTime(fanNewsFamilyRecord.getCreateTime());
        newsDetailVo.setCreateUser(null);
        return newsDetailVo;
    }

    /**
     * 联谊会记录家族前台增加查看数
     *
     * @Author: yuzhou
     * @Date: 2018-11-12
     * @Time: 13:49
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsFamilyRecord.getVisitNum() + 1;
        fanNewsFamilyRecord.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsFamilyRecord);

        if (visitNum > Constants.FAN_VISIT_NUM || visitNum.equals(Constants.FAN_VISIT_NUM)) {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 2;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend = new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setStatus(isAuto);
            fanSysRecommend.setShowId(fanNewsFamilyRecord.getShowId());
            fanSysRecommend.setNewsId(fanNewsFamilyRecord.getId());
            fanSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
}
