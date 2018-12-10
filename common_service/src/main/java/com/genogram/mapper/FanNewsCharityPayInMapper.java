package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityPayIn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.DonorVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊网-慈善公益-捐款人 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface FanNewsCharityPayInMapper extends BaseMapper<FanNewsCharityPayIn> {

    /**
     * 捐款名录
     *
     * @param mapPage 分页
     * @param map     查询条件
     * @return
     */
    List<FanNewsCharityPayIn> getDonorVoPage(Page<FanNewsCharityPayIn> mapPage, Map map);
}
