package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiIndexInfo;
import com.genogram.entity.ChiSysSite;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.mapper.ChiIndexInfoMapper;
import com.genogram.service.IAllFamilyService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IChiIndexInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IChiSysSiteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 省级网站:图腾;简介;宣言 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiIndexInfoServiceImpl extends ServiceImpl<ChiIndexInfoMapper, ChiIndexInfo> implements IChiIndexInfoService {

    @Autowired
    private IChiSysSiteService chiSysSiteService;

    @Autowired
    private IAllFamilyService allFamilyService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public IndexInfoVo getIndexInfoVoBySiteId(Integer siteId) {

        Wrapper<ChiIndexInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("site_id", siteId);

        ChiIndexInfo chiIndexInfo = this.selectOne(wrapper);

        ChiSysSite chiSysSite = chiSysSiteService.selectById(siteId);

        if (StringUtils.isEmpty(chiSysSite)) {
            return null;
        }

        IndexInfoVo indexInfoVo = new IndexInfoVo();
        BeanUtils.copyProperties(chiIndexInfo, indexInfoVo);

        indexInfoVo.setSiteName(chiSysSite.getName());
        indexInfoVo.setFamilyCode(chiSysSite.getFamilyCode());
        indexInfoVo.setRegionCode(chiSysSite.getRegionCode());

        AllFamily family = allFamilyService.getAllFamilyById(chiSysSite.getFamilyCode());
        indexInfoVo.setFamilyName(family.getValue());

        Wrapper<AllUserLogin> allUserLoginEntityWrapper = new EntityWrapper<>();
        wrapper.eq("family_code", chiSysSite.getFamilyCode());

        Page<AllUserLogin> allUserLoginPage = allUserLoginService.getAllUserLoginPage(allUserLoginEntityWrapper, 1, 100);

        indexInfoVo.setUserNum(allUserLoginPage.getTotal());

        return indexInfoVo ;
    }
}
