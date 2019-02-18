package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.*;
import com.genogram.entityvo.NewsUploadTreeFileVo;
import com.genogram.mapper.*;
import com.genogram.service.IAllFamilyService;
import com.genogram.service.IProNewsUploadTreeFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private IAllFamilyService allFamilyService;

    @Override
    public Page getProNewsUploadTreeFile(String regionCode, String fileName, Integer siteId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<ProNewsUploadTreeFile> proNewsUploadTreeFileWrapper = new EntityWrapper<>();
        Wrapper<FanNewsUploadTreeFile> fanNewsUploadTreeFileWrapper = new EntityWrapper<>();

        // 地区和名称为空
        if (regionCode == "" && fileName == "") {
            proNewsUploadTreeFileWrapper.in("status", list);
            proNewsUploadTreeFileWrapper.eq("site_id", siteId);
            proNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<ProNewsUploadTreeFile> newsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

            List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFileVos(newsUploadTreeFileList);

            //根据地区获取县级网站ID
            ProSysSite proSysSite = proSysSiteMapper.selectById(siteId);

            Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
            wrapper.eq("parent", proSysSite.getRegionCode());
            wrapper.eq("family_code", proSysSite.getFamilyCode());

            List<FanSysSite> sysSiteList = fanSysSiteMapper.selectList(wrapper);

            List siteList = new ArrayList();
            for (FanSysSite fanSysSite : sysSiteList) {
                siteList.add(fanSysSite.getId());
            }

            fanNewsUploadTreeFileWrapper.in("status", list);
            fanNewsUploadTreeFileWrapper.in("site_id", siteList);
            fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

            List<NewsUploadTreeFileVo> newsUploadTreeFileList1 = getNewsUploadTreeFile(fanNewsUploadTreeFileList);

            newsUploadTreeFileVoList.addAll(newsUploadTreeFileList1);

            Page page = new Page(pageNo, pageSize);
            page.setRecords(newsUploadTreeFileVoList);
            page.setTotal(newsUploadTreeFileVoList.size());

            return page;

            // 地区为空   名称不为空
        } else if (regionCode == "" && fileName != "") {
            proNewsUploadTreeFileWrapper.like("file_name", fileName);
            proNewsUploadTreeFileWrapper.eq("site_id", siteId);
            proNewsUploadTreeFileWrapper.in("status", list);
            proNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

            List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFileVos(proNewsUploadTreeFileList);

            fanNewsUploadTreeFileWrapper.like("file_name", fileName);
            fanNewsUploadTreeFileWrapper.in("status", list);
            fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

            List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

            List<NewsUploadTreeFileVo> newsUploadTreeFileList1 = getNewsUploadTreeFile(fanNewsUploadTreeFileList);

            newsUploadTreeFileVoList.addAll(newsUploadTreeFileList1);

            Page page = new Page(pageNo, pageSize);
            page.setRecords(newsUploadTreeFileVoList);
            page.setTotal(newsUploadTreeFileVoList.size());

            return page;

            // 地区不为空   名称为空
        } else if (regionCode != "" && fileName == "") {

            AllRegion allRegion = new AllRegion();
            allRegion.setCode(Integer.valueOf(regionCode));

            allRegion = allRegionMapper.selectOne(allRegion);

            if (StringUtils.isEmpty(allRegion)) {
                return null;
            }

            if (allRegion.getParentCode() == 0) {

                ProSysSite proSysSite01 = new ProSysSite();
                proSysSite01.setRegionCode(regionCode);

                //根据地区获取县级网站ID
                ProSysSite proSysSite = proSysSiteMapper.selectOne(proSysSite01);

                if (StringUtils.isEmpty(proSysSite)) {
                    return null;
                }

                proNewsUploadTreeFileWrapper.eq("site_id", proSysSite.getId());
                proNewsUploadTreeFileWrapper.in("status", list);
                proNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

                List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFileVos(proNewsUploadTreeFileList);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(newsUploadTreeFileVoList);
                page.setTotal(newsUploadTreeFileVoList.size());

                return page;
            } else {

                FanSysSite fanSysSite01 = new FanSysSite();
                fanSysSite01.setRegionCode(regionCode);

                FanSysSite fanSysSite = fanSysSiteMapper.selectOne(fanSysSite01);

                if (StringUtils.isEmpty(fanSysSite)) {
                    return null;
                }

                fanNewsUploadTreeFileWrapper.eq("site_id", fanSysSite.getId());
                fanNewsUploadTreeFileWrapper.in("status", list);
                fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

                List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFile(fanNewsUploadTreeFileList);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(newsUploadTreeFileVoList);
                page.setTotal(newsUploadTreeFileVoList.size());

                return page;
            }

            // 地区不为空   名称不为空
        } else {

            AllRegion allRegion = new AllRegion();
            allRegion.setCode(Integer.valueOf(regionCode));

            allRegion = allRegionMapper.selectOne(allRegion);

            if (StringUtils.isEmpty(allRegion)) {
                return null;
            }

            if (allRegion.getParentCode() == 0) {
                ProSysSite proSysSite01 = new ProSysSite();
                proSysSite01.setRegionCode(regionCode);

                //根据地区获取县级网站ID
                ProSysSite proSysSite = proSysSiteMapper.selectOne(proSysSite01);

                if (StringUtils.isEmpty(proSysSite)) {
                    return null;
                }

                proNewsUploadTreeFileWrapper.eq("site_id", proSysSite.getId());
                proNewsUploadTreeFileWrapper.like("file_name", fileName);
                proNewsUploadTreeFileWrapper.in("status", list);
                proNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<ProNewsUploadTreeFile> proNewsUploadTreeFileList = this.selectList(proNewsUploadTreeFileWrapper);

                List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFileVos(proNewsUploadTreeFileList);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(newsUploadTreeFileVoList);
                page.setTotal(newsUploadTreeFileVoList.size());

                return page;
            } else {

                FanSysSite fanSysSite01 = new FanSysSite();
                fanSysSite01.setRegionCode(regionCode);

                FanSysSite fanSysSite = fanSysSiteMapper.selectOne(fanSysSite01);

                if (StringUtils.isEmpty(fanSysSite)) {
                    return null;
                }

                fanNewsUploadTreeFileWrapper.eq("site_id", fanSysSite.getId());
                fanNewsUploadTreeFileWrapper.like("file_name", fileName);
                fanNewsUploadTreeFileWrapper.in("status", list);
                fanNewsUploadTreeFileWrapper.orderBy("update_time", false);

                List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList = fanNewsUploadTreeFileMapper.selectList(fanNewsUploadTreeFileWrapper);

                List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = getNewsUploadTreeFile(fanNewsUploadTreeFileList);

                Page page = new Page(pageNo, pageSize);
                page.setRecords(newsUploadTreeFileVoList);
                page.setTotal(newsUploadTreeFileVoList.size());

                return page;
            }
        }
    }

    private List<NewsUploadTreeFileVo> getNewsUploadTreeFile(List<FanNewsUploadTreeFile> fanNewsUploadTreeFileList) {

        List<NewsUploadTreeFileVo> newsUploadTreeFileList = new ArrayList<>();

        fanNewsUploadTreeFileList.forEach((FanNewsUploadTreeFile fanNewsUploadTreeFile) -> {
            NewsUploadTreeFileVo newsUploadTreeFileVo = new NewsUploadTreeFileVo();

            BeanUtils.copyProperties(fanNewsUploadTreeFile, newsUploadTreeFileVo);
            AllFamily allFamily = allFamilyService.getAllFamilyById(fanNewsUploadTreeFile.getFamilyCode());
            newsUploadTreeFileVo.setFamilyName(allFamily.getValue());
            newsUploadTreeFileVo.setUrl(fanNewsUploadTreeFile.getTreePreviewPath());

            newsUploadTreeFileList.add(newsUploadTreeFileVo);
        });
        return newsUploadTreeFileList;
    }

    private List<NewsUploadTreeFileVo> getNewsUploadTreeFileVos(List<ProNewsUploadTreeFile> proNewsUploadTreeFileList) {
        List<NewsUploadTreeFileVo> newsUploadTreeFileVoList = new ArrayList<>();

        proNewsUploadTreeFileList.forEach((ProNewsUploadTreeFile proNewsUploadTreeFile) -> {
            NewsUploadTreeFileVo newsUploadTreeFileVo = new NewsUploadTreeFileVo();

            BeanUtils.copyProperties(proNewsUploadTreeFile, newsUploadTreeFileVo);
            AllFamily allFamily = allFamilyService.getAllFamilyById(proNewsUploadTreeFile.getFamilyCode());
            newsUploadTreeFileVo.setFamilyName(allFamily.getValue());
            newsUploadTreeFileVo.setUrl(proNewsUploadTreeFile.getTreePreviewPath());

            newsUploadTreeFileVoList.add(newsUploadTreeFileVo);
        });
        return newsUploadTreeFileVoList;
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

        return this.insertOrUpdate(proNewsUploadTreeFile);
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

    @Override
    public Boolean insertOrUpdateProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile) {

        return this.insertOrUpdate(proNewsUploadTreeFile);
    }
}
