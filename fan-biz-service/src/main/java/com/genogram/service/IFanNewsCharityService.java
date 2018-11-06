package com.genogram.service;

import com.genogram.entityvo.ChiratyVo;

public interface IFanNewsCharityService {

     ChiratyVo getAllFanNewsCharity(Integer siteId,Integer status,Integer pageNo,Integer pageSize);
}
