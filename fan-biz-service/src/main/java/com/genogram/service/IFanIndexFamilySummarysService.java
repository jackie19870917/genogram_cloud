package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.baomidou.mybatisplus.service.IService;

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
     * 联谊堂信息
     *
     * @param siteId   网站ID
     * @param list     状态
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    Page<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(Integer siteId, List list, Integer pageNo, Integer pageSize);

    /**
     * /新增/修改联谊堂信息
     *
     * @param fanIndexFamilySummarys
     * @return
     */
    Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys);

    /**
     * 单一查询联谊堂信息
     *
     * @param id 主键
     * @return
     */
    FanIndexFamilySummarys getFanIndexFamilySummarys(Integer id);

    /**
     * 逻辑删除
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteFanIndexFamilySummarys(Integer id, Integer userId);
}
