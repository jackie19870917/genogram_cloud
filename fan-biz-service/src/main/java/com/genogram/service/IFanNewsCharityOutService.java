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

    //慈善支出(用途)
    Page<FanNewsCharityOut> getFanNewsCharityOutPage(Integer showId, Integer newsType, Integer status, Integer pageNo, Integer pageSize);

    //慈善收支(文章)
    Page<FanNewsCharityOutVo> getFanNewsCharityOutVoPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize);

    //慈善收支(详情)
    NewsDetailVo getNewsCharityOutDetail(Integer id, Integer showId);

    //新增/修改 慈善收支(文章)
    Boolean insertOrUpdateFanNewsCharityOutVo(FanNewsCharityOut fanNewsCharityOut, List<FanNewsUploadFile> fanNewsUploadFileList);
}
