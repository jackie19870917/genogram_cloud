package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.FanSysWebMenu;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.mapper.FanSysWebMenuMapper;
import com.genogram.mapper.FanSysWebNewsShowMapper;
import com.genogram.service.IFanSysWebNewsShowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊网站文章挂靠位置表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanSysWebNewsShowServiceImpl extends ServiceImpl<FanSysWebNewsShowMapper, FanSysWebNewsShow> implements IFanSysWebNewsShowService {

    @Autowired
    private FanSysWebMenuMapper fanSysWebMenuMapper;

    @Override
    public List<FanSysWebMenuVo> getMenu(String siteId) {
        List<FanSysWebMenuVo> volist = new ArrayList();

        //单表查询list
        List<FanSysWebNewsShow> list = this.selectList(new EntityWrapper<FanSysWebNewsShow>().eq("fan_sys_site_id",siteId));
        list.forEach((FanSysWebNewsShow a)->{
            FanSysWebMenuVo vo = new FanSysWebMenuVo();
            vo.setShowId(a.getId());
            vo.setFanSysSiteId(a.getFanSysSiteId());
            vo.setFanSysWebMenuId(a.getFanSysWebMenuId());
            //获取菜单信息
            FanSysWebMenu menu = fanSysWebMenuMapper.selectById(vo.getFanSysWebMenuId());
            vo.setMenuName(menu.getMenuName());
            vo.setMenuCode(menu.getMenuCode());
            vo.setTreeNum(menu.getTreeNum());
            vo.setOrderIndex(menu.getOrderIndex());
            vo.setMenuType(menu.getMenuType());
            volist.add(vo);
        });
        return volist;
    }
}
