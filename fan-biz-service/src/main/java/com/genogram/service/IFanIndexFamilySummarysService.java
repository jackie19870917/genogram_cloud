package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FanIndexFamilySummarysVo;

import java.util.List;

/**
 * <p>
 * 联谊会首页-联谊堂 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexFamilySummarysService extends IService<FanIndexFamilySummarys> {

    /**
     *     联谊堂信息
     * @param siteId   网站ID
     * @param status   状态
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    Page<FanIndexFamilySummarysVo> getFanIndexFamilySummarysPage(Integer siteId, List status, Integer pageNo, Integer pageSize);

    /**
     *  /新增/修改联谊堂信息
     * @param fanIndexFamilySummarysVo
     * @return
     */
    Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarysVo fanIndexFamilySummarysVo);

    /**
     * 单一查询联谊堂信息
     * @param id   主键
     * @return
     */
    FanIndexFamilySummarysVo getFanIndexFamilySummarys(Integer id);
}
