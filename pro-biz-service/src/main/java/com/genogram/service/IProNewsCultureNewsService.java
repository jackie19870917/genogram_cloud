package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCultureNews;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;

/**
 * <p>
 * 省级-家族文化文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsCultureNewsService extends IService<ProNewsCultureNews> {

    /**
     * 省级家族文化查询
     * @param entity  查询条件
     * @param pageNo  当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FamilyCultureVo> getFamilyCulturePage(Wrapper<ProNewsCultureNews> entity, Integer pageNo, Integer pageSize);

    /**
     * 省级家族文化详情查询
     * @param id 主键
     * @return
     */
    NewsDetailVo getFamilyCultureDetail(Integer id);

    /**
     * 省级家族文化文章增加查看数
     * @param id  主键
     */
    void addVisitNum(Integer id);

    /**
     * 省级家族文化新增 修改
     * @param proNewsCultureNews
     * @param fileName
     * @param filePath
     * @return
     */
    boolean addOrUpdateCulture(ProNewsCultureNews proNewsCultureNews, String fileName, String filePath);

    /**
     * 省级家族文化后台删除
     * @param id
     * @param status
     * @return
     */
    Boolean deleteCulturById(Integer id, int status);
}
