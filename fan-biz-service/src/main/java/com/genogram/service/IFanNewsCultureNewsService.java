package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCultureNews;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.unit.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 联谊会-家族文化文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCultureNewsService extends IService<FanNewsCultureNews> {

    /**
     * 联谊会家族文化查询
     *
     * @param entity   查询条件
     * @param pageNo   当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FamilyCultureVo> getFamilyCulturePage(Wrapper<FanNewsCultureNews> entity, Integer pageNo, Integer pageSize);

    /**
     * 联谊会家族文化详情查询
     *
     * @param id 文章ID
     * @return
     */
    NewsDetailVo getFamilyCultureDetail(Integer id);

    /**
     * 家族文化后台添加
     *
     * @param fanNewsCultureNews 家族文化上传实体类
     * @param fileName           上传的图片的字符串
     * @param filePath           上传的图片的路径
     * @return
     */
    boolean addOrUpdateCulture(FanNewsCultureNews fanNewsCultureNews, String fileName, String filePath);

    /**
     * 联谊会家族文化后台删除
     *
     * @param id        主键
     * @param status    状态(0:删除;1:已发布;2:草稿3:不显示)
     * @param userLogin
     * @return
     */
    Boolean deleteCulturById(Integer id, int status, AllUserLogin userLogin);

    /**
     * 联谊会家族文化增加查看数
     *
     * @param id
     */
    void addVisitNum(Integer id);

    /**
     * 联谊会首页家族文化文章查询
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FamilyCultureVo> getFamilyIndexCulturePage(Integer siteId, Integer pageNo, Integer pageSize);

    /**
     * 联谊会家族文化后台置顶
     *
     * @param id
     * @param userLogin
     * @return
     */
    Boolean culturStick(Integer id, AllUserLogin userLogin);
}
