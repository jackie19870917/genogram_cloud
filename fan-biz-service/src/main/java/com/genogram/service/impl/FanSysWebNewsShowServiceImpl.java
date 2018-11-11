package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanSysWebMenu;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.mapper.FanSysWebMenuMapper;
import com.genogram.mapper.FanSysWebNewsShowMapper;
import com.genogram.service.IFanSysWebMenuService;
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
    private IFanSysWebMenuService fanSysWebMenuService;

    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @Override
    public List<FanSysWebMenuVo> getMenu(String hostIp,String siteId,boolean isWeb, EntityWrapper<FanSysWebNewsShow> entityWrapper) {
        List<FanSysWebMenuVo> volist = new ArrayList();
        //单表查询list
        List<FanSysWebNewsShow> list = this.selectList(entityWrapper);
        list.forEach((a)->{
            FanSysWebMenuVo vo = new FanSysWebMenuVo();
            vo.setShowId(a.getId());
            vo.setFanSysSiteId(a.getFanSysSiteId());
            vo.setFanSysWebMenuId(a.getFanSysWebMenuId());
            //获取菜单信息
            FanSysWebMenu menu = fanSysWebMenuService.selectById(vo.getFanSysWebMenuId());
            vo.setMenuName(menu.getMenuName());
            vo.setMenuType(menu.getMenuType());
            vo.setTreeNum(menu.getTreeNum());
            vo.setParentId(menu.getParentId());
            vo.setMenuCode(menu.getMenuCode());
            String url = menu.getApiUrl()!=null ? menu.getApiUrl()+a.getId():null;

            vo.setApiUrl(hostIp+url);
            vo.setOrderIndex(menu.getOrderIndex());
            //web的第一级
            if(isWeb){
                if(menu.getIsWeb()!=null && menu.getIsWeb()==1){
                    volist.add(vo);
                }
            }
            //admin的第一级
            else{
                if(menu.getIsAdmin()!=null && menu.getIsAdmin()==1){
                    volist.add(vo);
                }
            }
        });

        //级联模式,只要一级菜单
        List firstList = getChildMenu(volist);
        return firstList;
    }

    private List<FanSysWebMenuVo> getChildMenu(List<FanSysWebMenuVo> volist){
        //只要一级菜单
        List<FanSysWebMenuVo> list = new ArrayList<>();

        volist.forEach((a)->{
            //一级菜单
            if(a.getTreeNum()==1){
                Map map = new HashMap<>(16);
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
        FanSysWebMenuVo vo = setIndexMenu(siteId,"首页联谊堂概况","index_fan_summary","genogram/fanIndex/index/getFanIndexFamilySummarysPage?siteId=","api:");
        volist.add(vo);

        //简介
        vo = setIndexMenu(siteId,"简介","index_summary","genogram/fanIndex/index/getFanIndexInfo?siteId=","api:");
        volist.add(vo);

        //公益基金
        vo = setIndexMenu(siteId,"公益基金","index_fund_1","genogram/fanNewsCharity/index/getFanIndexFund?siteId=","api:");
        volist.add(vo);
        //捐款名人
        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_1","genogram/fanNewsCharity/index/getDonorPage?showId=","api:");
        volist.add(vo);

        //本地字派
        vo = setIndexMenu(siteId,"本地字派","index_zipai","genogram/fanNewsCulture/index/getCommonalityIndexPage?showId=","api:");
        volist.add(vo);

        //message
        vo = setIndexMenu(siteId,"message","index_message","genogram/fanIndex/index/getChatRecordList?siteId=","api:");
        volist.add(vo);

        //家族动态
        vo = setIndexMenu(siteId,"家族动态","index_family_record1","genogram/fanNewsFamilyRecord/selectRecort?showId=","api:");
        volist.add(vo);

        //县级公告
        vo = setIndexMenu(siteId,"县级公告","index_family_record2","genogram/fanNewsFamilyRecord/selectRecort?showId=","api:");
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


        vo = setIndexMenu(siteId,"支出公开栏","index_charity_pay_out","genogram/fanNewsCharity/index/getFanNewsCharityOutPage?newsType=1&showId=","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"收益公开栏","index_architecture_pay_in","genogram/fanNewsCharity/index/getFanNewsCharityOutPage?newsType=2&showId=","api:");
        volist.add(vo);

        //公益总金额 首页有2个 所以分开2
        vo = setIndexMenu(siteId,"公益总金额","index_fund_2","genogram/fanNewsCharity/index/getFanIndexFund?siteId=","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_2","genogram/fanNewsCharity/index/getDonorPage?showId=","api:");
        volist.add(vo);

        //公共产业
        vo = setIndexMenu(siteId,"公共产业","index_industry_public","genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?type=1&showId=","api:");
        volist.add(vo);
        //个产业
        vo = setIndexMenu(siteId,"私人产业","index_industry_person","genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?type=2&showId=","api:");
        volist.add(vo);

        //慈善公益第二页的
        vo = setIndexMenu(siteId,"公益总金额","index_fund_3","genogram/fanNewsCharity/index/getFanIndexFund?siteId=","api:");
        volist.add(vo);

        vo = setIndexMenu(siteId,"捐款名人","index_architecture_pay_in_person_3","genogram/fanNewsCharity/index/getDonorPage?showId=","api:");
        volist.add(vo);

        //财政用途支出第二页的
        vo = setIndexMenu(siteId,"支出公开栏","index_charity_pay_out_3","genogram/fanNewsCharity/index/getFanNewsCharityOutPage?newsType=0&showId=","api:");
        volist.add(vo);

        return volist;
    }


    private FanSysWebMenuVo setIndexMenu(String siteId,String menuName,String menuType,String api, String comments){
        FanSysWebMenuVo vo = new FanSysWebMenuVo();
        vo.setFanSysSiteId(Integer.parseInt(siteId));
        vo.setMenuName(menuName);
        vo.setMenuType(menuType);
        String apiUrl = "http://192.168.2.179:8090/"+api;
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
        if("index_zipai".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 8);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }


        //家族文化-家族祠堂
        if("index_family_culture".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 9);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族产业-公共产业
        if("index_industry_public".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 17);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族产业-个人产业
        if("index_industry_person".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 18);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //慈善公益基金-个人捐款
        if("index_architecture_pay_in_person_1".equals(menuType) || "index_architecture_pay_in_person_2".equals(menuType) || "index_architecture_pay_in_person_3".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 16);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //慈善公益-支出公开-
        if("index_charity_pay_out".equals(menuType) || "index_charity_pay_out_3".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 14);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //慈善公益-收益公开
        if("index_architecture_pay_in".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 15);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族动态-家族动态
        if("index_family_record1".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 21);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }

        //家族动态-县级公告
        if("index_family_record2".equals(menuType)){
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<FanSysWebNewsShow>();
            entity.eq("fan_sys_site_id", siteId);
            entity.eq("fan_sys_web_menu_id", 22);
            List<FanSysWebNewsShow> fanSysWebNewsShows = iFanSysWebNewsShowService.selectList(entity);
            showId = fanSysWebNewsShows.get(0).getId().toString();
        }


        return showId;
    }
}
