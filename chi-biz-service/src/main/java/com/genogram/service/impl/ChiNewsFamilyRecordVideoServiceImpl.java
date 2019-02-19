package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecordVideo;
import com.genogram.mapper.ChiNewsFamilyRecordVideoMapper;
import com.genogram.service.IChiNewsFamilyRecordVideoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 * 省级记录家族视频上传-视频概要 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiNewsFamilyRecordVideoServiceImpl extends ServiceImpl<ChiNewsFamilyRecordVideoMapper, ChiNewsFamilyRecordVideo> implements IChiNewsFamilyRecordVideoService {

    /**
     *全国记录家族视频新增
     *@Author: yuzhou
     *@Date: 2019-02-19
     *@Time: 9:35
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean addOrUpdateRecordVideo(ChiNewsFamilyRecordVideo chiNewsFamilyRecordVideo, AllUserLogin userLogin) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();

        //判断是否有id
        if(chiNewsFamilyRecordVideo.getId()==null){
            chiNewsFamilyRecordVideo.setCreateUser(userLogin.getId());
            chiNewsFamilyRecordVideo.setCreateTime(format);
        }
        chiNewsFamilyRecordVideo.setUpdateUser(userLogin.getId());
        chiNewsFamilyRecordVideo.setUpdateTime(format);
        boolean result = this.insertOrUpdate(chiNewsFamilyRecordVideo);
        return result;
    }
}
