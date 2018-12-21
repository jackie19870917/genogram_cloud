package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.entityvo.NewsUploadTreeFileVo;
import com.genogram.mapper.FanNewsUploadTreeFileMapper;
import com.genogram.service.IAllFamilyService;
import com.genogram.service.CommonIFanNewsUploadTreeFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanNewsUploadTreeFileService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    @Autowired
    private IAllFamilyService allFamilyService;

    @Override
    public Page<NewsUploadTreeFileVo> getFanNewsUploadTreeFile(Integer siteId, String fileName, List list, Integer pageNo, Integer pageSize) {

        Wrapper<FanNewsUploadTreeFile> wrapper = new EntityWrapper<>();

        if (fileName != null) {
            wrapper.like("file_name", fileName);
        }
        wrapper.eq("site_id", siteId);
        wrapper.in("status", list);
        wrapper.orderBy("update_time", false);

        Page<FanNewsUploadTreeFile> newsUploadTreeFilePage = this.selectPage(new Page<>(pageNo, pageSize), wrapper);

        List<FanNewsUploadTreeFile> newsUploadTreeFileList = newsUploadTreeFilePage.getRecords();

        List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = new ArrayList<>();
        newsUploadTreeFileList.forEach((FanNewsUploadTreeFile fanNewsUploadTreeFile) -> {
            NewsUploadTreeFileVo newsUploadTreeFileVo = new NewsUploadTreeFileVo();

            BeanUtils.copyProperties(fanNewsUploadTreeFile, newsUploadTreeFileVo);
            AllFamily allFamily = allFamilyService.getAllFamilyById(fanNewsUploadTreeFile.getFamilyCode());
            newsUploadTreeFileVo.setFamilyName(allFamily.getValue());

            newsUploadTreeFileVoList.add(newsUploadTreeFileVo);
        });

        Page<NewsUploadTreeFileVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(newsUploadTreeFileVoList);
        mapPage.setTotal(newsUploadTreeFilePage.getTotal());

        return mapPage;
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
