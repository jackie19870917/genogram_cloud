package com.genogram.service.impl;

import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entityvo.CharityFundVo;
import com.genogram.entityvo.DonorVo;
import com.genogram.service.IFanIndexFundService;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanNewsCharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanNewsCharityServiceImpl implements IFanNewsCharityService {

    @Autowired
    private IFanIndexFundService fanIndexFundService;

    @Autowired
    private IFanNewsCharityOutService iFanNewsCharityOutService;

    @Autowired
    private IFanNewsCharityPayInService iFanNewsCharityPayInService;

    @Override
    public CharityFundVo GetCharityFundVo(Integer siteId,Integer newsType, Integer status, Integer pageNo, Integer pageSize) {

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

        List<FanNewsCharityOut> fanNewsCharityOutList = iFanNewsCharityOutService.getFanNewsCharityOutPage(siteId,newsType, status, pageNo, pageSize).getRecords();

        List<DonorVo> donorVoList = iFanNewsCharityPayInService.getDonorVoPage(siteId, status, pageNo, pageSize);

        CharityFundVo charityFundVo = new CharityFundVo();
        charityFundVo.setFanIndexFund(fanIndexFund);
        charityFundVo.setFanNewsCharityOutList(fanNewsCharityOutList);
        charityFundVo.setDonorVoList(donorVoList);

        return charityFundVo;
    }
}
