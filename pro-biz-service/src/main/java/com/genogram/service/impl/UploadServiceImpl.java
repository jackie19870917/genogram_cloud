package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entity.ProNewsUploadVedio;
import com.genogram.service.IProNewsUploadFileService;
import com.genogram.service.IProNewsUploadVedioService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-11
 * @Time: 23:19
 * @return:
 * @Description:
 *
 */
@Service
public class UploadServiceImpl implements IUploadFileService {

    @Autowired
    private IProNewsUploadFileService proNewsUploadFileService;

    @Autowired
    private IProNewsUploadVedioService fanNewsUploadVedioService;

    @Override
    public boolean storageFanFile(String fileName,String filePath,Integer newsId,Integer showId) {
        if(StringsUtils.isEmpty(fileName)) {
            return true;
        }

        updateOldFiletoNoIndex(newsId,showId);

        Timestamp format = DateUtil.getCurrentTimeStamp();
        ProNewsUploadFile proNewsUploadFile = new ProNewsUploadFile();
        proNewsUploadFile.setNewsId(newsId);
        proNewsUploadFile.setShowId(showId);
        proNewsUploadFile.setFileName(fileName);
        proNewsUploadFile.setFilePath(filePath);
        proNewsUploadFile.setStatus(1);
        proNewsUploadFile.setPicIndex(1);
        proNewsUploadFile.setCreateTime(format);
        proNewsUploadFile.setUpdateTime(format);

        return proNewsUploadFileService.insert(proNewsUploadFile);
    }

    @Override
    public boolean storageFanVedio(String fileName, String filePath, Integer newsId, Integer showId) {
        if(StringsUtils.isEmpty(fileName)) {
            return true;
        }

        updateOldVedio(newsId,showId);

        Timestamp format = DateUtil.getCurrentTimeStamp();
        ProNewsUploadVedio proNewsUploadVedio = new ProNewsUploadVedio();
        proNewsUploadVedio.setNewsId(newsId);
        proNewsUploadVedio.setShowId(showId);
        proNewsUploadVedio.setFileName(fileName);
        proNewsUploadVedio.setFilePath(filePath);
        proNewsUploadVedio.setStatus(1);
        proNewsUploadVedio.setCreateTime(format);
        proNewsUploadVedio.setUpdateTime(format);

        return fanNewsUploadVedioService.insert(proNewsUploadVedio);
    }


    /**
     *将原来的showid newsid 下的文章的附件首页取消
     * @Author: wang,wei
     * @Date: 2018-11-11
     * @Time: 23:19
     * @return:
     * @Description:
     *
     */
    private void updateOldFiletoNoIndex(Integer newsId,Integer showId){
        Wrapper<ProNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        List<ProNewsUploadFile> list = proNewsUploadFileService.selectList(entity);
        list.forEach((proNewsUploadFile)->{
            //取消首页置顶
            proNewsUploadFile.setPicIndex(0);
            //删除
            proNewsUploadFile.setStatus(0);
        });
        if(!list.isEmpty()){
            proNewsUploadFileService.insertOrUpdateBatch(list);
        }
    }

    private void updateOldVedio(Integer newsId,Integer showId){
        Wrapper<ProNewsUploadVedio> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        List<ProNewsUploadVedio> list = fanNewsUploadVedioService.selectList(entity);
        list.forEach((proNewsUploadVedio)->{
            //删除
            proNewsUploadVedio.setStatus(0);
        });
        if(!list.isEmpty()){
            fanNewsUploadVedioService.insertOrUpdateBatch(list);
        }
    }

    @Override
    public boolean storageFanFiles(String fileNames, Integer showId, Integer newsId) {
        if(StringsUtils.isEmpty(fileNames)) {
            return true;
        }
        String[] split = fileNames.split(";");
        Timestamp format = DateUtil.getCurrentTimeStamp();
        List<ProNewsUploadFile> list = new ArrayList<ProNewsUploadFile>();
        for (String fileName : split) {
            ProNewsUploadFile proNewsUploadFile = new ProNewsUploadFile();
            proNewsUploadFile.setShowId(showId);
            proNewsUploadFile.setNewsId(newsId);
            proNewsUploadFile.setFilePath(fileName);
            proNewsUploadFile.setStatus(1);
            proNewsUploadFile.setPicIndex(1);
            proNewsUploadFile.setCreateTime(format);
            proNewsUploadFile.setUpdateTime(format);
            list.add(proNewsUploadFile);
        }
        //批量insert
        return proNewsUploadFileService.insertBatch(list);
    }

    @Override
    public boolean deleteFanFilesByIds(String ids) {
        String[] split = ids.split(";");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //批量insert
        return proNewsUploadFileService.deleteBatchIds(list);
    }


}
