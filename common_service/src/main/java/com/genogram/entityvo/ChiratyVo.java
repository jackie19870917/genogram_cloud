package com.genogram.entityvo;

import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityOut;
import lombok.Data;

import java.util.List;

@Data
public class ChiratyVo {

    private FanIndexFund fanIndexFund;
    private List<FanNewsCharityOut> fanNewsCharityOutList;
    private List<DonorVo> donorVoList;

}
