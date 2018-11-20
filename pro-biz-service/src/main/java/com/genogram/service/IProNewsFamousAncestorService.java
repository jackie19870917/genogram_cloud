package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsFamousAncestor;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.AncestorsBranchVo;

/**
 * <p>
 * 省级-祖先分支 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsFamousAncestorService extends IService<ProNewsFamousAncestor> {

    /**
     * 省级祖先分支查询
     * @param entity 查询条件
     * @param pageNo 当前页
     * @param pageSize 每页显示的条数
     * @return
     */
    Page<ProNewsFamousAncestor> getFamousAncestorPage(Wrapper<ProNewsFamousAncestor> entity, Integer pageNo, Integer pageSize);

    /**
     * 省级祖先分支详情
     * @param id
     * @return
     */
    AncestorsBranchVo getFamousAncestorDetails(Integer id);

    /**
     * 省级
     * @param ancestorName
     * @return
     */
    AncestorsBranchVo getFamousAncestorVaguePage(String ancestorName);
}
