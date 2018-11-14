package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.FanSysWebMenu;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entity.ProSysWebMenu;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.mapper.ProSysWebNewsShowMapper;
import com.genogram.service.IProSysWebMenuService;
import com.genogram.service.IProSysWebNewsShowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private IProSysWebMenuService proSysWebMenuService;

    @Override
    public List<SysWebMenuVo> getTitlesByMenuId(String hostIp,boolean isWeb, int siteId, int menuId) {
        List<SysWebMenuVo> voList = new ArrayList<>();

        EntityWrapper<ProSysWebMenu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_id",menuId);
        List<ProSysWebMenu> list = proSysWebMenuService.selectList(entityWrapper);
        list.forEach((menu)->{
            SysWebMenuVo vo = new SysWebMenuVo();
            BeanUtils.copyProperties(menu,vo);

            //计算showId
            EntityWrapper<ProSysWebNewsShow> entityWrapper2 = new EntityWrapper<>();
            entityWrapper2.eq("fan_sys_site_id",siteId);
            entityWrapper2.eq("fan_sys_web_menu_id",menu.getId());
            ProSysWebNewsShow proSysWebNewsShow = this.selectOne(entityWrapper2);
            //set showId
            vo.setShowId(proSysWebNewsShow.getId());
            //设置后台API地址
            if(isWeb){
                vo.setApiUrl(hostIp+menu.getApiUrl()+vo.getShowId());
            }else{
                vo.setApiUrl(hostIp+menu.getApiAdminUrl()+vo.getShowId());
            }

            voList.add(vo);
        });

        return voList;
    }

    @Override
    public void initWebNewsShow(int siteId) {

    }

}
