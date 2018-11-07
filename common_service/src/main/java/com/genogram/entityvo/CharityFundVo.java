package com.genogram.entityvo;

import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityOut;
import lombok.Data;

import java.util.List;

//慈善基金
@Data
public class CharityFundVo {

    private FanIndexFund fanIndexFund;
    private List<FanNewsCharityOut> fanNewsCharityOutList;
    private List<DonorVo> donorVoList;

}
