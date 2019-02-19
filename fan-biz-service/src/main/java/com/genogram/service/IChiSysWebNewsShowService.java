package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ChiSysWebNewsShow;

/**
 * <p>
 * 省级web菜单基础表,文章位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
public interface IChiSysWebNewsShowService extends IService<ChiSysWebNewsShow> {

    /**
     * 开网站初始化菜单
     *
     * @param siteId
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public void initWebMenu(int siteId);
}
