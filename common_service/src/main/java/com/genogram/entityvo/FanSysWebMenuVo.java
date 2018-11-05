package com.genogram.entityvo;

import com.genogram.entity.FanSysWebMenu;
import lombok.Data;
@Data
public class FanSysWebMenuVo extends FanSysWebMenu {
    private int showId;
    private int fanSysSiteId;
    private int fanSysWebMenuId;
}
