package com.genogram.service;

import com.genogram.entity.ProSysWebNewsShow;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.SysWebMenuVo;
import java.util.List;

/**
 * <p>
 * 省级网站文章挂靠位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProSysWebNewsShowService extends IService<ProSysWebNewsShow> {
    /**
     * 静态菜单
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @return:
     * @Description:
     *
     */
    public List<SysWebMenuVo> getIndexMenu(String siteId);


    /**
     * 读取栏目
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @param menuId
     * @return:
     * @Description:
     *
     */
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId);

    /**
     * 开网站初始化菜单
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @return:
     * @Description:
     *
     */
    public void initWebMenu(int siteId);

    /**
     * 修改菜单
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param id
     * @param menuName
     * @return:
     * @Description:
     *
     */
    public void updateTitlesById(int id,String menuName);

    /**
     * 删除菜单
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param id
     * @param id
     * @return:
     * @Description:
     *
     */
    public String delTitlesById(int id);

    /**
     * 删除菜单
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @param menuName
     * @param parentId
     * @return:
     * @Description:
     *
     */
    public void addTitles(int siteId, String menuName,int parentId);
}
