package com.genogram.service;

import com.genogram.entity.FanNewsCharityPayIn;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.DonorVo;

import java.util.List;

/**
 * <p>
 * 联谊网-慈善公益-捐款人 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCharityPayInService extends IService<FanNewsCharityPayIn> {

    //捐款名录(个人总金额)
    List<DonorVo> getDonorVoPage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    //捐款名录(最新记录)
    List<DonorVo> getDonorVoPageByTime(Integer showId, Integer status, Integer pageNo, Integer pageSize);
}