package com.genogram.entityvo;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCharityPayIn;
import lombok.Data;

//捐款人
@Data
public class DonorVo{

    private FanNewsCharityPayIn fanNewsCharityPayIn;
    private AllUserLogin allUserLogin;
}
