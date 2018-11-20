package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityPayIn;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.DonorVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊网-慈善公益-捐款人 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCharityPayInService extends IService<FanNewsCharityPayIn> {

    /**
     *      捐款名录(个人捐款金额)
     * @param mapPage  分页
     * @param map   查询条件
     * @return
     */
    Page<DonorVo> getDonorVoPage(Page<FanNewsCharityPayIn> mapPage, Map map);
    /**
     *          捐款名录(最新时间)
     * @param showId     展示位置
     * @param status     状态
     * @param pageNo     当前页
     * @param pageSize   每页记录数
     * @return
     */
    Page<DonorVo> getDonorVoPageByTime(Integer showId, List status, Integer pageNo, Integer pageSize);

    /**
     *  新增捐款名录
     * @param fanNewsCharityPayIn 实体类
     * @return
     */
    Boolean insertFanNewsCharityPayIn(FanNewsCharityPayIn fanNewsCharityPayIn);

    /**
     * 单一查询
     * @param fanNewsCharityPayIn
     * @return
     */
    FanNewsCharityPayIn selectOne(FanNewsCharityPayIn fanNewsCharityPayIn);

}