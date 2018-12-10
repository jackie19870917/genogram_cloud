package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousAncestor;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.AncestorsBranchVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-祖先分支 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface FanNewsFamousAncestorMapper extends BaseMapper<FanNewsFamousAncestor> {

    /**
     * 联谊会祖先后台添加模糊查询
     *
     * @param mapPage
     * @param map
     * @return
     */
    List<AncestorsBranchVo> getFamousAncestorVaguePage(Page<AncestorsBranchVo> mapPage, Map map);
}
