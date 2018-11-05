package com.genogram.service.impl;

import com.genogram.entity.FanSysWebMenu;
import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.mapper.FanSysWebMenuMapper;
import com.genogram.service.IFanSysWebMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联谊会web菜单基础表,非文章位置表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanSysWebMenuServiceImpl extends ServiceImpl<FanSysWebMenuMapper, FanSysWebMenu> implements IFanSysWebMenuService {

    @Override
    public FanSysWebMenuVo showFanWebMenu(int siteId) {
        return null;
    }
}
