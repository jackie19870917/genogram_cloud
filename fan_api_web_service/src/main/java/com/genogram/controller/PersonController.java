package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.SysSiteVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanProIndexInfoService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "后台用户管理")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/sysSite/person")
public class PersonController {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    @Autowired
    private IFanProIndexInfoService proIndexInfoService;

    @ApiOperation(value = "网站", notes = "id-主键,familyCode-姓氏,regionCode-地区,name-网站名,pic-图腾")
    @RequestMapping(value = "getSysSite", method = RequestMethod.POST)
    public Response<SysSiteVo> getSysSite(SysSiteVo sysSiteVo,
                                          @ApiParam("siteType(联谊会-fan,省级-pro)") @RequestParam(value = "siteType") String siteType) {

        if ("fan".equals(siteType)) {

            Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
            List<FanSysSite> fanSysSiteList = allUserLoginService.getFanSysSite(wrapper);

            List<Integer> siteIdList = new ArrayList();
            List familyList = new ArrayList();
            fanSysSiteList.forEach((FanSysSite fanSysSite) -> {
                siteIdList.add(fanSysSite.getId());
                familyList.add(fanSysSite.getFamilyCode());
            });

            Wrapper<AllFamily> allFamilyWrapper = new EntityWrapper<>();
            allFamilyWrapper.in("id", familyList);
            List<AllFamily> allFamilies = allUserLoginService.getAllFamily(allFamilyWrapper);

            List<FanIndexInfo> fanIndexInfoList = new ArrayList<>();
            for (Integer siteId : siteIdList) {
                FanIndexInfo fanIndexInfo = fanIndexInfoService.getFanIndexInfo(siteId);
                fanIndexInfoList.add(fanIndexInfo);
            }

            List<SysSiteVo> sysSiteVoList = new ArrayList<>();
            fanSysSiteList.forEach((FanSysSite fanSysSite) -> {

                SysSiteVo sysSiteVo1 = new SysSiteVo();

                BeanUtils.copyProperties(fanSysSite,sysSiteVo1);

                List<AllFamily> families = new ArrayList<>();
                allFamilies.forEach((AllFamily allFamily) -> {

                    if (allFamily.getId().equals(fanSysSite.getFamilyCode())) {
                        families.add(allFamily);
                        sysSiteVo1.setFamilyName(allFamily.getValue());
                    }
                });

                List<FanIndexInfo> fanIndexInfos = new ArrayList<>();
                fanIndexInfoList.forEach((FanIndexInfo fanIndexInfo) -> {

                    if (!StringUtils.isEmpty(fanIndexInfo) && fanSysSite.getId().equals(fanIndexInfo.getSiteId())) {
                        fanIndexInfos.add(fanIndexInfo);
                        sysSiteVo1.setUrl(fanIndexInfo.getTotemPicSrc());
                    } else {
                        sysSiteVo1.setUrl("00/04/wKgChFv-eoeAHXUWAAIBsiLZEJ4138.png");
                    }
                });
                sysSiteVoList.add(sysSiteVo1);
            });

            if (StringUtils.isEmpty(sysSiteVoList)) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }

            return ResponseUtlis.success(sysSiteVoList);

        } else if ("pro".equals(siteType)) {
            Wrapper<ProSysSite> wrapper = new EntityWrapper<>();
            List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSite(wrapper);

            List<Integer> siteIdList = new ArrayList();
            List familyList = new ArrayList();
            proSysSiteList.forEach((ProSysSite proSysSite) -> {
                siteIdList.add(proSysSite.getId());
                familyList.add(proSysSite.getFamilyCode());
            });

            Wrapper<AllFamily> allFamilyWrapper = new EntityWrapper<>();
            allFamilyWrapper.in("id", familyList);
            List<AllFamily> allFamilies = allUserLoginService.getAllFamily(allFamilyWrapper);

            List<ProIndexInfo> proIndexInfoList = new ArrayList<>();
            for (Integer siteId : siteIdList) {
                ProIndexInfo proIndexInfo = proIndexInfoService.getProIndexInfo(siteId);
                proIndexInfoList.add(proIndexInfo);
            }

            List<SysSiteVo> sysSiteVoList = new ArrayList<>();
            proSysSiteList.forEach((ProSysSite proSysSite) -> {

                SysSiteVo sysSiteVo1 = new SysSiteVo();

                BeanUtils.copyProperties(proSysSite,sysSiteVo1);

                List<AllFamily> families = new ArrayList<>();
                allFamilies.forEach((AllFamily allFamily) -> {

                    if (allFamily.getId().equals(proSysSite.getFamilyCode())) {
                        families.add(allFamily);
                        sysSiteVo1.setFamilyName(allFamily.getValue());
                    }
                });

                List<ProIndexInfo> proIndexInfos = new ArrayList<>();
                proIndexInfoList.forEach((ProIndexInfo proIndexInfo) -> {

                    if (!StringUtils.isEmpty(proIndexInfo)&&proIndexInfo.getSiteId().equals(proSysSite.getId())) {
                        proIndexInfos.add(proIndexInfo);
                        sysSiteVo1.setUrl(proIndexInfo.getTotemPicSrc());
                    } else {
                        sysSiteVo1.setUrl("00/04/wKgChFv-eoeAHXUWAAIBsiLZEJ4138.png");
                    }
                });
                sysSiteVoList.add(sysSiteVo1);
            });
            return ResponseUtlis.success(sysSiteVoList);
        } else {
            return null;
        }

    }
}
