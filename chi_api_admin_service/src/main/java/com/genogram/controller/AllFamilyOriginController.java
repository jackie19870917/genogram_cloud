package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllFamilyOrigin;
import com.genogram.entity.AllUserLogin;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IAllFamilyOriginService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 姓氏起源 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-18
 */
@CrossOrigin(origins = "*")
@Api(description = "姓氏起源")
@RestController
@RequestMapping("/genogram/allFamilyOrigin")
public class AllFamilyOriginController {

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IAllFamilyOriginService allFamilyOriginService;

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
     * 全国姓氏起源新增
     *
     * @Author: yuzhou
     * @Date: 2019-02-18
     * @Time: 11:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "全国姓氏起源新增 修改", notes = "")
    @RequestMapping(value = "/addOrUpdateOrigin", method = RequestMethod.POST)
    public Response<AllFamilyOrigin> addOrUpdateOrigin(@ApiParam(value = "省级字派实体类") AllFamilyOrigin allFamilyOrigin,
                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        // 校验敏感词汇
        Set set1 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getFamilyParaphrase());
        Set set2 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getDerive());
        Set set3 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getJunWang());
        Set set4 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getMigrateLine());
        Set set5 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getPopulationDistribution());
        Set set6 = allCheckOutService.getSensitiveWord(allFamilyOrigin.getFamilyFigure());

        if (set1 != null && set1.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set1);
        } else if (set2 != null && set2.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set2);
        } else if (set3 != null && set3.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set3);
        } else if (set4 != null && set4.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set4);
        } else if (set5 != null && set5.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set5);
        } else if (set6 != null && set6.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set6);
        }

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Boolean aBoolean = allFamilyOriginService.addOrUpdateOrigin(allFamilyOrigin, userLogin);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }
}

