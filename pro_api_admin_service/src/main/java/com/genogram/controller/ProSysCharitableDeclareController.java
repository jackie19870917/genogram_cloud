package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProFanSysCharitableDeclareService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后端控制器
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Api(description = "省级后台家族慈善申报")
@RestController
@RequestMapping("/genogram/fanSysCharitableDeclare")
public class ProSysCharitableDeclareController {
    @Autowired
    private IProFanSysCharitableDeclareService fanSysCharitableDeclareService;

    @Autowired
    private IUserService userService;
    /**
     *省级慈善帮扶申报详情页查询
     *@Author: yuzhou
     *@Date: 2018-12-12
     *@Time: 15:32
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级慈善帮扶申报详情页查询", notes = "show_id:网站id")
    @RequestMapping(value = "/getSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureList(
            @ApiParam("显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam("当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页信息条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断showId是否为空
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入showId");
            }
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            List<Integer> list=new ArrayList<>();
            list.add(0);
            list.add(1);
            list.add(3);
            Wrapper<FanSysCharitableDeclare> entity = new EntityWrapper<FanSysCharitableDeclare>();
            entity.eq("show_id", showId);
            entity.in("status", list);
            Page<FanSysCharitableDeclare> fanSysCharitableDeclarePage = fanSysCharitableDeclareService.getCharitableDeclarePage(entity, pageNo, pageSize);
            if (fanSysCharitableDeclarePage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.success(fanSysCharitableDeclarePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级慈善帮扶申报详情
     *@Author: yuzhou
     *@Date: 2018-12-12
     *@Time: 16:13
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级慈善帮扶申报详情", notes = "")
    @RequestMapping(value = "/getFamilyStructureDetails", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureDetails(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入Id");
            }
            FanSysCharitableDeclare fanSysCharitableDeclare=fanSysCharitableDeclareService.getFamilyStructureDetails(id);
            if (fanSysCharitableDeclare==null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.success(fanSysCharitableDeclare);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级慈善帮扶申报详情 同意按钮
     *@Author: yuzhou
     *@Date:
     *@Time: 10:03
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级慈善帮扶申报详情 同意按钮", notes = "")
    @RequestMapping(value = "/familyStructureDetailsConsent", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> familyStructureDetailsConsent(
            @ApiParam("慈善帮扶表") FanSysCharitableDeclare fanSysCharitableDeclare,
            @ApiParam("区分三级别审核 1:审查人,2:审核人,3:审批人") @RequestParam(value = "ratify") Integer ratify,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            Boolean aBoolean =fanSysCharitableDeclareService.familyStructureDetailsConsent(userLoginInfoByToken,fanSysCharitableDeclare,ratify);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级慈善帮扶申报详情 退回按钮
     *@Author: yuzhou
     *@Date:
     *@Time: 10:03
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级慈善帮扶申报详情 退回按钮", notes = "")
    @RequestMapping(value = "/familyStructureDetailsDisagree", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> familyStructureDetailsDisagree(
            @ApiParam("慈善帮扶表") FanSysCharitableDeclare fanSysCharitableDeclare,
            @ApiParam("区分三级别审核 1:审查人,2:审核人,3:审批人") @RequestParam(value = "ratify") Integer ratify,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            Boolean aBoolean =fanSysCharitableDeclareService.familyStructureDetailsDisagree(userLoginInfoByToken,fanSysCharitableDeclare,ratify);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级慈善帮扶总金额
     *@Author: yuzhou
     *@Date: 2018-12-13
     *@Time: 12:05
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级慈善帮扶总金额", notes = "")
    @RequestMapping(value = "/familyStructureMoney", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> familyStructureMoney(
            @ApiParam("显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断showId是否为空
            if(StringsUtils.isEmpty(showId)){
                return ResponseUtlis.error(Constants.IS_EMPTY,"输入正确的showId");
            }
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            List<Integer> list=new ArrayList<>();
            list.add(0);
            list.add(1);
            list.add(3);

            //放入查询条件
            Map map=new HashMap(16);
            map.put("status",list);
            map.put("showId",showId);
            BigDecimal money=fanSysCharitableDeclareService.familyStructureMoney(map);
            return ResponseUtlis.success(money);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级慈善帮扶经办人输入金额
     *@Author: yuzhou
     *@Date: 2018-12-13
     *@Time: 16:12
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级慈善帮扶经办人输入金额", notes = "")
    @RequestMapping(value = "/familyStructureResponsiblePerson", method = RequestMethod.POST)
    public Response<FanSysCharitableDeclare> familyStructureResponsiblePerson(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id,
            @ApiParam("经办人输入的金额") @RequestParam(value = "responsiblePersonMoney") BigDecimal responsiblePersonMoney,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断id是否为空
            if(StringsUtils.isEmpty(id)){
                return ResponseUtlis.error(Constants.IS_EMPTY,"请输入主键Id");
            }
            //判断responsiblePersonMoney是否为空
            if(StringsUtils.isEmpty(responsiblePersonMoney)){
                return ResponseUtlis.error(Constants.IS_EMPTY,"请输入正确的金额");
            }
            Boolean aBoolean=fanSysCharitableDeclareService.familyStructureResponsiblePerson(userLoginInfoByToken,id,responsiblePersonMoney);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,"存入失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,"存入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }




}

