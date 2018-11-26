package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousAncestor;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-祖先分支 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsFamousAncestorService extends IService<FanNewsFamousAncestor> {


    /**
     * 联谊会联谊会祖先查询
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanNewsFamousAncestor> getFamousAncestorPage(Integer siteId, Integer pageNo, Integer pageSize);

    /**
     * 联谊会祖先人物详情查询
     * @param id
     * @return
     */
    AncestorsBranchVo getFamousAncestorDetails(Integer id);

    /**
     * 联谊会祖先后台添加模糊查询
     * @param map
     * @param mapPage
     * @return
     */
    Page<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map);

    /**
     * 联谊会添加
     * @param fanNewsFamousAncestor
     * @param proSplit
     * @param fanSplit
     * @return
     */
    Boolean addFamousAncestor(FanNewsFamousAncestor fanNewsFamousAncestor, List<String> proSplit, List<String> fanSplit);

    /**
     * 联谊会删除
     * @param id
     * @return
     */
    Boolean deleteFamousAncestor(Integer id);
}
