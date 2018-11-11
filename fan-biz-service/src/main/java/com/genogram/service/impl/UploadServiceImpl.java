package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
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
    private IFanNewsUploadFileService iFanNewsUploadFileService;

    @Override
    public boolean storageFanFile(String fileNames,Integer showId, Integer newsId) {
        String[] split = fileNames.split(";");
        List<FanNewsUploadFile> list = new ArrayList<FanNewsUploadFile>();
        if(list==null || list.isEmpty()) {
            return true;
        }
        for (String fileName : split) {
            FanNewsUploadFile fanNewsUploadFile = new FanNewsUploadFile();
            fanNewsUploadFile.setShowId(showId);
            fanNewsUploadFile.setNewsId(newsId);
            fanNewsUploadFile.setFilePath(fileName);
            Timestamp format = DateUtil.getCurrentTimeStamp();
            fanNewsUploadFile.setCreateTime(format);
            fanNewsUploadFile.setUpdateTime(format);
            list.add(fanNewsUploadFile);
        }
        //批量insert
        return iFanNewsUploadFileService.insertBatch(list);
    }

    @Override
    public boolean deleteFanFilesByIds(String ids) {
        String[] split = ids.split(";");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //批量insert
        return iFanNewsUploadFileService.deleteBatchIds(list);
    }
}
