package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCharityOut;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;

/**
 * <p>
 * 省级-家族慈善财务收入支出(文章)表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsCharityOutService extends IService<ProNewsCharityOut> {

    /**
     *                慈善支出(用途)
     * @param showId   慈善支出展示位置
     * @param newsType   类型(1-慈善支出,2-慈善收益)
     * @param status     状态
     * @param pageNo     当前页
     * @param pageSize   每页记录数
     * @return
     */
    Page<ProNewsCharityOut> getProNewsCharityOutPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize);

    /**
     *                慈善支出(文章)
     * @param entity     实体类
     * @param pageNo     当前页
     * @param pageSize   每页记录数
     * @return
     */
    Page<NewsCharityOutVo> getNewsCharityOutVoPage(Wrapper<ProNewsCharityOut> entity, Integer pageNo, Integer pageSize);
    /**
     *            慈善收支文章详情
     * @param id        文章iD
     * @return
     */
    NewsDetailVo getNewsCharityOutDetail(Integer id);

    /**
     *     新增/修改  慈善收支(文章)
     * @param proNewsCharityOut   慈善收支
     * @param fileName
     * @param filePath
     * @return
     */
    Boolean insertOrUpdateProNewsCharityOutVo(ProNewsCharityOut proNewsCharityOut,String fileName,String filePath);

    /**
     * 逻辑删除  慈善收支(文章)
     * @param proNewsCharityOut
     * @return
     */
    Boolean deleteProNewsCharityOut(ProNewsCharityOut proNewsCharityOut);
}
