package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousPerson;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entityvo.*;

/**
 * <p>
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsFamousPersonService extends IService<FanNewsFamousPerson> {
    /**
     * 前台查询
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanNewsFamousPerson> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 后台查询
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyPersonVo> getFamilyPersionPages(Wrapper<FanNewsFamousPerson> entity, Integer pageNo, Integer pageSize);
    /**
     * 联谊会家族名人的详情
     * @param id  主键
     * @return
     */
    FamilyPersonVo getFamilyPersionDetail(Integer id);

    /**
     * 联谊会家族名人后台新增
     * @param fanNewsFamousPerson  家族名人上传实体类
     * @param fileName 文件列表
     * @param filePath 文件路径
     * @return
     */
    boolean addOrUpdatePersion(FanNewsFamousPerson fanNewsFamousPerson,String fileName, String filePath);

    /**
     *删除
     * @param id
     * @param status
     * @return
     */
    Boolean deletePersionById(Integer id, int status);
    /**
     * 联谊会家族名人增加查看数
     * @param id
     */
    void addVisitNum(Integer id);
    /**
     *联谊会家族名人详情查询
     * @param id  文章ID
     * @return
     */
    FamilyPersonVo getFamilyFamilyDetail(Integer id);

    /**
     * 组织架构
     * @param showId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanNewsFamousPerson> getFamilyFramePage(Integer showId, Integer status, Integer pageNo, Integer pageSize);
    /**
     * 组织架构
     * @param showId
     * @return
     */
    FanNewsFamousPerson getFamilyFrameList(Integer showId);
}
