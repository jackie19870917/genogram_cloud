package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringUtils;
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
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Override
    public boolean storageFanFile(String fileName,String filePath,Integer newsId,Integer showId) {
        if(StringUtils.isEmpty(fileName)) {
            return true;
        }

        updateOldFiletoNoIndex(newsId,showId);

        Timestamp format = DateUtil.getCurrentTimeStamp();
        FanNewsUploadFile fanNewsUploadFile = new FanNewsUploadFile();
        fanNewsUploadFile.setNewsId(newsId);
        fanNewsUploadFile.setShowId(showId);
        fanNewsUploadFile.setFileName(fileName);
        fanNewsUploadFile.setFilePath(filePath);
        fanNewsUploadFile.setStatus(1);
        fanNewsUploadFile.setPicIndex(1);
        fanNewsUploadFile.setCreateTime(format);
        fanNewsUploadFile.setUpdateTime(format);

        return fanNewsUploadFileService.insert(fanNewsUploadFile);
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
        Wrapper<FanNewsUploadFile> entity = new EntityWrapper<>();
        entity.eq("news_id", newsId);
        entity.eq("show_id", showId);
        List<FanNewsUploadFile> list = fanNewsUploadFileService.selectList(entity);
        list.forEach((fanNewsUploadFile)->{
            //取消首页置顶
            fanNewsUploadFile.setPicIndex(0);
            //删除
            fanNewsUploadFile.setStatus(0);
        });
        if(!list.isEmpty()){
            fanNewsUploadFileService.insertOrUpdateBatch(list);
        }
    }

    @Override
    public boolean storageFanFiles(String fileNames, Integer showId, Integer newsId) {
        if(StringUtils.isEmpty(fileNames)) {
            return true;
        }
        String[] split = fileNames.split(";");
        Timestamp format = DateUtil.getCurrentTimeStamp();
        List<FanNewsUploadFile> list = new ArrayList<FanNewsUploadFile>();
        for (String fileName : split) {
            FanNewsUploadFile fanNewsUploadFile = new FanNewsUploadFile();
            fanNewsUploadFile.setShowId(showId);
            fanNewsUploadFile.setNewsId(newsId);
            fanNewsUploadFile.setFilePath(fileName);
            fanNewsUploadFile.setStatus(1);
            fanNewsUploadFile.setPicIndex(1);
            fanNewsUploadFile.setCreateTime(format);
            fanNewsUploadFile.setUpdateTime(format);
            list.add(fanNewsUploadFile);
        }
        //批量insert
        return fanNewsUploadFileService.insertBatch(list);
    }

    @Override
    public boolean deleteFanFilesByIds(String ids) {
        String[] split = ids.split(";");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //批量insert
        return fanNewsUploadFileService.deleteBatchIds(list);
    }


}
