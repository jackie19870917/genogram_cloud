package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsFamilyRecordVedio;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entity.ProNewsUploadVedio;
import com.genogram.entityvo.FamilyRecordVideoVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProFamilyRecordVedioVo;
import com.genogram.mapper.ProNewsFamilyRecordVedioMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.*;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    private IUploadFileService iuploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public Page<FamilyRecordVideoVo> getFamilyRecordVedioPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyRecordVideoVo> familyRecordVedioVoList = new ArrayList<>();

        Wrapper<ProNewsFamilyRecordVedio> entity = new EntityWrapper<ProNewsFamilyRecordVedio>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询视频主表
        Page<ProNewsFamilyRecordVedio> proNewsFamilyRecordVedioPage = this.selectPage(new Page<ProNewsFamilyRecordVedio>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ProNewsFamilyRecordVedio> list = proNewsFamilyRecordVedioPage.getRecords();
        if (list.size() == 0) {
            return null;
        }
        //得到所有视频id
        List newsids = new ArrayList<>();
        list.forEach((news) -> {
            newsids.add(news.getId());
        });
        //查询视频文字
        Wrapper<ProNewsUploadVedio> uploadentity = new EntityWrapper<ProNewsUploadVedio>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id", newsids);
        //查询所有文章id下的视频附件
        List<ProNewsUploadVedio> files = iProNewsUploadVedioService.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach((news) -> {
            ProFamilyRecordVedioVo familyRecordVedioVo = new ProFamilyRecordVedioVo();
            //copy bean
            BeanUtils.copyProperties(news, familyRecordVedioVo);

            //判断文章id是否一样
            List<ProNewsUploadVedio> proNewsUploadVedio = new ArrayList<>();
            files.forEach((data) -> {
                if (news.getId().equals(data.getNewsId())) {
                    proNewsUploadVedio.add(data);
                }
            });
            //存储视频list集合
            familyRecordVedioVo.setProNewsFamilyRecordVedios(list);

            //设置封面file
            this.getPicIndex(familyRecordVedioVo, news.getId(), showId);

            //存储到新的集合中
            familyRecordVedioVoList.add(familyRecordVedioVo);
        });
        //重新设置page对象
        Page<FamilyRecordVideoVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(familyRecordVedioVoList);
        mapPage.setSize(proNewsFamilyRecordVedioPage.getSize());
        mapPage.setTotal(proNewsFamilyRecordVedioPage.getTotal());

        return mapPage;
    }

    @Override
    public NewsDetailVo getFamilyVedioRecord(Integer id) {
        //根据Id查出记录家族详情
        ProNewsFamilyRecordVedio fanNewsFamilyRecordVedio = this.selectById(id);

        if (fanNewsFamilyRecordVedio == null) {
            return null;
        }

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<>();
        uploadentity.eq("show_id", fanNewsFamilyRecordVedio.getShowId());
        uploadentity.eq("news_id", id);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files = iProNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecordVedio, newsDetailVo);
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
    public FamilyRecordVideoVo getFamilyVedioDetilRecord(Integer id) {
        //根据Id查出记录家族详情
        ProNewsFamilyRecordVedio fanNewsFamilyRecordVedio = this.selectById(id);

        if (fanNewsFamilyRecordVedio == null) {
            return null;
        }

        //查询视频
        Wrapper<ProNewsUploadVedio> uploadentity = new EntityWrapper<>();
        uploadentity.eq("news_id", fanNewsFamilyRecordVedio.getId());
        uploadentity.eq("show_id", fanNewsFamilyRecordVedio.getShowId());
        uploadentity.eq("status", 1);
        //查询所有文章id下的视频附件
        List<ProNewsUploadVedio> files = iProNewsUploadVedioService.selectList(uploadentity);


        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        FamilyRecordVideoVo familyRecordVedioVo = new FamilyRecordVideoVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecordVedio, familyRecordVedioVo);
        //存储图片list集合
        //设置封面file
        this.getPicIndex(familyRecordVedioVo, familyRecordVedioVo.getId(), familyRecordVedioVo.getShowId());
        familyRecordVedioVo.setFanUploadVedioList(files);
        //存储作者名称时间
        familyRecordVedioVo.setUpdateTimeLong(fanNewsFamilyRecordVedio.getUpdateTime().getTime());
        familyRecordVedioVo.setCreateTimeLong(fanNewsFamilyRecordVedio.getCreateTime().getTime());
        familyRecordVedioVo.setCreateUserName(null);
        familyRecordVedioVo.setCreateUserName(null);
        return familyRecordVedioVo;
    }

    private void getPicIndex(FamilyRecordVideoVo vo, int newsId, int showId) {
        Wrapper<ProNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        entity.eq("status", 1);
        entity.eq("pic_index", 1);
        ProNewsUploadFile file = iProNewsUploadFileService.selectOne(entity);
        List list = new ArrayList();
        list.add(file);
        vo.setFanNewsUploadFile(list);
    }
    @Override
    public boolean addOrUpdateVedioRecord(ProNewsFamilyRecordVedio proNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (proNewsFamilyRecordVedio.getId() == null) {
            //存入创建时间
            proNewsFamilyRecordVedio.setCreateTime(format);
            proNewsFamilyRecordVedio.setCreateUser(proNewsFamilyRecordVedio.getUpdateUser());
        }
        //存入修改时间
        proNewsFamilyRecordVedio.setUpdateTime(format);
        boolean result = this.insertOrUpdate(proNewsFamilyRecordVedio);
        //存储图片
        if (result) {
            iuploadFileService.storageFanFile(picfileName, picPath, proNewsFamilyRecordVedio.getId(), proNewsFamilyRecordVedio.getShowId());
            iuploadFileService.storageFanVedio(vedioFileName, vedioPath, proNewsFamilyRecordVedio.getId(), proNewsFamilyRecordVedio.getShowId());
        }
        return result;
    }

    @Override
    public Boolean deleteVedioRecordById(Integer id, int status, Integer userId) {
        ProNewsFamilyRecordVedio proNewsFamilyRecordVedio = this.selectById(id);
        proNewsFamilyRecordVedio.setStatus(status);
        proNewsFamilyRecordVedio.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人
        proNewsFamilyRecordVedio.setUpdateUser(userId);
        boolean result = this.updateAllColumnById(proNewsFamilyRecordVedio);
        return result;
    }

    private void getPicIndex(ProFamilyRecordVedioVo vo, int newsId, int showId) {
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
