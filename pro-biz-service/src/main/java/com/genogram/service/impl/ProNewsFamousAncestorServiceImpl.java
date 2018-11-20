package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.mapper.ProNewsFamousAncestorMapper;
import com.genogram.service.IProNewsFamousAncestorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        ancestorsBranchVo.setProNewsFamousAncestorList(proNewsFamousAncestorList);
        return ancestorsBranchVo;
    }

    @Override
    public AncestorsBranchVo getFamousAncestorVaguePage(String ancestorName) {
        return null;
    }
}
