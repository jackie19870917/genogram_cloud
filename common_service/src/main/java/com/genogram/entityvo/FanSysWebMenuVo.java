package com.genogram.entityvo;

import com.genogram.entity.FanSysWebMenu;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FanSysWebMenuVo extends FanSysWebMenu {
    private int showId;
    private int fanSysSiteId;
    private int fanSysWebMenuId;
}
