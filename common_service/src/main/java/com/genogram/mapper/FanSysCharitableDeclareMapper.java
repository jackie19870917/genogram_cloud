package com.genogram.mapper;

import com.genogram.entity.FanSysCharitableDeclare;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
public interface FanSysCharitableDeclareMapper extends BaseMapper<FanSysCharitableDeclare> {

    /**
     * 慈善帮扶总金额
     * @param map
     * @return
     */
    BigDecimal familyStructureMoney(Map map);
}
