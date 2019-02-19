package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.ChiSysWebMenu;
import com.genogram.entity.ChiSysWebNewsShow;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.mapper.ChiSysWebNewsShowMapper;
import com.genogram.service.IChiSysWebMenuService;
import com.genogram.service.IChiSysWebNewsShowService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级web菜单基础表,文章位置表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiSysWebNewsShowServiceImpl extends ServiceImpl<ChiSysWebNewsShowMapper, ChiSysWebNewsShow> implements IChiSysWebNewsShowService {

    private final static String SHOW_ID = "showId=";
    private final static String SITE_ID = "siteId=";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_1 = "index_architecture_pay_in_person_1";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_2 = "index_architecture_pay_in_person_2";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_3 = "index_architecture_pay_in_person_3";
    private final static String INDEX_ARCHITECHTURE_PAY_IN_PERSON_2_2 = "index_architecture_pay_in_person_2_2";
    private final static String INDEX_CHARITY_PAY_OUT = "index_charity_pay_out";
    private final static String INDEX_CHARITY_PAY_OUT_3 = "index_charity_pay_out_3";
    private final static int NUM_100 = 100;

    @Autowired
    private IChiSysWebMenuService chiSysWebMenuService;

    @Override
    public void initWebMenu(int siteId) {
        Wrapper<ChiSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        List<ChiSysWebNewsShow> list = this.selectList(entity);
        List<ChiSysWebNewsShow> showList = new ArrayList<>();

        if (list.isEmpty()) {
            List<ChiSysWebMenu> menuList = chiSysWebMenuService.selectList(null);
            menuList.forEach(menu -> {
                ChiSysWebNewsShow show = new ChiSysWebNewsShow();
                BeanUtils.copyProperties(menu, show);
                show.setSiteId(siteId);
                //加3个9避免 重复
                String showId = siteId + "99" + menu.getId();
                show.setShowId(Integer.parseInt(showId));
                show.setMenuId(menu.getId());
                show.setUpdateUser(1);
                show.setUpdateTime(DateUtil.getCurrentTimeStamp());
                show.setCreateUser(1);
                show.setCreateTime(DateUtil.getCurrentTimeStamp());

                showList.add(show);
            });
        }
        if (showList != null && !showList.isEmpty()) {
            this.insertBatch(showList);
        }
    }
}
