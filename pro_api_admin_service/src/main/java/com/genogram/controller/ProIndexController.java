package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProIndexInfo;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.IProIndexInfoService;
import com.genogram.service.IProIndexSlidePicService;
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
 * <p>
 * 省级首页-设置 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "省级首页(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/proIndex")
public class ProIndexController {

    @Autowired
    private IProIndexInfoService proIndexInfoService;

    @Autowired
    private IProIndexSlidePicService proIndexSlidePicService;

    @Autowired
    private IUserService userService;

    /**
     * 省级网站信息
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "getIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> getIndexInfoVo(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = proIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(indexInfoVo);
    }

    @ApiOperation(value = "新增/修改基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "insertOrUpdateIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> insertOrUpdateIndexInfo(IndexInfoVo indexInfoVo,
                                                         @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(indexInfoVo.getId())) {
            indexInfoVo.setCreateUser(userLogin.getId());
        }

        indexInfoVo.setUpdateUser(userLogin.getId());
        Boolean result = proIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 删除   省级基本信息
     *
     * @param proIndexInfo
     * @return
     */
    @ApiOperation(value = "删除基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "deleteProIndexInfo", method = RequestMethod.POST)
    public Response<ProIndexInfo> deleteFanIndexInfo(ProIndexInfo proIndexInfo,
                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);
        proIndexInfo.setUpdateUser(userLogin.getId());

        Boolean result = proIndexInfoService.deleteProIndexInfo(proIndexInfo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 轮播图
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> getProIndexSlidePic(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();

        //状态   1-前后台显示    2-前台不显示      0-前后台都不显示
        list.add(1);
        list.add(2);

        List<ProIndexSlidePic> proIndexSlidePic = proIndexSlidePicService.getProIndexSlidePic(siteId, list);

        return ResponseUtlis.success(proIndexSlidePic);
    }

    /**
     * 新增/修改  轮播图
     *
     * @param proIndexSlidePic
     * @return
     */
    @ApiOperation(value = "新增/修改 轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片,sort:排序")
    @RequestMapping(value = "insertOrUpdateProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> insertOrUpdateProIndexSlidePic(ProIndexSlidePic proIndexSlidePic,
                                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(proIndexSlidePic.getId())) {
            proIndexSlidePic.setCreateUser(userLogin.getId());
        }

        proIndexSlidePic.setUpdateUser(userLogin.getId());
        Boolean result = proIndexSlidePicService.insertOrUpdateProIndexSlidePic(proIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除  轮播图
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "deleteProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> deleteProIndexSlidePic(@ApiParam("主键") @RequestParam Integer id,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        //用户Id
        Integer userId = userService.getUserLoginInfoByToken(token).getId();

        Boolean result = proIndexSlidePicService.deleteProIndexSlidePic(id, userId);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}
