package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsIndustry;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 联谊会-家族产业 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsIndustryService extends IService<FanNewsIndustry> {

    /**
     *联谊会家族产业查询
     * @param entity 查询条件
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FamilyIndustryVo> getFamilyIndustryPage(Wrapper<FanNewsIndustry> entity, Integer pageNo, Integer pageSize);

    /**
     * 联谊会家族产业各个产业的详情
     * @param id  主键
     * @return
     */
    IndustryDetailVo getFamilyIndustryDetail(Integer id);

    /**
     * 联谊会家族产业后台新增
     * @param fanNewsIndustry 家族产业上传实体类
     * @param fileName 上传的图片名称
     * @param filePath 上传的图片路径
     * @return
     */
    boolean addOrUpdateIndustry(FanNewsIndustry fanNewsIndustry, String fileName,String filePath);

    /**
     *联谊会家族产业后台删除
     * @param id
     * @param status
     * @return
     */
    Boolean deleteIndustryById(Integer id, int status);

    /**
     * 联谊会家族产业前台增加查看数
     * @param id  主键
     */
    void addVisitNum(Integer id);
}
