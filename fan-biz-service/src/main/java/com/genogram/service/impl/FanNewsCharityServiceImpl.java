package com.genogram.service.impl;

import com.genogram.service.IFanIndexFundService;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanNewsCharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 18:55
 *@Param: 
 *@return:
 *@Description:
*/
@Service
public class FanNewsCharityServiceImpl implements IFanNewsCharityService {

    @Autowired
    private IFanIndexFundService fanIndexFundService;

    @Autowired
    private IFanNewsCharityOutService iFanNewsCharityOutService;

    @Autowired
    private IFanNewsCharityPayInService iFanNewsCharityPayInService;

   /* @Override
    public CharityFundVo getCharityFundVo(Integer siteId,Integer showId1,Integer showId2,Integer newsType, Integer status, Integer pageNo, Integer pageSize) {

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

        List<FanNewsCharityOut> fanNewsCharityOutList = iFanNewsCharityOutService.getFanNewsCharityOutPage(siteId,showId1, status, pageNo, pageSize).getRecords();

        List<DonorVo> donorVoList = iFanNewsCharityPayInService.getDonorVoPage(showId2, status, pageNo, pageSize);

        CharityFundVo charityFundVo = new CharityFundVo();
        charityFundVo.setFanIndexFund(fanIndexFund);
        charityFundVo.setFanNewsCharityOutList(fanNewsCharityOutList);
        charityFundVo.setDonorVoList(donorVoList);

        return charityFundVo;
    }*/
}
