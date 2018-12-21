package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.mapper.FanNewsFamousAncestorMapper;
import com.genogram.service.CommonIFanNewsFamousAncestorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.service.IFanProNewsFamousAncestorService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-祖先分支 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamousAncestorServiceImpl extends ServiceImpl<FanNewsFamousAncestorMapper, FanNewsFamousAncestor> implements IFanNewsFamousAncestorService {

    @Autowired
    private IFanProNewsFamousAncestorService proNewsFamousAncestorService;

    @Autowired
    private FanNewsFamousAncestorMapper fanNewsFamousAncestorMapper;

    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    /**
     * 联谊会联谊会祖先查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-21
     * @Time: 10:36
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<FanNewsFamousAncestor> getFamousAncestorPage(Integer siteId, Integer pageNo, Integer pageSize) {
        //查询showId 显示位置Id
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper<>();
        entity.eq("site_Id", siteId);
        //fan_sys_web_news_show 表 menu_id  8表示祖先分支
        entity.eq("menu_id", 8);
        FanSysWebNewsShow fanSysWebNewsShow = fanSysWebNewsShowService.selectOne(entity);
        //parent_id 为0代表主数据
        Integer parentId = 0;
        //查询条件
        Wrapper<FanNewsFamousAncestor> entityAncestor = new EntityWrapper<>();
        entityAncestor.eq("show_id", fanSysWebNewsShow.getShowId());
        entityAncestor.eq("parent_id", parentId);
        entityAncestor.orderBy("update_time", false);
        Page<FanNewsFamousAncestor> fanNewsFamousAncestorPage = this.selectPage(new Page<FanNewsFamousAncestor>(pageNo, pageSize), entityAncestor);
        return fanNewsFamousAncestorPage;
    }

    /**
     * 联谊会祖先人物详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-21
     * @Time: 10:36
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public AncestorsBranchVo getFamousAncestorDetails(Integer id) {
        //创建AncestorsBranchVo返回的实体类
        AncestorsBranchVo ancestorsBranchVo = new AncestorsBranchVo();
        //根据主键查询
        FanNewsFamousAncestor fanNewsFamousAncestor = this.selectById(id);
        //根据父Id查询分支后裔
        Wrapper<FanNewsFamousAncestor> entity = new EntityWrapper<>();
        entity.eq("parent_id", id);
        entity.orderBy("update_time", false);
        List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
        //封装到要返回的实体类中
        BeanUtils.copyProperties(fanNewsFamousAncestor, ancestorsBranchVo);
        if (fanNewsFamousAncestors.size() != 0) {
            ancestorsBranchVo.setFanNewsFamousAncestorList(fanNewsFamousAncestors);
        }
        return ancestorsBranchVo;
    }

    /**
     * 联谊会祖先后台添加模糊查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 15:58
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map) {
        List<AncestorsBranchVo> famousAncestorVaguePage = fanNewsFamousAncestorMapper.getFamousAncestorVaguePage(mapPage, map);
        if (famousAncestorVaguePage.size() == 0) {
            return null;
        }
        mapPage.setRecords(famousAncestorVaguePage);
        return mapPage;
    }

    /**
     * 联谊会添加 修改
     *
     * @param fanNewsFamousAncestor
     * @param proSplit
     * @param fanSplit
     */
    @Override
    public Boolean addFamousAncestor(FanNewsFamousAncestor fanNewsFamousAncestor, List<String> proSplit, List<String> fanSplit, AllUserLogin userLoginInfoByToken) {
        //父iD第一级为0
        fanNewsFamousAncestor.setParentId(0);
        if (fanNewsFamousAncestor.getId() != null) {
            //根据父ID查询人物对象集合
            Wrapper<FanNewsFamousAncestor> entity = new EntityWrapper<FanNewsFamousAncestor>();
            entity.eq("parent_id", fanNewsFamousAncestor.getId());
            List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
            //判断是否有分支
            if (fanNewsFamousAncestors.size() != 0) {
                //获取祖先分支id集合
                List<Integer> list = new ArrayList<>();
                for (FanNewsFamousAncestor fanNewsFamous : fanNewsFamousAncestors) {
                    list.add(fanNewsFamous.getId());
                }
                //删除祖先分支
                this.deleteBatchIds(list);
            }
            //修改时间
            fanNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            fanNewsFamousAncestor.setUpdateUser(userLoginInfoByToken.getId());
        } else {
            //创建时间
            fanNewsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
            fanNewsFamousAncestor.setCreateUser(userLoginInfoByToken.getId());
            //修改时间
            fanNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            fanNewsFamousAncestor.setUpdateUser(userLoginInfoByToken.getId());
        }

        //插入主数据
        boolean insert = this.insertOrUpdate(fanNewsFamousAncestor);

        //修改时修改省级县级分支后裔的数据
        if (fanNewsFamousAncestor.getId() != null) {
            //查询县级的分支后裔
            Wrapper<FanNewsFamousAncestor> entity = new EntityWrapper<FanNewsFamousAncestor>();
            //分支ID  (fan或者pro 的主键)
            entity.eq("branch_id", fanNewsFamousAncestor.getId());
            //分类  1 代表县级2代表省级
            entity.eq("source_classify", 1);
            List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
            //新建县级分支后裔修改集合
            List<FanNewsFamousAncestor> fanNews = new ArrayList<>();
            if (fanNewsFamousAncestors.size() != 0) {
                for (FanNewsFamousAncestor newsFamousAncestor : fanNewsFamousAncestors) {
                    //新建县级祖先分支实体类
                    FanNewsFamousAncestor fan = new FanNewsFamousAncestor();
                    //修改数据放入到查询数据中
                    BeanUtils.copyProperties(fanNewsFamousAncestor, fan);
                    fan.setId(newsFamousAncestor.getId());
                    fan.setShowId(newsFamousAncestor.getShowId());
                    fan.setParentId(newsFamousAncestor.getParentId());
                    fanNews.add(fan);
                }
                this.updateBatchById(fanNews);
                ////查询省级的分支后裔
                Wrapper<ProNewsFamousAncestor> entityPro = new EntityWrapper<ProNewsFamousAncestor>();
                //分支ID  (fan或者pro 的主键)
                entityPro.eq("branch_id", fanNewsFamousAncestor.getId());
                //分类  1 代表县级2代表省级
                entityPro.eq("source_classify", 2);
                List<ProNewsFamousAncestor> proNewsFamousAncestors = proNewsFamousAncestorService.selectList(entityPro);
                //新建县级分支后裔修改集合
                List<ProNewsFamousAncestor> proNews = new ArrayList<>();
                if (proNewsFamousAncestors.size() != 0) {
                    for (ProNewsFamousAncestor proNewsFamousAncestor : proNewsFamousAncestors) {
                        //新建省级祖先分支实体类
                        ProNewsFamousAncestor pro = new ProNewsFamousAncestor();
                        //修改数据放入到查询数据中
                        BeanUtils.copyProperties(fanNewsFamousAncestor, pro);
                        pro.setId(proNewsFamousAncestor.getId());
                        pro.setShowId(proNewsFamousAncestor.getShowId());
                        pro.setParentId(proNewsFamousAncestor.getParentId());
                        proNews.add(pro);
                    }
                    proNewsFamousAncestorService.updateBatchById(proNews);
                }
            }
        }


        //省级数据list集合
        List<ProNewsFamousAncestor> proNewsFamousAncestors = null;

        //县级数据list集合
        List<FanNewsFamousAncestor> fanNewsFamousAncestors = null;

        //判断是否有县级数据
        if (fanSplit != null) {
            //查询县级
            fanNewsFamousAncestors = this.selectBatchIds(fanSplit);
            for (FanNewsFamousAncestor newsFamousAncestor : fanNewsFamousAncestors) {
                //修改分支的showId为-1
                newsFamousAncestor.setShowId(-1);
                //父Id
                newsFamousAncestor.setParentId(fanNewsFamousAncestor.getId());
                //分类  1 代表县级2代表省级
                newsFamousAncestor.setSourceClassify(1);
                //分支ID  (fan或者pro 的主键)
                newsFamousAncestor.setBranchId(newsFamousAncestor.getId());
                //时间
                newsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
                newsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            }
            if (fanNewsFamousAncestors.size() != 0) {
                //批量插入
                this.insertBatch(fanNewsFamousAncestors);
            }
        }

        //判断fanNewsFamousAncestors 集合是否有数据,有 就清空
        if (fanNewsFamousAncestors != null && fanNewsFamousAncestors.size() != 0) {
            fanNewsFamousAncestors.clear();
        }

        //判断是否有省级数据
        if (proSplit != null) {
            //查询省级
            proNewsFamousAncestors = proNewsFamousAncestorService.selectBatchIds(proSplit);
            for (ProNewsFamousAncestor proNewsFamousAncestor : proNewsFamousAncestors) {
                FanNewsFamousAncestor famousAncestor = new FanNewsFamousAncestor();
                BeanUtils.copyProperties(proNewsFamousAncestor, famousAncestor);
                fanNewsFamousAncestors.add(famousAncestor);
            }
            //修改父Id
            for (FanNewsFamousAncestor newsFamousAncestor : fanNewsFamousAncestors) {
                //修改分支的showId为-1
                newsFamousAncestor.setShowId(-1);
                //父Id
                newsFamousAncestor.setParentId(fanNewsFamousAncestor.getId());
                //分类  1 代表县级2代表省级
                newsFamousAncestor.setSourceClassify(2);
                //分支ID  (fan或者pro 的主键)
                newsFamousAncestor.setBranchId(newsFamousAncestor.getId());
                //时间
                newsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
                newsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            }
            if (proNewsFamousAncestors.size() != 0) {
                //批量插入
                this.insertBatch(fanNewsFamousAncestors);
            }

        }
        return insert;
    }

    /**
     * 联谊会删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteFamousAncestor(Integer id) {
        //根据父ID查询人物对象集合
        Wrapper<FanNewsFamousAncestor> entity = new EntityWrapper();
        entity.eq("parent_id", id);
        List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
        //判断是否有分支
        if (fanNewsFamousAncestors.size() != 0) {
            //获取祖先分支id集合
            List<Integer> list = new ArrayList<>();
            for (FanNewsFamousAncestor fanNewsFamousAncestor : fanNewsFamousAncestors) {
                list.add(fanNewsFamousAncestor.getId());
            }
            //删除祖先分支
            this.deleteBatchIds(list);
        }
        //删除主表
        Boolean aBoolean = this.deleteById(id);
        return aBoolean;
    }
}
