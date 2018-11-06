package com.genogram.entityvo;

import com.genogram.entity.FanSysWebMenu;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
public class FanSysWebMenuVo extends FanSysWebMenu {
    private int showId;
    private int fanSysSiteId;
    private int fanSysWebMenuId;
    private List<FanSysWebMenuVo> child;
}
