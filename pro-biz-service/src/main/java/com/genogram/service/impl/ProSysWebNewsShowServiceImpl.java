package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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

        List<SysWebMenuVo> volist = new ArrayList();

        SysWebMenuVo vo = new SysWebMenuVo();
        //轮播图
        vo = setIndexMenu(siteId,"轮播图","fan_index_slide_pic","genogram/proIndex/index/getProIndexSlidePic?siteId=","");
        volist.add(vo);
        //简介
        vo = setIndexMenu(siteId,"简介","index_summary","genogram/proIndex/index/getIndexInfo?siteId=","");
        volist.add(vo);

        //支出公开栏;收益公开栏
        vo = setIndexMenu(siteId,"支出公开栏",INDEX_CHARITY_PAY_OUT,"genogram/proNewsCharity/index/getProNewsCharityOut?showId=","");
        volist.add(vo);
        vo = setIndexMenu(siteId,"收益公开栏","index_architecture_pay_in","genogram/proNewsCharity/index/getProNewsCharityOut?showId=","");
        volist.add(vo);
        //公益基金(共享code 特殊处理)
        vo = setIndexMenu(siteId,"公益基金","index_fund_1","genogram/proNewsCharity/index/getProIndexFund?siteId=","");
        volist.add(vo);
        //捐款名人(共享code 特殊处理)
        vo = setIndexMenu(siteId,"捐款名人",INDEX_ARCHITECHTURE_PAY_IN_PERSON_1,"genogram/proNewsCharity/index/getDonorVoByCreateTime?showId=","");
        //家族头条
        vo = setIndexMenu(siteId,"家族头条","index_family_record1","","");
        volist.add(vo);
        //省级公告
        vo = setIndexMenu(siteId,"省级公告","index_family_record2","","");
        volist.add(vo);

        //资讯-联谊会发布-家族文化除去字派,记录家族除去视频
        vo = setIndexMenu(siteId,"联谊会发布","index_fan_news_recommend","","");
        volist.add(vo);
        //人物 - 家族长老 - 联谊会
        vo = setIndexMenu(siteId,"家族长老","index_fan_person_recommend_1","","");
        volist.add(vo);
        //人物 - 家族栋梁 - 联谊会
        vo = setIndexMenu(siteId,"家族栋梁","index_fan_person_recommend_2","","");
        volist.add(vo);

        //文化 - 字派 - 省级
        vo = setIndexMenu(siteId,"字派","index_zipai","genogram/proNewsCulture/getCommonalityPage?showId=","");
        volist.add(vo);
        //文化 - 迁徙之源 - 省级
        vo = setIndexMenu(siteId,"迁徙之源","index_family_qianxi","genogram/proNewsCulture/index/getFamilyIndexCulturePage?showId=","");
        volist.add(vo);
        //文化 - 家族祠堂 - 省级
        vo = setIndexMenu(siteId,"家族祠堂","index_family_culture","genogram/proNewsCulture/index/getFamilyIndexCulturePage?showId=","");
        volist.add(vo);

        //人物 - 祖先 - 省级
        vo = setIndexMenu(siteId,"祖先","index_family_person_1","","");
        volist.add(vo);
        //人物 - 名人 - 省级
        vo = setIndexMenu(siteId,"名人","index_family_person_2","","");
        volist.add(vo);
        //人物 - 精英 - 省级
        vo = setIndexMenu(siteId,"精英","index_family_person_3","","");
        volist.add(vo);


        //慈善公益第二页的
        vo = setIndexMenu(siteId,"公益总金额","index_fund_3","genogram/proNewsCharity/index/getProIndexFund?siteId=","慈善公益第二页的");
        volist.add(vo);
        vo = setIndexMenu(siteId,"捐款名人",INDEX_ARCHITECHTURE_PAY_IN_PERSON_3,"genogram/proNewsCharity/index/getDonorVoByCreateTime?showId=","慈善公益第二页的");
        volist.add(vo);
        //财政用途支出第二页的
        vo = setIndexMenu(siteId,"支出公开栏",INDEX_CHARITY_PAY_OUT_3,"genogram/proNewsCharity/index/getProNewsCharityOut?showId=","慈善公益第二页的");
        volist.add(vo);


        return volist;


    }

    @Override
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId) {
        List<SysWebMenuVo> voList = new ArrayList<>();
        EntityWrapper<ProSysWebNewsShow> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("site_id",siteId);
        entityWrapper.eq("parent_id",menuId);
        List<ProSysWebNewsShow> list = this.selectList(entityWrapper);
        list.forEach((menu)->{
            SysWebMenuVo vo = new SysWebMenuVo();
            BeanUtils.copyProperties(menu,vo);
            vo.setApiUrl(vo.getApiUrl()+vo.getShowId());
            vo.setApiAdminUrl(vo.getApiAdminUrl()+vo.getShowId());
            voList.add(vo);
        });
        return voList;
    }

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
                String showId = siteId+""+menu.getId();
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

    @Override
    public void updateTitlesById(int id, String menuName) {
        ProSysWebNewsShow proSysWebNewsShows = this.selectById(id);
        proSysWebNewsShows.setMenuName(menuName);
        proSysWebNewsShows.setUpdateUser(1);
        proSysWebNewsShows.setUpdateTime(DateUtil.getCurrentTimeStamp());
        this.insertOrUpdate(proSysWebNewsShows);
    }

    @Override
    public String delTitlesById(int id) {
        ProSysWebNewsShow proSysWebNewsShows = this.selectById(id);
        if(proSysWebNewsShows.getIstatic().equals(0)){
            //固定的栏目不允许删除
            return "can not del the title";
        }else{
            this.deleteById(id);
            return "del successful";
        }
    }

    @Override
    public void addTitles(int siteId, String menuName, int parentId) {
        Wrapper<ProSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        entity.eq("parent_id", parentId);
        entity.orderBy("order_index", true);
        List<ProSysWebNewsShow> proSysWebNewsShowsList = this.selectList(entity);
        //得到最后一条参考记录
        ProSysWebNewsShow lastOne = proSysWebNewsShowsList.get(proSysWebNewsShowsList.size()-1);
        lastOne.setMenuName(menuName);

        if(lastOne.getMenuId()<NUM_100){
            //第一次添加100以内的,则组成新方式
            lastOne.setMenuId(lastOne.getTreeNum() *100);
        }else{
            lastOne.setMenuId(lastOne.getMenuId()+1);
        }
        lastOne.setOrderIndex(lastOne.getOrderIndex()+1);
        String showId = lastOne.getSiteId() + "" + lastOne.getMenuId();
        lastOne.setShowId(Integer.parseInt(showId));
        //手动添加的节点
        lastOne.setIstatic(1);

        lastOne.setUpdateUser(1);
        lastOne.setUpdateTime(DateUtil.getCurrentTimeStamp());
        lastOne.setCreateUser(1);
        lastOne.setCreateTime(DateUtil.getCurrentTimeStamp());

        this.insert(lastOne);
    }

    private SysWebMenuVo setIndexMenu(String siteId, String menuName, String menuCode, String api, String comments){
        SysWebMenuVo vo = new SysWebMenuVo();
        vo.setSiteId(Integer.parseInt(siteId));
        vo.setMenuName(menuName);
        vo.setMenuCode(menuCode);
        String apiUrl = api;
        if(api.contains(SHOW_ID)) {
            apiUrl = apiUrl+ getShowIdBySiteId(menuCode,siteId);
        }

        if(api.contains(SITE_ID)) {
            apiUrl = apiUrl+ siteId;
        }

        vo.setApiUrl(apiUrl);
        vo.setComments(comments);
        return vo;
    }

    private String getShowIdBySiteId(String menuCode,String siteId){
        String showId="";
        //慈善公益基金-个人捐款-捐款名录
        if(INDEX_ARCHITECHTURE_PAY_IN_PERSON_1.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_2.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_3.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_2_2.equals(menuCode)){
            Wrapper<ProSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", "index_architecture_pay_in_person");
            showId = getShowId(entity);
        }

        //慈善公益-支出公开-
        else if(INDEX_CHARITY_PAY_OUT.equals(menuCode) || INDEX_CHARITY_PAY_OUT_3.equals(menuCode)){
            Wrapper<ProSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", "index_charity_pay_out");
            showId = getShowId(entity);
        }

        else{
            Wrapper<ProSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", menuCode);
            showId = getShowId(entity);
        }

        return showId;
    }

    private String getShowId(Wrapper<ProSysWebNewsShow> entity){
        String showId="";
        ProSysWebNewsShow proSysWebNewsShows = this.selectOne(entity);
        if(proSysWebNewsShows != null ){
            showId = proSysWebNewsShows.getShowId().toString();
        }
        return showId;
    }
}
