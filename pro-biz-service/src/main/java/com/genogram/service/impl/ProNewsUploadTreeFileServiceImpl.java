package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.*;
import com.genogram.mapper.*;
import com.genogram.service.IProNewsUploadTreeFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-03
 */
@Service
public class ProNewsUploadTreeFileServiceImpl extends ServiceImpl<ProNewsUploadTreeFileMapper, ProNewsUploadTreeFile> implements IProNewsUploadTreeFileService {

    @Autowired
    private FanNewsUploadTreeFileMapper fanNewsUploadTreeFileMapper;

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Autowired
    private ProSysSiteMapper proSysSiteMapper;

    @Autowired
    private AllRegionMapper allRegionMapper;

    @Override
    public Page getProNewsUploadTreeFile(String regionCode, String fileName, List list, Integer pageNo, Integer pageSize) {

        Wrapper<ProNewsUploadTreeFile> proNewsUploadTreeFileWrapper = new EntityWrapper<>();
        Wrapper<FanNewsUploadTreeFile> fanNewsUploadTreeFileWrapper = new EntityWrapper<>();

        // 地区和名称为空
        if (regionCode == null && fileName == null) {
            proNewsUploadTreeFileWrapper.in("status", list);
            proNewsUploadTreeFileWrapper.orderBy("update_time", false);

            return this.selectPage(new Page<>(pageNo, pageSize), proNewsUploadTreeFileWrapper);

            // 地区为空   名称不为空
        } else if (regionCode == null && fileName != null) {
            proNewsUploadTreeFileWrapper.like("file_name", fileName);
            proNewsUploadTreeFileWrapper.in("status", list);
            proNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

            fanNewsUploadTreeFileWrapper.like("file_name", fileName);
            fanNewsUploadTreeFileWrapper.in("status", list);
            fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

            List arrayList = new ArrayList<>();
            arrayList.add(proNewsUploadTreeFileList);
            arrayList.add(fanNewsUploadTreeFileList);

            Page page = new Page(pageNo, pageSize);
            page.setRecords(arrayList);
            page.setTotal(proNewsUploadTreeFileList.size() + fanNewsUploadTreeFileList.size());

            return page;

            // 地区不为空   名称为空
        } else if (regionCode != null && fileName == null) {

            AllRegion allRegion = new AllRegion();
            allRegion.setCode(Integer.valueOf(regionCode));

            allRegion = allRegionMapper.selectOne(allRegion);

            if (StringUtils.isEmpty(allRegion)) {
                return null;
            }

            if (allRegion.getParentCode() == 0) {

                ProSysSite proSysSite = new ProSysSite();
                proSysSite.setRegionCode(regionCode);

                //根据地区获取县级网站ID
                proSysSite = proSysSiteMapper.selectOne(proSysSite);

                proNewsUploadTreeFileWrapper.eq("site_id", proSysSite.getId());
                proNewsUploadTreeFileWrapper.in("status", list);
                proNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(proNewsUploadTreeFileList);
                page.setTotal(proNewsUploadTreeFileList.size());

                return page;
            } else {

                FanSysSite fanSysSite = new FanSysSite();
                fanSysSite.setRegionCode(regionCode);

                fanSysSite = fanSysSiteMapper.selectOne(fanSysSite);

                fanNewsUploadTreeFileWrapper.eq("site_id", fanSysSite.getId());
                fanNewsUploadTreeFileWrapper.in("status", list);
                fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(fanNewsUploadTreeFileList);
                page.setTotal(fanNewsUploadTreeFileList.size());

                return page;
            }

            // 地区不为空   名称为空
        } else {

            AllRegion allRegion = new AllRegion();
            allRegion.setCode(Integer.valueOf(regionCode));

            allRegion = allRegionMapper.selectOne(allRegion);

            if (StringUtils.isEmpty(allRegion)) {
                return null;
            }

            if (allRegion.getParentCode() == 0) {

                ProSysSite proSysSite = new ProSysSite();
                proSysSite.setRegionCode(regionCode);

                proSysSite = proSysSiteMapper.selectOne(proSysSite);

                proNewsUploadTreeFileWrapper.eq("site_id", proSysSite.getId());
                proNewsUploadTreeFileWrapper.like("file_name", fileName);
                proNewsUploadTreeFileWrapper.in("status", list);
                proNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(proNewsUploadTreeFileList);
                page.setTotal(proNewsUploadTreeFileList.size());

                return page;
            } else {

                FanSysSite fanSysSite = new FanSysSite();
                fanSysSite.setRegionCode(regionCode);

                fanSysSite = fanSysSiteMapper.selectOne(fanSysSite);

                fanNewsUploadTreeFileWrapper.eq("site_id", fanSysSite.getId());
                fanNewsUploadTreeFileWrapper.like("file_name", fileName);
                fanNewsUploadTreeFileWrapper.in("status", list);
                fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(fanNewsUploadTreeFileList);
                page.setTotal(fanNewsUploadTreeFileList.size());

                return page;
            }
        }

    }

    @Override
    public Page<ProNewsUploadTreeFile> getProNewsUploadTreeFilePage(Integer siteId, String fileName, List list, Integer pageNo, Integer pageSize) {

        Wrapper<ProNewsUploadTreeFile> wrapper = new EntityWrapper<>();
        if (fileName != null) {
            wrapper.like("file_name", fileName);
        }
        wrapper.eq("site_id", siteId);

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public Boolean insertProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        proNewsUploadTreeFile.setIsFrom(2);
        proNewsUploadTreeFile.setCreateTime(timeStamp);
        proNewsUploadTreeFile.setUpdateTime(timeStamp);

        return this.insert(proNewsUploadTreeFile);
    }

    @Override
    public ProNewsUploadTreeFile getProNewsUploadTreeFile(Integer id) {

        return this.selectById(id);
    }

    @Override
    public FanNewsUploadTreeFile getFanNewsUploadTreeFile(Integer id) {

        return fanNewsUploadTreeFileMapper.selectById(id);
    }

    @Override
    public Boolean updateProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        proNewsUploadTreeFile.setCreateTime(timeStamp);
        proNewsUploadTreeFile.setUpdateTime(timeStamp);

        return this.updateById(proNewsUploadTreeFile);
    }

    @Override
    public Boolean deleteProNewsUploadTreeFile(Integer id, Integer userId) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        ProNewsUploadTreeFile proNewsUploadTreeFile = new ProNewsUploadTreeFile();
        proNewsUploadTreeFile.setUpdateTime(timeStamp);
        proNewsUploadTreeFile.setStatus(0);
        proNewsUploadTreeFile.setUpdateUser(userId);

        return this.updateById(proNewsUploadTreeFile);
    }
}
