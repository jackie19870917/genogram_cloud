package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.FanSysWebMenu;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.mapper.FanSysWebNewsShowMapper;
import com.genogram.service.IFanSysWebMenuService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private IFanSysWebMenuService fanSysWebMenuService;

    @Override
    public List<SysWebMenuVo> getIndexMenu(String siteId) {
        List<SysWebMenuVo> volist = new ArrayList();
        //首页联谊堂概况
        SysWebMenuVo vo = setIndexMenu(siteId, "首页联谊堂概况", "index_fan_summary", "/genogram/fanIndex/index/getFanIndexFamilySummarysPage?siteId=", "");
        volist.add(vo);
        //轮播图
        vo = setIndexMenu(siteId, "轮播图", "fan_index_slide_pic", "/genogram/fanIndex/index/getFanIndexSlidePicList?siteId=", "");
        volist.add(vo);
        //简介
        vo = setIndexMenu(siteId, "简介", "index_summary", "/genogram/fanIndex/index/getFanIndexInfo?siteId=", "");
        volist.add(vo);
        //公益基金(共享code 特殊处理)
        vo = setIndexMenu(siteId, "公益基金", "index_fund_1", "/genogram/fanNewsCharity/index/getFanIndexFund?siteId=", "");
        volist.add(vo);
        //捐款名人(共享code 特殊处理) 4个头像的，按最新排序
        vo = setIndexMenu(siteId, "捐款名人", INDEX_ARCHITECHTURE_PAY_IN_PERSON_1, "/genogram/fanNewsCharity/index/getPayUser?order=time&label=desc&pageSize=4&showId=", "按最新排序");
        volist.add(vo);
        //本地字派
        vo = setIndexMenu(siteId, "本地字派", "index_zipai", "/genogram/fanNewsCulture/index/getCommonalityIndexPage?showId=", "");
        volist.add(vo);
        //message
        vo = setIndexMenu(siteId, "message", "index_message", "/genogram/fanIndex/index/getChatRecordList?siteId=", "");
        volist.add(vo);
        //家族动态
        vo = setIndexMenu(siteId, "家族动态", "index_family_record1", "/genogram/fanNewsFamilyRecord/selectRecortPage?showId=", "");
        volist.add(vo);
        //县级公告
        vo = setIndexMenu(siteId, "县级公告", "index_family_record2", "/genogram/fanNewsFamilyRecord/selectRecortPage?showId=", "");
        volist.add(vo);
        //家族视频
        vo = setIndexMenu(siteId, "家族视频", "index_family_video", "/genogram/fanNewsFamilyRecord/selectVedioPage?showId=", "");
        volist.add(vo);

        //暂时写死 会长,副会长,族长,官员,企业家,店主
        vo = setIndexMenu(siteId, "组织架构", "index_architecture", "/genogram/fanIndex/getFamilyStructureList?siteId=", "");
        volist.add(vo);
        //家族文化
        vo = setIndexMenu(siteId, "家族文化", "index_family_culture", "/genogram/fanNewsCulture/index/getFamilyIndexCulturePage?siteId=", "");
        volist.add(vo);
        vo = setIndexMenu(siteId, "支出公开栏", INDEX_CHARITY_PAY_OUT, "/genogram/fanNewsCharity/index/getFanNewsCharityOutPage?showId=", "");
        volist.add(vo);
        vo = setIndexMenu(siteId, "收益公开栏", "index_architecture_pay_in", "/genogram/fanNewsCharity/index/getFanNewsCharityOutPage?showId=", "");
        volist.add(vo);
        //捐款名人最新排序 公益总金额 首页有2个 所以分开2
        vo = setIndexMenu(siteId, "公益总金额", "index_fund_2", "/genogram/fanNewsCharity/index/getFanIndexFund?siteId=", "");
        //首页第二个捐款名人,1.按最多
        volist.add(vo);
        //捐款名人(最多排序) 公益总金额 首页有2个 所以分开2
        vo = setIndexMenu(siteId, "捐款名人", INDEX_ARCHITECHTURE_PAY_IN_PERSON_2_2, "/genogram/fanNewsCharity/index/getPayUser?order=time&label=desc&pageSize=6&showId=", "按最新排序");
        volist.add(vo);
        //公共产业
        vo = setIndexMenu(siteId, "公共产业", "index_industry_public", "/genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?showId=", "");
        volist.add(vo);
        //个产业
        vo = setIndexMenu(siteId, "私人产业", "index_industry_person", "/genogram/fanNewsIndustry/index/getFamilyIndexIndustryList?showId=", "");
        volist.add(vo);
        //慈善公益第二页的
        vo = setIndexMenu(siteId, "公益总金额", "index_fund_3", "/genogram/fanNewsCharity/index/getFanIndexFund?siteId=", "慈善公益第二页的");
        volist.add(vo);
        vo = setIndexMenu(siteId, "捐款名人", INDEX_ARCHITECHTURE_PAY_IN_PERSON_3, "/genogram/fanNewsCharity/index/getDonorPage?showId=", "慈善公益第二页的");
        volist.add(vo);
        //财政用途支出第二页的
        vo = setIndexMenu(siteId, "支出公开栏", INDEX_CHARITY_PAY_OUT_3, "/genogram/fanNewsCharity/index/getFanNewsCharityOutPage?showId=", "慈善公益第二页的");
        volist.add(vo);
        return volist;
    }

    @Override
    public SysWebMenuVo getSiteIdByShowId(Integer showId) {
        Wrapper<FanSysWebNewsShow> wrapper = new EntityWrapper<>();
        wrapper.eq("show_id", showId);

        FanSysWebNewsShow fanSysWebNewsShow = this.selectOne(wrapper);

        SysWebMenuVo sysWebMenuVo = new SysWebMenuVo();
        BeanUtils.copyProperties(fanSysWebNewsShow, sysWebMenuVo);

        return sysWebMenuVo;
    }

    @Override
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId) {
        List<SysWebMenuVo> voList = new ArrayList<>();
        EntityWrapper<FanSysWebNewsShow> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("site_id", siteId);
        entityWrapper.eq("parent_id", menuId);
        List<FanSysWebNewsShow> list = this.selectList(entityWrapper);
        list.forEach((menu) -> {
            SysWebMenuVo vo = new SysWebMenuVo();
            BeanUtils.copyProperties(menu, vo);
            vo.setApiUrl(vo.getApiUrl() + vo.getShowId());
            vo.setApiAdminUrl(vo.getApiAdminUrl() + vo.getShowId());
            voList.add(vo);
        });

        return voList;
    }

    @Override
    public void initWebMenu(int siteId) {
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        List<FanSysWebNewsShow> list = this.selectList(entity);
        List<FanSysWebNewsShow> showList = new ArrayList<>();

        if (list.isEmpty()) {
            List<FanSysWebMenu> menuList = fanSysWebMenuService.selectList(null);
            menuList.forEach(menu -> {
                FanSysWebNewsShow show = new FanSysWebNewsShow();
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

    private SysWebMenuVo setIndexMenu(String siteId, String menuName, String menuCode, String api, String comments) {
        SysWebMenuVo vo = new SysWebMenuVo();
        vo.setSiteId(Integer.parseInt(siteId));
        vo.setMenuName(menuName);
        vo.setMenuCode(menuCode);
        String apiUrl = api;
        if (api.contains(SHOW_ID)) {
            apiUrl = apiUrl + getShowIdBySiteId(menuCode, siteId);
        }

        if (api.contains(SITE_ID)) {
            apiUrl = apiUrl + siteId;
        }

        vo.setApiUrl(apiUrl);
        vo.setComments(comments);
        return vo;
    }

    private String getShowIdBySiteId(String menuCode, String siteId) {
        String showId = "";
        //慈善公益基金-个人捐款-捐款名录
        if (INDEX_ARCHITECHTURE_PAY_IN_PERSON_1.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_2.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_3.equals(menuCode) || INDEX_ARCHITECHTURE_PAY_IN_PERSON_2_2.equals(menuCode)) {
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", "index_architecture_pay_in_person");
            showId = getShowId(entity);
        }

        //慈善公益-支出公开-
        else if (INDEX_CHARITY_PAY_OUT.equals(menuCode) || INDEX_CHARITY_PAY_OUT_3.equals(menuCode)) {
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", "index_charity_pay_out");
            showId = getShowId(entity);
        } else {
            Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
            entity.eq("site_id", siteId);
            entity.eq("menu_code", menuCode);
            showId = getShowId(entity);
        }

        return showId;
    }

    private String getShowId(Wrapper<FanSysWebNewsShow> entity) {
        String showId = "";
        FanSysWebNewsShow fanSysWebNewsShows = this.selectOne(entity);
        if (fanSysWebNewsShows != null) {
            showId = fanSysWebNewsShows.getShowId().toString();
        }
        return showId;
    }


    @Override
    public void updateTitlesById(int id, String menuName) {
        FanSysWebNewsShow fanSysWebNewsShows = this.selectById(id);
        fanSysWebNewsShows.setMenuName(menuName);
        fanSysWebNewsShows.setUpdateUser(1);
        fanSysWebNewsShows.setUpdateTime(DateUtil.getCurrentTimeStamp());
        this.insertOrUpdate(fanSysWebNewsShows);
    }

    @Override
    public String delTitlesById(int id) {
        FanSysWebNewsShow fanSysWebNewsShows = this.selectById(id);
        if (fanSysWebNewsShows.getIstatic().equals(0)) {
            //固定的栏目不允许删除
            return "can not del the title";
        } else {
            this.deleteById(id);
            return "del successful";
        }
    }

    @Override
    public void addTitles(int siteId, String menuName, int parentId) {
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        entity.eq("parent_id", parentId);
        entity.orderBy("order_index", true);
        List<FanSysWebNewsShow> fanSysWebNewsShowsList = this.selectList(entity);
        FanSysWebNewsShow lastOne = fanSysWebNewsShowsList.get(fanSysWebNewsShowsList.size() - 1);
        lastOne.setMenuName(menuName);

        if (lastOne.getMenuId() < NUM_100) {
            //第一次添加100以内的,则组成新方式
            lastOne.setMenuId(lastOne.getParentId() * 100);
        } else {
            lastOne.setMenuId(lastOne.getMenuId() + 1);
        }
        lastOne.setOrderIndex(lastOne.getOrderIndex() + 1);
        //给个999 避免重复
        String showId = lastOne.getSiteId() + "99" + lastOne.getMenuId();
        lastOne.setShowId(Integer.parseInt(showId));
        //手动添加的节点
        lastOne.setIstatic(1);
        lastOne.setMenuCode(UUID.randomUUID().toString().replaceAll("-", ""));

        lastOne.setUpdateUser(1);
        lastOne.setUpdateTime(DateUtil.getCurrentTimeStamp());
        lastOne.setCreateUser(1);
        lastOne.setCreateTime(DateUtil.getCurrentTimeStamp());

        this.insert(lastOne);
    }

    @Override
    public FanSysWebNewsShow getSysWebNewsShowBySiteIdAndMenuCode(int siteId, String menuCode) {
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        entity.eq("menu_code", menuCode);
        FanSysWebNewsShow fanSysWebNewsShow = this.selectOne(entity);
        return fanSysWebNewsShow;
    }

    @Override
    public List<FanSysWebNewsShow> getMenuCodeByParentId(int siteId, int parentId) {
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        entity.eq("parent_id", parentId);
        return this.selectList(entity);
    }
}
