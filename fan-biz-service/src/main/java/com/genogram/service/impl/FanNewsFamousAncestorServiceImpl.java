package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.mapper.FanNewsFamousAncestorMapper;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IProNewsFamousAncestorService;
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
    private IProNewsFamousAncestorService proNewsFamousAncestorService;

    @Autowired
    private FanNewsFamousAncestorMapper fanNewsFamousAncestorMapper;

    /**
     *联谊会联谊会祖先查询
     *@Author: yuzhou
     *@Date: 2018-11-21
     *@Time: 10:36
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<FanNewsFamousAncestor> getFamousAncestorPage(Wrapper<FanNewsFamousAncestor> entity, Integer pageNo, Integer pageSize) {
        Page<FanNewsFamousAncestor> fanNewsFamousAncestorPage = this.selectPage(new Page<FanNewsFamousAncestor>(pageNo, pageSize), entity);
        return fanNewsFamousAncestorPage;
    }

    /**
     *联谊会祖先人物详情查询
     *@Author: yuzhou
     *@Date: 2018-11-21
     *@Time: 10:36
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public AncestorsBranchVo getFamousAncestorDetails(Integer id) {
        //创建AncestorsBranchVo返回的实体类
        AncestorsBranchVo ancestorsBranchVo=new AncestorsBranchVo();
        //根据主键查询
        FanNewsFamousAncestor fanNewsFamousAncestor = this.selectById(id);
        //根据父Id查询分支后裔
        Wrapper<FanNewsFamousAncestor> entity=new EntityWrapper<>();
        entity.eq("parent_id",id);
        entity.orderBy("update_time", false);
        List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
        //封装到要返回的实体类中
        BeanUtils.copyProperties(fanNewsFamousAncestor,ancestorsBranchVo);
        if(fanNewsFamousAncestors.size()!=0){
            ancestorsBranchVo.setFanNewsFamousAncestorList(fanNewsFamousAncestors);
        }
        return ancestorsBranchVo;
    }

    /**
     *联谊会祖先后台添加模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 15:58
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public Page<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map) {
        List<AncestorsBranchVo> famousAncestorVaguePage = fanNewsFamousAncestorMapper.getFamousAncestorVaguePage(mapPage, map);
        if(famousAncestorVaguePage.size()==0){
            return null;
        }
        mapPage.setRecords(famousAncestorVaguePage);
        return mapPage;
    }

    /**
     * 联谊会添加 修改
     * @param fanNewsFamousAncestor
     * @param proSplit
     * @param fanSplit
     */
    @Override
    public Boolean addFamousAncestor(FanNewsFamousAncestor fanNewsFamousAncestor, List<String> proSplit, List<String> fanSplit) {
        //父iD第一级为0
        fanNewsFamousAncestor.setParentId(0);
        if(fanNewsFamousAncestor.getId()!=null){
            //根据父ID查询人物对象集合
            Wrapper<FanNewsFamousAncestor> entity=new EntityWrapper<FanNewsFamousAncestor>();
            entity.eq("parent_id",fanNewsFamousAncestor.getId());
            List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
            //判断是否有分支
            if (fanNewsFamousAncestors.size()!=0){
                //获取祖先分支id集合
                List<Integer> list=new ArrayList<>();
                for (FanNewsFamousAncestor fanNewsFamous: fanNewsFamousAncestors) {
                    list.add(fanNewsFamous.getId());
                }
                //删除祖先分支
                this.deleteBatchIds(list);
            }
            //修改时间
            fanNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
        }else{
            //创建时间
            fanNewsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
            //修改时间
            fanNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
        }
        //插入主数据
        boolean insert = this.insertOrUpdate(fanNewsFamousAncestor);
        //查询主键
        Wrapper<FanNewsFamousAncestor> entity=new EntityWrapper();
        entity.eq("show_id",fanNewsFamousAncestor.getShowId());
        FanNewsFamousAncestor fanNews = this.selectOne(entity);


        //省级数据list集合
        List<ProNewsFamousAncestor> proNewsFamousAncestors=null;

        //县级数据list集合
        List<FanNewsFamousAncestor> fanNewsFamousAncestors=null;

        //判断是否有县级数据
        if(fanSplit.size()!=0){
            //查询县级
            fanNewsFamousAncestors = this.selectBatchIds(fanSplit);
            for (FanNewsFamousAncestor newsFamousAncestor : fanNewsFamousAncestors) {
                newsFamousAncestor.setShowId(-1);
                newsFamousAncestor.setParentId(fanNews.getId());
                newsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
                newsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            }
            if(fanNewsFamousAncestors.size()!=0){
                //批量插入
                this.insertBatch(fanNewsFamousAncestors);
            }
        }

        //判断是否有省级数据
        if(proSplit.size()!=0){
            //查询省级
            proNewsFamousAncestors = proNewsFamousAncestorService.selectBatchIds(proSplit);
            for (ProNewsFamousAncestor proNewsFamousAncestor : proNewsFamousAncestors) {
                FanNewsFamousAncestor famousAncestor=new FanNewsFamousAncestor();
                BeanUtils.copyProperties(proNewsFamousAncestor,famousAncestor);
                fanNewsFamousAncestors.add(famousAncestor);
            }
            //修改父Id
            for (FanNewsFamousAncestor newsFamousAncestor : fanNewsFamousAncestors) {
                newsFamousAncestor.setShowId(-1);
                newsFamousAncestor.setParentId(fanNews.getId());
            }
            if(proNewsFamousAncestors.size()!=0){
                //批量插入
                this.insertBatch(fanNewsFamousAncestors);
            }

        }
        return insert;
    }

    /**
     * 联谊会删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteFamousAncestor(Integer id) {
        //根据父ID查询人物对象集合
        Wrapper<FanNewsFamousAncestor> entity=new EntityWrapper();
        entity.eq("parent_id",id);
        List<FanNewsFamousAncestor> fanNewsFamousAncestors = this.selectList(entity);
        //判断是否有分支
        if (fanNewsFamousAncestors.size()!=0){
            //获取祖先分支id集合
            List<Integer> list=new ArrayList<>();
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
