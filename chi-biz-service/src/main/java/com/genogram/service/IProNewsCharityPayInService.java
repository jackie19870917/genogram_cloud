package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCharityPayIn;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.DonorVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-慈善公益-捐款人 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsCharityPayInService extends IService<ProNewsCharityPayIn> {

    /**
     * 捐款名录(个人捐款金额)
     *
     * @param mapPage 分页
     * @param map     查询条件
     * @return
     */
    Page<DonorVo> getDonorVoPage(Page<ProNewsCharityPayIn> mapPage, Map map);

    /**
     * 捐款名录(最新时间)
     *
     * @param showId   展示位置
     * @param status   状态
     * @param nickName 昵称
     * @param order    排序
     * @param label    升序,降序
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    Page<DonorVo> getDonorVoPageByTime(Integer showId, List status, String nickName, Integer pageNo, Integer pageSize, String order, String label);

    /**
     * 新增捐款名录
     *
     * @param proNewsCharityPayIn 实体类
     * @return
     */
    Boolean insertProNewsCharityPayIn(ProNewsCharityPayIn proNewsCharityPayIn);

    /**
     * 单一查询
     *
     * @param proNewsCharityPayIn
     * @return
     */
    ProNewsCharityPayIn selectOne(ProNewsCharityPayIn proNewsCharityPayIn);
}
