package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 姓氏表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-04
 */
public interface AllFamilyMapper extends BaseMapper<AllFamily> {
    
    /**
     * queryMyItems
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:05
     * @param mapPage
     * @param requestParam
     * @return:
     * @Description:
     *
     */
    List<AllFamily> queryMyItems(Page<AllFamily> mapPage, Map requestParam);
}
