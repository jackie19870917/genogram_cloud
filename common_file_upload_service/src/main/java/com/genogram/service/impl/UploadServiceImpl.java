package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.UploadService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UploadServiceImpl extends ServiceImpl<FanNewsUploadFileMapper, FanNewsUploadFile> implements UploadService {
    //上传
    @Override
    public boolean upLoadFile(String fileNames,Integer showid, Integer newsid) {
        String[] split = fileNames.split(";");
        List<FanNewsUploadFile> list = new ArrayList<FanNewsUploadFile>();
        for (String s : split) {
            FanNewsUploadFile fanNewsUploadFile = new FanNewsUploadFile();
            fanNewsUploadFile.setShowId(showid);
            fanNewsUploadFile.setNewsId(newsid);
            fanNewsUploadFile.setFilePath(s);
            Timestamp format = DateUtil.format(new Date());
            if(fanNewsUploadFile.getId()==null){
                //存入创建时间
                fanNewsUploadFile.setCreateTime(format);
            }else{
                //存入修改时间
                fanNewsUploadFile.setUpdateTime(format);
            }
            list.add(fanNewsUploadFile);
        }
        //批量insert
        return this.insertBatch(list);
    }
    //删除
    @Override
    public boolean deleteFile(String ids) {
        String[] split = ids.split(";");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //批量insert
        return this.deleteBatchIds(list);
    }
}
