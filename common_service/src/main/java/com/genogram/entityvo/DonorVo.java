package com.genogram.entityvo;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCharityPayIn;
import lombok.Data;

/**
 *          捐款名录
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 10:40
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class DonorVo{

    private FanNewsCharityPayIn fanNewsCharityPayIn;
    private AllUserLogin allUserLogin;
}
