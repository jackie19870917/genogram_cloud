package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 *省级祖先分支
 *@Author: yuzhou
 *@Date: 2018-11-20
 *@Time: 11:40
 *@Param:
 *@return:
 *@Description:
*/
@Api(description = "省级祖先分支")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsAncestor")
public class ProNewsAncestorController {


    /**
     *
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 11:52
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "proSelectRecortPage",method = RequestMethod.GET)
    public Response<ProNewsFamousAncestor> selectRecortPage(
            @RequestParam(value = "showId") Integer showId, // 显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        /*try {
            int status = 1;
            Page<ProNewsFamousAncestor> proFamilyRecordPage = iProNewsFamilyRecordService.getProFamilyRecordPage(showId, status, pageNo, pageSize);
            if(proFamilyRecordPage==null){
                //没有取到参数,返回空参
                Page<ProNewsFamousAncestor> proNewsFamousAncestor = new Page<ProNewsFamousAncestor>();
                return ResponseUtlis.error(Constants.ERRO_CODE,proNewsFamousAncestor);
            }
            return ResponseUtlis.success(proFamilyRecordPage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }*/
        return null;
    }
}

