package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysWebMenu;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entity.ProSysWebMenu;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.mapper.FanSysWebNewsShowMapper;
import com.genogram.mapper.ProSysWebNewsShowMapper;
import com.genogram.service.IFanSysWebMenuService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.service.IProSysWebMenuService;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 联谊网站文章挂靠位置表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
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
    public void initWebMenu(int siteId) {
        Wrapper<ProSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        List<ProSysWebNewsShow> list = this.selectList(entity);
        List<ProSysWebNewsShow> showList = new ArrayList<>();

        if(list.isEmpty()){
            List<ProSysWebMenu> menuList = proSysWebMenuService.selectList(null);
            menuList.forEach(menu->{
                ProSysWebNewsShow show = new ProSysWebNewsShow();
                BeanUtils.copyProperties(menu,show);
                show.setSiteId(siteId);
                //加3个9避免 重复
                String showId = siteId+"99"+menu.getId();
                show.setShowId( Integer.parseInt(showId));
                show.setMenuId(menu.getId());
                show.setUpdateUser(1);
                show.setUpdateTime(DateUtil.getCurrentTimeStamp());
                show.setCreateUser(1);
                show.setCreateTime(DateUtil.getCurrentTimeStamp());

                showList.add(show);
            });
        }
        if(showList!=null && !showList.isEmpty()){
            this.insertBatch(showList);
        }
    }
}
