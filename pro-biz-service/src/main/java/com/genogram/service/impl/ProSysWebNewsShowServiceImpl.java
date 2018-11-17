package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.ProSysWebMenu;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.mapper.ProSysWebNewsShowMapper;
import com.genogram.service.IProSysWebMenuService;
import com.genogram.service.IProSysWebNewsShowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 省级网站文章挂靠位置表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProSysWebNewsShowServiceImpl extends ServiceImpl<ProSysWebNewsShowMapper, ProSysWebNewsShow> implements IProSysWebNewsShowService {
    private final static String SHOW_ID = "showId=";
    private final static String SITE_ID = "siteId=";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_1 ="index_architecture_pay_in_person_1";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_2 ="index_architecture_pay_in_person_2";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_3 ="index_architecture_pay_in_person_3";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_2_2 ="index_architecture_pay_in_person_2_2";
    private final static String INDEX_CHARITY_PAY_OUT ="index_charity_pay_out";
    private final static String INDEX_CHARITY_PAY_OUT_3 ="index_charity_pay_out_3";
    private final static int NUM_100 = 100;

    @Autowired
    private IProSysWebMenuService proSysWebMenuService;

    @Override
    public List<SysWebMenuVo> getIndexMenu(String siteId) {
        return null;
    }

    @Override
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId) {
        return null;
    }

    @Override
    public void initWebMenu(int siteId) {

    }

    @Override
    public void updateTitlesById(int id, String menuName) {

    }

    @Override
    public String delTitlesById(int id) {
        return null;
    }

    @Override
    public void addTitles(int siteId, String menuName, int parentId) {

    }
}
