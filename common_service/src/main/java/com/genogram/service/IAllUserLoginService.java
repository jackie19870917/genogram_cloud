package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllFamily;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;

import java.util.List;

/**
 * <p>
 * 用户登录表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IAllUserLoginService extends IService<AllUserLogin> {

    /**
     * 登陆
     *
     * @param allUserLogin
     * @return
     */
    AllUserLogin getAllUserLogin(AllUserLogin allUserLogin);

    /**
     * 注册
     *
     * @param allUserLogin
     * @return
     */
    AllUserLogin insertAllUserLogin(AllUserLogin allUserLogin);

    /**
     * 查询
     *
     * @param id 主键
     * @return
     */
    AllUserLogin getAllUserLoginById(Integer id);

    /**
     * 修改密码
     *
     * @param allUserLogin
     * @return
     */
    Boolean updateAllUserLogin(AllUserLogin allUserLogin);

    /**
     * 修改个人资料
     *
     * @param allUserLogin
     * @return
     */
    Boolean updateUserLogin(AllUserLogin allUserLogin);

    /**
     * 查询用户
     *
     * @param wrapper
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserLogin> getAllUserLoginPage(Wrapper<AllUserLogin> wrapper, Integer pageNo, Integer pageSize);

    /**
     * 联谊会网站
     *
     * @param wrapper
     * @return
     */
    List<FanSysSite> getFanSysSite(Wrapper<FanSysSite> wrapper);

    /**
     * 省级网站
     *
     * @param wrapper
     * @return
     */
    List<ProSysSite> getProSysSite(Wrapper<ProSysSite> wrapper);

    /**
     * 查询姓氏
     *
     * @param wrapper
     * @return
     */
    List<AllFamily> getAllFamily(Wrapper<AllFamily> wrapper);

    /**
     * 联谊会网站
     *
     * @param wrapper
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<FanSysSite> getFanSysSitePage(Wrapper<FanSysSite> wrapper, Integer pageNo, Integer pageSize);

    /**
     * 省级网站
     *
     * @param wrapper
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ProSysSite> getProSysSitePage(Wrapper<ProSysSite> wrapper, Integer pageNo, Integer pageSize);

    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
    AllUserLogin getAllUserLoginByOpenId(String openId);
}
