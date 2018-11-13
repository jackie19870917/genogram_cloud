package com.genogram.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.FanSysWebNewsShow;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FanSysWebMenuVo;
import java.util.List;

/**
 * <p>
 * 联谊网站文章挂靠位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanSysWebNewsShowService extends IService<FanSysWebNewsShow> {
   
    /**
     * getMenu
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @return:
     * @Description:
     *
     */
    public List<FanSysWebMenuVo> getMenu(String hostIp,String siteId,boolean isWeb,EntityWrapper<FanSysWebNewsShow> entityWrapper);

    /**
     * getMenu
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @return:
     * @Description:
     *
     */
    public List<FanSysWebMenuVo> getIndexMenu(String siteId);



    public List<FanSysWebMenuVo> getTitlesByMenuId(String hostIp,int siteId, int menuId);
}
