package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCharityPayIn;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-慈善公益-捐款人 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface ProNewsCharityPayInMapper extends BaseMapper<ProNewsCharityPayIn> {

    /**
     * 捐款名录
     *
     * @param mapPage 分页
     * @param map     查询条件
     * @return
     */
    List<ProNewsCharityPayIn> getDonorVoPage(Page<ProNewsCharityPayIn> mapPage, Map map);
}
