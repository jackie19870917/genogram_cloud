package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;

import java.util.List;

/**
 * <p>
 * 联谊会-家族慈善财务支出表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCharityOutService extends IService<FanNewsCharityOut> {

    /**
     *                慈善支出(用途)
     * @param showId   慈善支出展示位置
     * @param newsType   类型(1-慈善支出,2-慈善收益)
     * @param status     状态
     * @param pageNo     当前页
     * @param pageSize   每页记录数
     * @return
     */
    Page<FanNewsCharityOut> getFanNewsCharityOutPage(Integer showId, Integer newsType, Integer status, Integer pageNo, Integer pageSize);

    /**
     *                慈善支出(文章)
     * @param showId   慈善支出展示位置
     * @param newsType   类型(1-慈善支出,2-慈善收益)
     * @param status     状态
     * @param pageNo     当前页
     * @param pageSize   每页记录数
     * @return
     */
    Page<FanNewsCharityOutVo> getFanNewsCharityOutVoPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize);

    /**
     *            慈善收支文章详情
     * @param id        文章iD
     * @param showId    文章展示位置
     * @return
     */
    NewsDetailVo getNewsCharityOutDetail(Integer id, Integer showId);

    /**
     *     新增/修改  慈善收支(文章)
     * @param fanNewsCharityOut   慈善收支
     * @param fanNewsUploadFileList
     * @return
     */
    Boolean insertOrUpdateFanNewsCharityOutVo(FanNewsCharityOut fanNewsCharityOut, List<FanNewsUploadFile> fanNewsUploadFileList);
}
