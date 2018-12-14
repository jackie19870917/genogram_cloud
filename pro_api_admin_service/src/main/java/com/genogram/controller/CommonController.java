package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllRegion;
import com.genogram.entity.AllUserLogin;
import com.genogram.service.IAllRegionService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 省级公共
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proPublic")
@Api(description = "公共方法")
public class CommonController {

    @Autowired
    private IAllRegionService allRegionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(2);
        list.add(5);
        list.add(9);

        return list;
    }


    /**
     * 省级下属县级官网查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 18:47
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级下属县级官网查询", notes = "根据sizeId查询")
    @RequestMapping(value = "/provincialSubordinate", method = RequestMethod.GET)
    public Response<AllRegion> getProvincialSubordinate(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                        @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            List<AllRegion> provincialSubordinate = allRegionService.getProvincialSubordinate(siteId);
            if (provincialSubordinate == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(provincialSubordinate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
