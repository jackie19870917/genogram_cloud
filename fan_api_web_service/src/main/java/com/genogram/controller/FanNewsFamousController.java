package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.service.IFanNewsFamousPersonService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会-家族名人,家族长老,家族栋梁,组织架构 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@Api(description = "联谊会家族名人")
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsFamous")
public class FanNewsFamousController {
    @Autowired
    private IFanNewsFamousPersonService iFanNewsFamousPersonService;
    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    /**
     * 家族长老查询,组织架构
     */
    @ResponseBody
    @ApiOperation(value = "家族名人分页", notes = "showId:显示位置,pageNo:当前页,pageSize:总页数")
    @RequestMapping(value = "selectPersonPage", method = RequestMethod.GET)
    public Response<FanNewsFamousPerson> selectPersonPage(
            @RequestParam(value = "showId") Integer showId, // 显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<FanNewsFamousPerson> familyPersonVo = iFanNewsFamousPersonService.getFamilyPersionPage(showId, status, pageNo, pageSize);
            if (familyPersonVo == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(familyPersonVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族文化详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "文章id")
    @RequestMapping(value = "/getFamilyFamilyDetail", method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyCultureDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            FamilyPersonVo familyPersonVo = new FamilyPersonVo();
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "数据为空");
            }
            FamilyPersonVo newsDetailVo = iFanNewsFamousPersonService.getFamilyFamilyDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            //增加查看数
            iFanNewsFamousPersonService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

//    /**
//     * 组织架构
//     */
//    @ResponseBody
//    @ApiOperation(value = "组织架构分页", notes = "showId:显示位置,pageNo:当前页,pageSize:总页数")
//    @RequestMapping(value = "selectFramework",method = RequestMethod.GET)
//    public Response<FanNewsFamousPerson> selectFramework(
//            @RequestParam(value = "site_id") Integer site_id // 产业显示位置
//    ) {
//        try {
//            Map map = new LinkedHashMap();
//            //拿到会长的showid
//            FanSysWebNewsShow show = fanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(site_id,"persion_huizhang");
//            //
//            show.getShowId();
//            //
//            show.getMenuName();
//
//
//
//
//            //key huizhang,huuizhang list
//            map.put(show.getMenuName(),huizhanglist);
//
//            Page<FanNewsFamousPerson> familyPersonVo = iFanNewsFamousPersonService.getFamilyFramePage(showId, status, pageNo, pageSize);
//            if (familyPersonVo == null) {
//                //没有取到参数,返回空参
//                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
//                return ResponseUtlis.error(Constants.ERRO_CODE, emptfamilyCultureVo);
//            }
//            return ResponseUtlis.success(familyPersonVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
//        }
//    }
}

