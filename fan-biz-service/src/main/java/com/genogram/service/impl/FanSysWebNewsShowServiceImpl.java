package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @Override
    public List<FanSysWebMenuVo> getMenu(String siteId) {
        List<FanSysWebMenuVo> volist = new ArrayList();

        //单表查询list
        List<FanSysWebNewsShow> list = this.selectList(new EntityWrapper<FanSysWebNewsShow>().eq("fan_sys_site_id",siteId));
        list.forEach((a)->{
            FanSysWebMenuVo vo = new FanSysWebMenuVo();
            vo.setShowId(a.getId());
            vo.setFanSysSiteId(a.getFanSysSiteId());
            vo.setFanSysWebMenuId(a.getFanSysWebMenuId());
            //获取菜单信息
            FanSysWebMenu menu = fanSysWebMenuMapper.selectById(vo.getFanSysWebMenuId());
            vo.setMenuName(menu.getMenuName());
            vo.setMenuType(menu.getMenuType());
            vo.setTreeNum(menu.getTreeNum());
            vo.setParentId(menu.getParentId());
            String url = menu.getApiUrl()!=null ? menu.getApiUrl()+a.getId():null;

            vo.setApiUrl("192.168.2.179:8090"+url);
            vo.setOrderIndex(menu.getOrderIndex());
            volist.add(vo);
        });

        //级联模式,只要一级菜单
        List firstList = getChildMenu(volist);
        return firstList;
    }

    private List<FanSysWebMenuVo> getChildMenu(List<FanSysWebMenuVo> volist){
        //只要一级菜单
        List<FanSysWebMenuVo> list = new ArrayList<>();

        volist.forEach((a)->{
            if(a.getTreeNum()==1){
                Map map = new HashMap<>();
                List<FanSysWebMenuVo> child = new ArrayList<>();
               //for
                volist.forEach((b)->{
                if(a.getFanSysWebMenuId() == b.getParentId()){
                    FanSysWebMenuVo vo = new FanSysWebMenuVo();
                    vo.setShowId(vo.getShowId());
                    vo.setMenuName(b.getMenuName());
                    vo.setOrderIndex(b.getOrderIndex());
                    vo.setMenuType(b.getMenuType());
                    vo.setApiUrl(b.getApiUrl());
                    child.add(vo);
                }
                } );
                a.setChild(child);

                list.add(a);
            }
        });

        return list;
    }

    @Override
    public List<FanSysWebMenuVo> getIndexMenu(String siteId) {
        List<FanSysWebMenuVo> volist = new ArrayList();

        //首页联谊堂概况
        FanSysWebMenuVo vo = setIndexMenu(siteId,"首页联谊堂概况","index_fan_summary","#","api:");
        volist.add(vo);

        //简介
        vo = setIndexMenu(siteId,"简介","index_summary","#","api:");
        volist.add(vo);

        //公益基金
        vo = setIndexMenu(siteId,"公益基金","index_fund_1","#","api:");
        volist.add(vo);
        //捐款名人
        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_1","#","api:");
        volist.add(vo);

        //本地字派
        vo = setIndexMenu(siteId,"本地字派","index_zipai","genogram/fanNewsCulture/index/getCommonalityIndexPage?showId=","api:");
        volist.add(vo);

        //message
        vo = setIndexMenu(siteId,"message","index_message","genogram/fanIndex/index/getChatRecordList?siteId=","api:");
        volist.add(vo);

        //家族动态
        vo = setIndexMenu(siteId,"家族动态","index_family_record1","#","api:");
        volist.add(vo);

        //家族动态
        vo = setIndexMenu(siteId,"县级公告","index_family_record2","#","api:");
        volist.add(vo);

        //暂时写死 会长,副会长,族长,官员,企业家,店主
        vo = setIndexMenu(siteId,"会长","index_architecture_huizhang","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"副会长","index_architecture_fuhuizhang","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"官员","index_architecture_guanyuan","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"企业家","index_architecture_qiyejia","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"店主企业家","index_architecture_dianzhu","#","api:");
        volist.add(vo);

        //家族文化
        vo = setIndexMenu(siteId,"家族文化","index_family_culture","genogram/fanNewsCulture/index/getFamilyIndexCulturePage?showId=","api:");
        volist.add(vo);


        vo = setIndexMenu(siteId,"支出公开栏","index_charity_pay_out","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"收益公开栏","index_architecture_pay_in","#","api:");
        volist.add(vo);

        //公益总金额 首页有2个 所以分开2
        vo = setIndexMenu(siteId,"公益总金额","index_fund_2","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_2","#","api:");
        volist.add(vo);

        //公共产业
        vo = setIndexMenu(siteId,"公共产业","index_industry_public","genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?type=1&showId=","api:");
        volist.add(vo);
        //个产业
        vo = setIndexMenu(siteId,"私人产业","index_industry_person","genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?type=2&showId=","api:");
        volist.add(vo);

        //慈善公益第二页的
        vo = setIndexMenu(siteId,"公益总金额","index_fund_3","#","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_3","#","api:");
        volist.add(vo);

        //财政支出第二页的
        vo = setIndexMenu(siteId,"支出公开栏","index_charity_pay_out_3","#","api:");
        volist.add(vo);

        return volist;
    }


    private FanSysWebMenuVo setIndexMenu(String siteId,String menuName,String menuType,String api, String comments){
        FanSysWebMenuVo vo = new FanSysWebMenuVo();
        vo.setFanSysSiteId(Integer.parseInt(siteId));
        vo.setMenuName(menuName);
        vo.setMenuType(menuType);
        String apiUrl = "192.168.2.179:8090/"+api;
         if(api.contains("showId=")) {
             apiUrl = apiUrl+ getShowIdBySiteId(menuType,siteId);
         }

        if(api.contains("siteId=")) {
            apiUrl = apiUrl+ siteId;
        }


        vo.setApiUrl(apiUrl);
        vo.setComments(comments);
        return vo;
    }

    private String getShowIdBySiteId(String menuType,String siteId){
        String showId="";

        //家族字派
        if(menuType.equals("index_zipai")){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 8);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }


        //家族文化-家族祠堂
        if(menuType.equals("index_family_culture")){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 9);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族产业-公共产业
        if(menuType.equals("index_industry_public")){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 17);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族产业-个人产业
        if(menuType.equals("index_industry_person")){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 18);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        return showId;
    }
}
