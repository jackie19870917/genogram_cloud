package com.genogram.service;

import com.genogram.entityvo.CharityFundVo;

public interface IFanNewsCharityService {

     /**
      *
      * @param siteId      网站ID
      * @param showId1     慈善支出显示位置
      * @param showId2     捐款名录显示位置
      * @param pageSize    每页记录数
      * @return
      */

     CharityFundVo GetCharityFundVo(Integer siteId,Integer showId1,Integer showId2,Integer newsType, Integer status, Integer pageNo, Integer pageSize);
}
