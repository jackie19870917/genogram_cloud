package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会文章推荐表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IProSysRecommendService extends IService<FanSysRecommend> {


    /**
     * 省级后台点击推荐
     *
     * @param fanSysRecommend 推荐实体类
     * @return
     */
    Boolean addRecommend(FanSysRecommend fanSysRecommend);

    /**
     * 省级后台点击取消
     *
     * @param entity
     * @param status
     * @return
     */
    Boolean deleteRecommend(Wrapper<FanSysRecommend> entity, int status);

    /**
     * 省级首页县级推荐文章查询
     *
     * @param map 查询条件
     * @return
     */
    List<IndustryDetailVo> getRecommendArticle(Map map);

    /**
     * 省级首页县级推荐人物查询
     *
     * @param map
     * @return
     */
    List<FamilyPersonVo> getRecommendFigure(Map map);

    /**
     * 省级首页文章推荐详情查询
     *
     * @param id     主键
     * @param source 分类 1代表家族文化 2 代表记录家族 3代表家族名人
     * @return
     */
    Object getRecommendParticulars(Integer id, Integer source);

    /**
     * 省级首页名人推荐详情查询
     *
     * @param id
     * @return
     */
    FamilyPersonVo getRecommendFigureParticulars(Integer id);

    /**
     * 省级手动文章查询
     *
     * @param map
     * @return
     */
    List<CommonRecommendVo> getManualRecommend(Map map);


    /**
     * 省级后台设置手动推荐到全国
     *
     * @param map
     * @return
     */
    List<CommonRecommendVo> getManuaRecommendNationwide(Map map);
}
