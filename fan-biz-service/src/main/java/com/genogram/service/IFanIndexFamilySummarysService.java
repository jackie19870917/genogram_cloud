package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 联谊会首页-联谊堂 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexFamilySummarysService extends IService<FanIndexFamilySummarys> {

    //联谊堂
    Page<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(Integer siteId,Integer status,Integer pageNo,Integer pageSize);

    //新增/修改联谊堂信息
    Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys);

    //单一查询联谊堂
    FanIndexFamilySummarys getFanIndexFamilySummarys(Integer id);
}
