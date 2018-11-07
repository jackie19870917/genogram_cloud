package com.genogram.service;

import com.genogram.entityvo.CharityFundVo;

public interface IFanNewsCharityService {

     CharityFundVo GetCharityFundVo(Integer siteId,Integer newsType, Integer status, Integer pageNo, Integer pageSize);
}
