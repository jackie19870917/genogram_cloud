package com.genogram.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entityvo.ChiratyVo;
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
    public ChiratyVo getAllFanNewsCharity(Integer siteId,Integer status,Integer pageNo,Integer pageSize) {

        FanIndexFund fanIndexFund = fanIndexFundService.selectBySiteId(siteId);

        List<FanNewsCharityOut> fanNewsCharityOutList = iFanNewsCharityOutService.selectPage(siteId, status, pageNo, pageSize).getRecords();

        List<DonorVo> donorVoList = iFanNewsCharityPayInService.queryMyItems(siteId, status, pageNo, pageSize);

        ChiratyVo chiratyVo = new ChiratyVo();
        chiratyVo.setFanIndexFund(fanIndexFund);
        chiratyVo.setFanNewsCharityOutList(fanNewsCharityOutList);
        chiratyVo.setDonorVoList(donorVoList);

        return chiratyVo;
    }
}
