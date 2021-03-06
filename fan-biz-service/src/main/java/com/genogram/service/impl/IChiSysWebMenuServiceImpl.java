package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.ChiSysWebMenu;
import com.genogram.mapper.ChiSysWebMenuMapper;
import com.genogram.service.ChiSysWebMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级web菜单基础表,仅做初始化的时候用 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class IChiSysWebMenuServiceImpl extends ServiceImpl<ChiSysWebMenuMapper, ChiSysWebMenu> implements ChiSysWebMenuService {

}
