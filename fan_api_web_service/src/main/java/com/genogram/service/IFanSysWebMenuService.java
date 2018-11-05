package com.genogram.service;

import com.genogram.entity.FanSysWebMenu;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FanSysWebMenuVo;

/**
 * <p>
 * 联谊会web菜单基础表,非文章位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanSysWebMenuService extends IService<FanSysWebMenu> {
    public FanSysWebMenuVo showFanWebMenu(int siteId);
}
