package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;

import java.util.List;

/**
 * <p>
 * 联谊网站文章挂靠位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IProSysWebNewsShowService extends IService<ProSysWebNewsShow> {

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
