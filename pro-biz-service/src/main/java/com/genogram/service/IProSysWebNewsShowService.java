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
     *
     * @param siteId
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public List<SysWebMenuVo> getIndexMenu(String siteId);


    /**
     * 读取栏目
     *
     * @param siteId
     * @param menuId
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public List<SysWebMenuVo> getTitlesByMenuId(int siteId, int menuId);

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

    /**
     * 修改菜单
     *
     * @param id
     * @param menuName
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public void updateTitlesById(int id, String menuName);

    /**
     * 删除菜单
     *
     * @param id
     * @param id
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public String delTitlesById(int id);

    /**
     * 删除菜单
     *
     * @param siteId
     * @param menuName
     * @param parentId
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public void addTitles(int siteId, String menuName, int parentId);

    /**
     * 通过siteId menuCode 找到菜单对象
     *
     * @param siteId
     * @param menuCode
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @return:
     * @Description:
     */
    public ProSysWebNewsShow getSysWebNewsShowBySiteIdAndMenuCode(int siteId, String menuCode);
}
