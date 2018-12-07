package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.service.IFanNewsFamousPersonService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 家族名人
 * @author Administrator
 */
@RestController
@Api(description = "家族名人(后台)")
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsFamous")
public class FanNewsFamousController {
        @Autowired
        private IFanNewsFamousPersonService fanNewsFamousPersonService;

    /**
     *联谊会家族名人后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人后台查询")
    @RequestMapping(value ="/getFamilyPersonPage",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersonPage(
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList  = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<FanNewsFamousPerson> entity = new EntityWrapper<FanNewsFamousPerson>();
            entity.eq("show_id", showId);
            if (statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<FamilyPersonVo> familyPersonVoPage = fanNewsFamousPersonService.getFamilyPersionPages(entity, pageNo, pageSize);
            if (familyPersonVoPage == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"数据为空");
            }
            return ResponseUtlis.success(familyPersonVoPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
    /**
     *联谊会家族名人的详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人详情后台查询")
    @RequestMapping(value ="/getFamilyPersonDetail",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersonDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }

    /**
     *联谊会家族名人进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人进入修改")
    @RequestMapping(value ="/getFamilyPersonAmend",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersonAmend(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }

    /**
     *联谊会家族名人文章进入修改页面抽取方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FamilyPersonVo> getNewsDetailVoResponse( @RequestParam("id") Integer id) {
        try {
            FamilyPersonVo familyPersonVo = fanNewsFamousPersonService.getFamilyPersionDetail(id);
            return ResponseUtlis.success(familyPersonVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会家族名人后台添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人后台添加和修改 发表")
    @RequestMapping(value = "/addOrUpdatePerson", method = RequestMethod.POST)
    public Response<FanNewsFamousPerson> addOrUpdatePerson(FanNewsFamousPerson fanNewsFamousPerson,String fileName, String filePath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamousPerson.setStatus(1);
        return getFanNewsPersonResponse(fanNewsFamousPerson, fileName,  filePath);
    }

    /**
     *联谊会家族名人后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:10
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人后台添加和修改 草稿")
    @RequestMapping(value = "/addOrUpdateIndustryDrft", method = RequestMethod.POST)
    public Response<FanNewsFamousPerson> addOrUpdateIndustryDrft(FanNewsFamousPerson fanNewsFamousPerson, String fileName, String filePath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamousPerson.setStatus(2);
        return getFanNewsPersonResponse(fanNewsFamousPerson,fileName,filePath);
    }

    /**
     *联谊会家族名人后台添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FanNewsFamousPerson> getFanNewsPersonResponse(FanNewsFamousPerson fanNewsFamousPerson, String fileName, String filePath) {
        try {
            // 插入数据
            boolean b = fanNewsFamousPersonService.addOrUpdatePersion(fanNewsFamousPerson, fileName, filePath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会家族名人后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:22
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation("家族名人后台删除")
    @RequestMapping(value ="/deletePersonById",method = RequestMethod.GET)
    public Response<FanNewsFamousPerson> deletePersonById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = fanNewsFamousPersonService.deletePersionById(id, status);
            if (!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
