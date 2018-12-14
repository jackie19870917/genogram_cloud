package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.ProFamilyPersonVo;

/**
 * @author Administrator
 */
public interface IProNewsFamilyPersionService extends IService<ProNewsFamousPerson> {
    /**
     * 前后台查询
     *
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProFamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 省级家族名人增加查看数
     *
     * @param id
     */
    void addVisitNum(Integer id);

    /**
     * 省级家族名人详情查询
     *
     * @param id 文章ID
     * @return
     */
    ProFamilyPersonVo getFamilyFamilyDetail(Integer id);

    /**
     * 省级家族名人的详情
     *
     * @param id 主键Pro
     * @return
     */
    ProFamilyPersonVo getFamilyPersionDetail(Integer id);

    /**
     * 后台查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProFamilyPersonVo> getFamilyPersionPages(Wrapper<ProNewsFamousPerson> entity, Integer pageNo, Integer pageSize);

    /**
     * 联谊会家族名人后台新增
     *
     * @param proNewsFamousPerson 家族名人上传实体类
     * @param fileName            文件列表
     * @param filePath            文件路径
     * @return
     */
    boolean addOrUpdatePersion(ProNewsFamousPerson proNewsFamousPerson, String fileName, String filePath);

    /**
     * 删除
     *
     * @param id
     * @param status
     * @param userId
     * @return
     */
    Boolean deletePersonById(Integer id, int status,Integer userId);

    /**
     * 通过siteId menuCode 找到菜单对象
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:04
     * @param siteId
     * @param menuCode
     * @return:
     * @Description:
     *
     */
    //public ProNewsFamousPerson getSysWebNewsShowBySiteIdAndMenuCode(int siteId, String menuCode);

    /**
     * 组织架构
     *
     * @param showId
     * @return
     */
    ProNewsFamousPerson getFamilyFrameList(Integer showId);
}
