package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族慈善财 后台控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanNewsCharity/")
public class FanNewsCharityController {

    @Autowired
    private IFanNewsCharityOutService iFanNewsCharityOutService;

    /**
     * 慈善收支
     *
     * @param showId   慈善收支显示位置
     * @param newsType 种类(1.财政支出;2.财政收入)
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */

    @RequestMapping(value = "getFanNewsCharityOutPage", method = RequestMethod.GET)
    public Response<FanNewsCharityOutVo> getFanNewsCharityOutVo(@RequestParam("showId") Integer showId,
                                                                @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                                @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List list = new ArrayList();
        //   1-正常   2-草稿
        Integer status = 1;
        Integer statu = 2;

        list.add(status);
        list.add(statu);

        Page<FanNewsCharityOutVo> fanNewsCharityOutPage = iFanNewsCharityOutService.getFanNewsCharityOutVoPage(showId, newsType, list, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }


}
