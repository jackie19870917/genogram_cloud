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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 家族名人
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsFamous")
public class FanNewsFamousController {
        @Autowired
        private IFanNewsFamousPersonService iFanNewsFamousPersonService;

    /**
     *联谊会家族名人后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value ="/getFamilyPersionPage",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersionPage(
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
            Page<FamilyPersonVo> familyPersonVoPage = iFanNewsFamousPersonService.getFamilyPersionPages(entity, pageNo, pageSize);
            if (familyPersonVoPage == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
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
    @RequestMapping(value ="/getFamilyPersionDetail",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersionDetail(
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
    @RequestMapping(value ="/getFamilyPersionAmend",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getFamilyPersionAmend(
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
            FamilyPersonVo familyPersonVo = iFanNewsFamousPersonService.getFamilyPersionDetail(id);
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
    @RequestMapping(value = "/addOrUpdatePersion", method = RequestMethod.POST)
    public Response<FanNewsFamousPerson> addOrUpdateIndustry(FanNewsFamousPerson fanNewsFamousPerson, String fileNames) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamousPerson.setStatus(1);
        return getFanNewsPersionResponse(fanNewsFamousPerson, fileNames);
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
    @RequestMapping(value = "/addOrUpdateIndustryDrft", method = RequestMethod.POST)
    public Response<FanNewsFamousPerson> addOrUpdateIndustryDrft(FanNewsFamousPerson fanNewsFamousPerson, String fileNames) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamousPerson.setStatus(2);
        return getFanNewsPersionResponse(fanNewsFamousPerson, fileNames);
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
    private Response<FanNewsFamousPerson> getFanNewsPersionResponse(FanNewsFamousPerson fanNewsFamousPerson, String fileNames) {
        try {
            // 插入数据
            boolean b = iFanNewsFamousPersonService.addOrUpdatePersion(fanNewsFamousPerson, fileNames);
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
    @RequestMapping(value ="/deletePersionById",method = RequestMethod.GET)
    public Response<FanNewsFamousPerson> deletePersionById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = iFanNewsFamousPersonService.deletePersionById(id, status);
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
