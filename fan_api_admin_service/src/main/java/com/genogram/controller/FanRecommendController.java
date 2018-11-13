package com.genogram.controller;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.service.IFanSysRecommendService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 *联谊会推荐
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/recommend")
public class FanRecommendController {

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

    /**
     *联谊会后台点击推荐
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 9:16
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> addRecommend(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id //主键
    ) {
        if(showId==null || id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        fanSysRecommendService.addRecommend(showId,id);
        return null;
    }


}
