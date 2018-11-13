package com.genogram.entityvo;

import com.genogram.entity.FanSysWebMenu;
import lombok.Data;

import java.util.List;

@Data
/**
 * @Author: wang,wei
 * @Date: 2018/11/5
 * @Time: 21:17
 * @Param: 
 * @return: 
 * @Description:
 *
 */
public class SysWebMenuVo extends FanSysWebMenu {
    private int showId;
    private int fanSysSiteId;
    private int fanSysWebMenuId;
    private List<SysWebMenuVo> child;
}
