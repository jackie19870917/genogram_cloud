package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.entity.ProNewsFamousAncestor;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.AncestorsBranchVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-祖先分支 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface ProNewsFamousAncestorMapper extends BaseMapper<ProNewsFamousAncestor> {

    /**
     * 省级祖先后台添加模糊查询
     *
     * @param mapPage
     * @param map
     * @return
     */
    List<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map);
}
