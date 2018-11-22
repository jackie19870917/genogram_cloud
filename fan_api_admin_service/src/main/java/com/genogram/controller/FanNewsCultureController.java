package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *联谊会家族文化增删改查
 *@Author: yuzhou
 *@Date: 2018-11-09
 *@Time: 16:00
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsCulture")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService fanNewsCultureZipaiService;

    @Autowired
    private IFanNewsCultureNewsService fanNewsCultureNewsService;

    @Autowired
    private IUserService userService;

    /**
     *联谊会家族字派后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token")@RequestParam("token")String token
    ) {
        try {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }

            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            //查询条件
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
                entity.eq("show_id", Integer.valueOf(showId));
            if(statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<NewsCultureZipaiVo> fanNewsCultureZipai = fanNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if(fanNewsCultureZipai==null){
                //没有取到参数,返回空参
                Page<FanNewsCultureZipai> emptfanNewsCultureZipai = new Page<FanNewsCultureZipai>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfanNewsCultureZipai);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族字派后台进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getZiPaiDetail",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getZiPaiDetail(
            @RequestParam(value = "id") Integer id, // 家族字派文章ID
            @ApiParam("token")@RequestParam("token")String token
    ){
        try {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断id是否有值
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            FanNewsCultureZipai fanNewsCultureZipai=fanNewsCultureZipaiService.getZiPaiDetail(id);
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族字派后台新增修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateZiPai",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai,
                                                          @ApiParam("token")@RequestParam("token")String token){
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureZipai.setStatus(1);
        return getFanNewsCultureZipaiResponse(fanNewsCultureZipai,token);
    }

    /**
     *联谊会家族字派后台新增修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 11:16
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateZiPaiDrft",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> addOrUpdateZiPaiDrft(FanNewsCultureZipai fanNewsCultureZipai,
                                                              @ApiParam("token")@RequestParam("token")String token){
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureZipai.setStatus(2);
        return getFanNewsCultureZipaiResponse(fanNewsCultureZipai,token);
    }

    /**
     *联谊会家族字派后台新增修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-100
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanNewsCultureZipai> getFanNewsCultureZipaiResponse(FanNewsCultureZipai fanNewsCultureZipai,@ApiParam("token")@RequestParam("token")String token) {
        try {
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            if(fanNewsCultureZipai.getId()==null){
                //创建人
             fanNewsCultureZipai.setCreateUser(userLoginInfoByToken.getId());
             //修改人
             fanNewsCultureZipai.setUpdateUser(userLoginInfoByToken.getId());
            }else{
                //修改人
                fanNewsCultureZipai.setUpdateUser(userLoginInfoByToken.getId());
            }
            boolean result=fanNewsCultureZipaiService.addOrUpdateZiPai(fanNewsCultureZipai);
            if( ! result){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

 /**
  *联谊会家族字派后台删除
  *@Author: yuzhou
  *@Date: 2018-11-10
  *@Time: 10:05
  *@Param:
  *@return:
  *@Description:
 */
    @RequestMapping(value ="/deleteZipaiById",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> deleteZipaiById(
            @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam("token")String token
    ) {
        try {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断id是否为空
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = fanNewsCultureZipaiService.deleteZipaiById(id, status);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族文化后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token")@RequestParam("token")String token
    ) {
        try{
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        List statusList  = new ArrayList();
        statusList.add(1);
        statusList.add(2);
        //查询文章信息的条件
        Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
        entity.eq("show_id", Integer.valueOf(showId));
        if (statusList.size()!=0){
            entity.in("status", statusList);
        }
        entity.orderBy("create_time", false);
        Page<FamilyCultureVo> familyCultureVoList = fanNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
        if (familyCultureVoList == null) {
            //没有取到参数,返回空参
            Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
            return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
        }
        return ResponseUtlis.success(familyCultureVoList);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseUtlis.error(Constants.FAILURE_CODE,null);
    }
    }

    /**
     *联谊会家族文化后台详情查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCultureDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam("token")String token
    ) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //判断id是否为空
        if(id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getNewsDetailVoResponse( id);
    }

    /**
     *联谊会家族文化后台进入修改页面
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCultureAmend",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureAmend(
            @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam("token")String token
    ) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //判断id是否为空
        if(id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getNewsDetailVoResponse(id);
    }

    /**
     *家族文化详情修改公共方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
     *@Param:
     *@return:
     *@Description:
    */
    private Response<NewsDetailVo> getNewsDetailVoResponse(@RequestParam("id") Integer id) {
        try {
            NewsDetailVo newsDetailVo = fanNewsCultureNewsService.getFamilyCultureDetail(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *联谊会家族文化后台添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateCulture", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addOrUpdateCulture(FanNewsCultureNews fanNewsCultureNews,
                                                           String fileName,String filePath,
                                                           @ApiParam("token")@RequestParam("token")String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureNews.setStatus(1);
        return getFanNewsCultureNewsResponse(fanNewsCultureNews, fileName,filePath,token);
    }

    /**
     *联谊会家族文化后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateCultureDrft", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addOrUpdateCultureDrft(FanNewsCultureNews fanNewsCultureNews,
                                                               String fileName,String filePath,
                                                               @ApiParam("token")@RequestParam("token")String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureNews.setStatus(2);
        return getFanNewsCultureNewsResponse(fanNewsCultureNews, fileName,filePath,token);
    }

    /**
     *家族文化后台添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:18
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanNewsCultureNews> getFanNewsCultureNewsResponse(FanNewsCultureNews fanNewsCultureNews,
                                                                       String fileName,String filePath,
                                                                       @ApiParam("token")@RequestParam("token")String token) {
        try{
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            if(fanNewsCultureNews.getId()==null){
                //创建人
                fanNewsCultureNews.setCreateUser(userLoginInfoByToken.getId());
                //修改人
                fanNewsCultureNews.setUpdateUser(userLoginInfoByToken.getId());
            }else{
                //修改人
                fanNewsCultureNews.setUpdateUser(userLoginInfoByToken.getId());
            }
            // 插入数据
            boolean insert = fanNewsCultureNewsService.addOrUpdateCulture(fanNewsCultureNews,fileName,filePath);
            if( ! insert){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }


    /**
     *联谊会家族文化后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 9:43
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/deleteCulturById",method = RequestMethod.GET)
    public Response<FanNewsCultureNews> deleteCulturById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = fanNewsCultureNewsService.deleteCulturById(id, status);
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
