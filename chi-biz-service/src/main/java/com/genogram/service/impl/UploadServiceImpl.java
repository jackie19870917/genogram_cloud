package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ChiNewsUploadFile;
import com.genogram.entity.ChiNewsUploadVideo;
import com.genogram.service.IChiNewsUploadFileService;
import com.genogram.service.IChiNewsUploadVideoService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wang, wei
 * @Date: 2018-11-11
 * @Time: 23:19
 * @return:
 * @Description:
 */
@Service
public class UploadServiceImpl implements IUploadFileService {

    @Autowired
    private IChiNewsUploadFileService chiNewsUploadFileService;

    @Autowired
    private IChiNewsUploadVideoService chiNewsUploadVideoService;

    @Override
    public boolean storageFanFile(String fileName, String filePath, Integer newsId, Integer showId) {
        if (StringsUtils.isEmpty(fileName)) {
            return true;
        }

        updateOldFiletoNoIndex(newsId, showId);

        Timestamp format = DateUtil.getCurrentTimeStamp();
        ChiNewsUploadFile chiNewsUploadFile = new ChiNewsUploadFile();
        chiNewsUploadFile.setNewsId(newsId);
        chiNewsUploadFile.setShowId(showId);
        chiNewsUploadFile.setFileName(fileName);
        chiNewsUploadFile.setFilePath(filePath);
        chiNewsUploadFile.setStatus(1);
        chiNewsUploadFile.setPicIndex(1);
        chiNewsUploadFile.setCreateTime(format);
        chiNewsUploadFile.setUpdateTime(format);

        return chiNewsUploadFileService.insert(chiNewsUploadFile);
    }

    @Override
    public boolean storageFanVedio(String fileName, String filePath, Integer newsId, Integer showId) {
        if (StringsUtils.isEmpty(fileName)) {
            return true;
        }

        updateOldVedio(newsId, showId);

        Timestamp format = DateUtil.getCurrentTimeStamp();
        ChiNewsUploadVideo chiNewsUploadVideo = new ChiNewsUploadVideo();
        chiNewsUploadVideo.setNewsId(newsId);
        chiNewsUploadVideo.setShowId(showId);
        chiNewsUploadVideo.setFileName(fileName);
        chiNewsUploadVideo.setFilePath(filePath);
        chiNewsUploadVideo.setStatus(1);
        chiNewsUploadVideo.setCreateTime(format);
        chiNewsUploadVideo.setUpdateTime(format);

        return chiNewsUploadVideoService.insert(chiNewsUploadVideo);
    }


    /**
     * 将原来的showid newsid 下的文章的附件首页取消
     *
     * @Author: wang, wei
     * @Date: 2018-11-11
     * @Time: 23:19
     * @return:
     * @Description:
     */
    private void updateOldFiletoNoIndex(Integer newsId, Integer showId) {
        Wrapper<ChiNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        List<ChiNewsUploadFile> list = chiNewsUploadFileService.selectList(entity);
        list.forEach((chiNewsUploadFile) -> {
            //取消首页置顶
            chiNewsUploadFile.setPicIndex(0);
            //删除
            chiNewsUploadFile.setStatus(0);
        });
        if (!list.isEmpty()) {
            chiNewsUploadFileService.insertOrUpdateBatch(list);
        }
    }

    private void updateOldVedio(Integer newsId, Integer showId) {
        Wrapper<ChiNewsUploadVideo> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        List<ChiNewsUploadVideo> list = chiNewsUploadVideoService.selectList(entity);
        list.forEach((chiNewsUploadVideo) -> {
            //删除
            chiNewsUploadVideo.setStatus(0);
        });
        if (!list.isEmpty()) {
            chiNewsUploadVideoService.insertOrUpdateBatch(list);
        }
    }

    @Override
    public boolean storageFanFiles(String fileNames, Integer showId, Integer newsId) {
        if (StringsUtils.isEmpty(fileNames)) {
            return true;
        }
        String[] split = fileNames.split(";");
        Timestamp format = DateUtil.getCurrentTimeStamp();
        List<ChiNewsUploadFile> list = new ArrayList<ChiNewsUploadFile>();
        for (String fileName : split) {
            ChiNewsUploadFile chiNewsUploadFile = new ChiNewsUploadFile();
            chiNewsUploadFile.setShowId(showId);
            chiNewsUploadFile.setNewsId(newsId);
            chiNewsUploadFile.setFilePath(fileName);
            chiNewsUploadFile.setStatus(1);
            chiNewsUploadFile.setPicIndex(1);
            chiNewsUploadFile.setCreateTime(format);
            chiNewsUploadFile.setUpdateTime(format);
            list.add(chiNewsUploadFile);
        }
        //批量insert
        return chiNewsUploadFileService.insertBatch(list);
    }

    @Override
    public boolean deleteFanFilesByIds(String ids) {
        String[] split = ids.split(";");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //批量insert
        return chiNewsUploadFileService.deleteBatchIds(list);
    }


}
