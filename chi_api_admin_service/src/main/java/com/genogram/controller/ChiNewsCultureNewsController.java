package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsCultureNews;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IChiNewsCultureNewsService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 全国-家族文化文章表 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-19
 */
@Controller
@RequestMapping("/genogram/chiNewsCultureNews")
public class ChiNewsCultureNewsController {

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IChiNewsCultureNewsService chiNewsCultureNewsService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(3);
        list.add(6);
        list.add(9);

        return list;
    }


    /**
     *全国姓氏文化新增 修改
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 9:54
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "全国姓氏文化新增 修改", notes = "")
    @RequestMapping(value = "/addOrUpdateCultureNews", method = RequestMethod.POST)
    public Response<ChiNewsCultureNews> addOrUpdateCultureNews(@ApiParam(value = "省级字派实体类") ChiNewsCultureNews chiNewsCultureNews,
                                                               @ApiParam(value = "上传文件名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                               @ApiParam(value = "上传文件地址") @RequestParam(value = "filePath", required = false) String filePath,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆s
        if (StringsUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringsUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        // 校验敏感词汇
        Set set = allCheckOutService.getSensitiveWord(chiNewsCultureNews.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        //状态(0:删除;1:已发布;2:不显示)
        int status = 1;
        chiNewsCultureNews.setStatus(status);
        Boolean aBoolean = chiNewsCultureNewsService.addOrUpdateCultureNews(chiNewsCultureNews, userLogin,fileName,filePath);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }

}

