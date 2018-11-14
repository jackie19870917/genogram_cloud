package com.genogram.service.impl;

import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.service.IProNewsUploadFileService;
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
    private IProNewsUploadFileService proNewsUploadFileService;

    @Override
    public boolean storageFanFile(String fileNames,Integer showId, Integer newsId) {
        if(StringUtils.isEmpty(fileNames)) {
            return true;
        }
        String[] split = fileNames.split(";");
        List<ProNewsUploadFile> list = new ArrayList<ProNewsUploadFile>();
        for (String fileName : split) {
            ProNewsUploadFile proNewsUploadFile = new ProNewsUploadFile();
            proNewsUploadFile.setShowId(showId);
            proNewsUploadFile.setNewsId(newsId);
            proNewsUploadFile.setFilePath(fileName);
            Timestamp format = DateUtil.getCurrentTimeStamp();
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
