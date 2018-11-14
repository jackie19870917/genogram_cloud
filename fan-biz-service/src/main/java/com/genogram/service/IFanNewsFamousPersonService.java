package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamousPerson;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.IndustryDetailVo;

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
    Page<FamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 后台查询
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyPersonVo> getFamilyPersionPages(Wrapper<FanNewsFamousPerson> entity, Integer pageNo, Integer pageSize);
    /**
     * 联谊会家族产业各个产业的详情
     * @param id  主键
     * @return
     */
    FamilyPersonVo getFamilyPersionDetail(Integer id);

    /**
     * 联谊会家族产业后台新增
     * @param fanNewsFamousPerson  家族名人上传实体类
     * @param fileNames  上传的图片的字符串
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
}
