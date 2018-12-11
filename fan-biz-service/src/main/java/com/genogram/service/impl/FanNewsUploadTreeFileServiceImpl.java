package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.mapper.FanNewsUploadTreeFileMapper;
import com.genogram.service.IFanNewsUploadTreeFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-04
 */
@Service
public class FanNewsUploadTreeFileServiceImpl extends ServiceImpl<FanNewsUploadTreeFileMapper, FanNewsUploadTreeFile> implements IFanNewsUploadTreeFileService {

    @Override
    public Page<FanNewsUploadTreeFile> getFanNewsUploadTreeFile(Integer siteId, String fileName, List list, Integer pageNo, Integer pageSize) {

        Wrapper<FanNewsUploadTreeFile> wrapper = new EntityWrapper<>();

        if (fileName != null) {
            wrapper.like("file_name", fileName);
        }
        wrapper.eq("site_id", siteId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public Boolean insertOrUpdateFanNewsUploadTreeFile(FanNewsUploadTreeFile fanNewsUploadTreeFile) {

        return this.insertOrUpdate(fanNewsUploadTreeFile);
    }

    @Override
    public FanNewsUploadTreeFile getFanNewsUploadTreeFile(Integer id) {
        return this.selectById(id);
    }

    @Override
    public Boolean deleteFanNewsUploadTreeFile(Integer id, Integer userId) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        FanNewsUploadTreeFile fanNewsUploadTreeFile = new FanNewsUploadTreeFile();
        fanNewsUploadTreeFile.setId(id);
        fanNewsUploadTreeFile.setUpdateTime(timeStamp);
        fanNewsUploadTreeFile.setStatus(0);
        fanNewsUploadTreeFile.setUpdateUser(userId);

        return this.updateById(fanNewsUploadTreeFile);
    }
}
