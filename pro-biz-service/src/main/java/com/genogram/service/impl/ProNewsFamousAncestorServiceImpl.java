package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.mapper.ProNewsFamousAncestorMapper;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.genogram.service.IProNewsFamousAncestorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-祖先分支 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsFamousAncestorServiceImpl extends ServiceImpl<ProNewsFamousAncestorMapper, ProNewsFamousAncestor> implements IProNewsFamousAncestorService {

    @Autowired
    private IProNewsFamousAncestorService proNewsFamousAncestorService;

    @Autowired
    private ProNewsFamousAncestorMapper proNewsFamousAncestorMapper;

    @Autowired
    private IFanNewsFamousAncestorService fanNewsFamousAncestorService;

    /**
     *省级祖先分支查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 14:02
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<ProNewsFamousAncestor> getFamousAncestorPage( Wrapper<ProNewsFamousAncestor> entity, Integer pageNo, Integer pageSize) {
        Page<ProNewsFamousAncestor> proNewsFamousAncestorPage = proNewsFamousAncestorService.selectPage(new Page<ProNewsFamousAncestor>(pageNo, pageSize), entity);
        return proNewsFamousAncestorPage;
    }

    /**
     *省级祖先分支详情
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 14:26
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public AncestorsBranchVo getFamousAncestorDetails(Integer id) {
        //创建AncestorsBranchVo返回的实体类
        AncestorsBranchVo ancestorsBranchVo=new AncestorsBranchVo();
        //根据主键查询
        ProNewsFamousAncestor proNewsFamousAncestor = proNewsFamousAncestorService.selectById(id);
        //根据父Id查询分支后裔
        Wrapper<ProNewsFamousAncestor> entity=new EntityWrapper<>();
        entity.eq("parent_id",id);
        entity.orderBy("update_time", false);
        List<ProNewsFamousAncestor> proNewsFamousAncestorList = proNewsFamousAncestorService.selectList(entity);
        //封装到要返回的实体类中
        BeanUtils.copyProperties(proNewsFamousAncestor,ancestorsBranchVo);
        if(proNewsFamousAncestorList.size()!=0){
            ancestorsBranchVo.setProNewsFamousAncestorList(proNewsFamousAncestorList);
        }
        return ancestorsBranchVo;
    }

    /**
     *省级祖先后台添加模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 15:58
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map) {
        List<AncestorsBranchVo> famousAncestorVaguePage = proNewsFamousAncestorMapper.getFamousAncestorVaguePage(mapPage, map);
        if(famousAncestorVaguePage.size()==0){
            return null;
        }
        mapPage.setRecords(famousAncestorVaguePage);
        return mapPage;
    }

    /**
     * 省级添加
     * @param proNewsFamousAncestor
     * @param proSplit
     * @param fanSplit
     */
    @Override
    public Boolean addFamousAncestor(ProNewsFamousAncestor proNewsFamousAncestor, List<String> proSplit, List<String> fanSplit) {
        //父iD第一级为0
        proNewsFamousAncestor.setParentId(0);
        if(proNewsFamousAncestor.getId()!=null){
            //根据父ID查询人物对象集合
            Wrapper<ProNewsFamousAncestor> entity=new EntityWrapper<ProNewsFamousAncestor>();
            entity.eq("parent_id",proNewsFamousAncestor.getId());
            List<ProNewsFamousAncestor> proNewsFamousAncestors = proNewsFamousAncestorService.selectList(entity);
            //判断是否有分支
            if (proNewsFamousAncestors.size()!=0){
                //获取祖先分支id集合
                List<Integer> list=new ArrayList<>();
                for (ProNewsFamousAncestor proNewsFamous: proNewsFamousAncestors) {
                    list.add(proNewsFamous.getId());
                }
                //删除祖先分支
                proNewsFamousAncestorService.deleteBatchIds(list);
            }
            //修改时间
            proNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
        }else{
            //创建时间
            proNewsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
            //修改时间
            proNewsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
        }
        //插入主数据
        boolean insert = proNewsFamousAncestorService.insertOrUpdate(proNewsFamousAncestor);
        //查询主键
        Wrapper<ProNewsFamousAncestor> entity=new EntityWrapper();
        entity.eq("show_id",proNewsFamousAncestor.getShowId());
        ProNewsFamousAncestor proNews = proNewsFamousAncestorService.selectOne(entity);

        //省级数据list集合
        List<ProNewsFamousAncestor> proNewsFamousAncestors=null;

        //县级数据list集合
        List<FanNewsFamousAncestor> fanNewsFamousAncestors=null;
        //判断是否有省级数据
        if(proSplit!=null){
            //查询省级
             proNewsFamousAncestors = proNewsFamousAncestorService.selectBatchIds(proSplit);
            //修改父Id
            for (ProNewsFamousAncestor newsFamousAncestor : proNewsFamousAncestors) {
                newsFamousAncestor.setShowId(-1);
                newsFamousAncestor.setParentId(proNews.getId());
            }
            if(proNewsFamousAncestors.size()!=0){
                //批量插入
                proNewsFamousAncestorService.insertBatch(proNewsFamousAncestors);
            }

        }

        //判断是否有县级数据
        if(fanSplit!=null){
            //查询县级
            fanNewsFamousAncestors = fanNewsFamousAncestorService.selectBatchIds(fanSplit);
            for (FanNewsFamousAncestor fanNewsFamousAncestor : fanNewsFamousAncestors) {
                ProNewsFamousAncestor famousAncestor=new ProNewsFamousAncestor();
                BeanUtils.copyProperties(fanNewsFamousAncestor,famousAncestor);
                proNewsFamousAncestors.add(famousAncestor);
            }
            for (ProNewsFamousAncestor newsFamousAncestor : proNewsFamousAncestors) {
                newsFamousAncestor.setShowId(-1);
                newsFamousAncestor.setParentId(proNews.getId());
                newsFamousAncestor.setCreateTime(DateUtil.getCurrentTimeStamp());
                newsFamousAncestor.setUpdateTime(DateUtil.getCurrentTimeStamp());
            }
            if(fanNewsFamousAncestors.size()!=0){
                //批量插入
                proNewsFamousAncestorService.insertBatch(proNewsFamousAncestors);
            }
        }
         return insert;
    }

    /**
     * 省级删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteFamousAncestor(Integer id) {
        //根据父ID查询人物对象集合
        Wrapper<ProNewsFamousAncestor> entity=new EntityWrapper();
        entity.eq("parent_id",id);
        List<ProNewsFamousAncestor> proNewsFamousAncestors = proNewsFamousAncestorService.selectList(entity);
        //判断是否有分支
        if (proNewsFamousAncestors.size()!=0){
            //获取祖先分支id集合
            List<Integer> list=new ArrayList<>();
            for (ProNewsFamousAncestor proNewsFamousAncestor : proNewsFamousAncestors) {
                list.add(proNewsFamousAncestor.getId());
            }
            //删除祖先分支
            proNewsFamousAncestorService.deleteBatchIds(list);
        }
        //删除主表
        Boolean aBoolean = proNewsFamousAncestorService.deleteById(id);
        return aBoolean;
    }

}
