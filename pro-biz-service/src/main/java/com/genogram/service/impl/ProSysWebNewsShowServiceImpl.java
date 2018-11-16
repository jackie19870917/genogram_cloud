package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
    @Autowired
    private IProSysWebMenuService proSysWebMenuService;

    @Override
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId) {
        List<SysWebMenuVo> voList = new ArrayList<>();

        EntityWrapper<ProSysWebMenu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_id",menuId);
        List<ProSysWebMenu> list = proSysWebMenuService.selectList(entityWrapper);
        list.forEach((menu)->{
            SysWebMenuVo vo = new SysWebMenuVo();
            BeanUtils.copyProperties(menu,vo);

            //计算showId
            EntityWrapper<ProSysWebNewsShow> entityWrapper2 = new EntityWrapper<>();
            entityWrapper2.eq("pro_sys_site_id",siteId);
            entityWrapper2.eq("pro_sys_web_menu_id",menu.getId());
            ProSysWebNewsShow proSysWebNewsShow = this.selectOne(entityWrapper2);
            //set showId
            vo.setShowId(proSysWebNewsShow.getShowId());
            //设置后台API地址
            vo.setApiUrl(menu.getApiUrl()+vo.getShowId());
            vo.setApiAdminUrl(menu.getApiAdminUrl()+vo.getShowId());

            voList.add(vo);
        });

        return voList;
    }

    @Override
    public boolean initWebNewsShow(int siteId) {
        boolean result = true;
        EntityWrapper<ProSysWebNewsShow> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("pro_sys_site_id",siteId);
        List list = this.selectList(entityWrapper);
        List<ProSysWebNewsShow> showList = new ArrayList<>();
        //则insert
        if(list==null || list.isEmpty()){
            EntityWrapper<ProSysWebMenu> entityWrapper2 = new EntityWrapper<>();
            //默认菜单
            entityWrapper2.eq("istatic",0);
            List<ProSysWebMenu> menuList = proSysWebMenuService.selectList(entityWrapper2);
            menuList.forEach((menu)->{
                ProSysWebNewsShow show = new ProSysWebNewsShow();
                show.setProSysSiteId(siteId);
                show.setProSysWebMenuId(menu.getId());
                String showId = String.valueOf(siteId) + String.valueOf(menu.getId());
                show.setShowId( Integer.parseInt(showId));
                //show.set
                showList.add(show);
            });
            this.insertBatch(showList);
        }else{
            result = false;
        }
        return true;
    }

    @Override
    public List<SysWebMenuVo> getIndexMenu(String siteId) {
        List<SysWebMenuVo> volist = new ArrayList();


        SysWebMenuVo vo = new SysWebMenuVo();

        //支出公开栏-省级
        vo = setIndexMenu(siteId,"支出公开栏","财务支出","","");
        volist.add(vo);
        //收益公开栏-省级
        vo = setIndexMenu(siteId,"收益公开栏","财务收益","","");
        volist.add(vo);

        //公益基金总金额
        vo = setIndexMenu(siteId,"公益基金总金额","公益基金","","");
        volist.add(vo);

        //捐款名人
        vo = setIndexMenu(siteId,"捐款名人","捐款名录","","api:");
        volist.add(vo);

        //家族头条-省级
        vo = setIndexMenu(siteId,"家族头条","家族头条","","");
        volist.add(vo);

        //省级公告-省级
        vo = setIndexMenu(siteId,"省级公告","省级公告","","");
        volist.add(vo);

        //资讯-联谊会发布-家族文化除去字派,记录家族除去视频,
        vo = setIndexMenu(siteId,"联谊会发布","省级公告","","");
        volist.add(vo);


        //人物 - 家族长老 - 联谊会
        vo = setIndexMenu(siteId,"家族长老","家族长老","","");
        volist.add(vo);

        //人物 - 家族栋梁 - 联谊会
        vo = setIndexMenu(siteId,"家族栋梁","家族栋梁","","");
        volist.add(vo);


        //文化 - 字派 - 省级
        vo = setIndexMenu(siteId,"字派","本地字派","","");
        volist.add(vo);

        //文化 - 迁徙之源 - 省级
        vo = setIndexMenu(siteId,"迁徙之源","迁徙之源","","");
        volist.add(vo);

        //文化 - 家族祠堂 - 省级
        vo = setIndexMenu(siteId,"家族祠堂","家族祠堂","","");
        volist.add(vo);


        //人物 - 祖先 - 省级
        vo = setIndexMenu(siteId,"祖先","祖先","","");
        volist.add(vo);

        //人物 - 名人 - 省级
        vo = setIndexMenu(siteId,"祖先","祖先","","");
        volist.add(vo);

        //人物 - 精英 - 省级
        vo = setIndexMenu(siteId,"祖先","祖先","","");
        volist.add(vo);


        return null;
    }

    private SysWebMenuVo setIndexMenu(String siteId,String showName, String menuName, String api, String comments){
        SysWebMenuVo vo = new SysWebMenuVo();
        vo.setFanSysSiteId(Integer.parseInt(siteId));
        vo.setMenuName(menuName);
        String apiUrl = api;
        if(api.contains("showId=")) {
            apiUrl = apiUrl+ getShowIdBySiteId(menuName,siteId);
        }

        if(api.contains("siteId=")) {
            apiUrl = apiUrl+ siteId;
        }
        vo.setApiUrl(apiUrl);
        vo.setComments(comments);
        return vo;
    }

    private String getShowIdBySiteId(String menuName,String siteId){

        return null;
    }

}
