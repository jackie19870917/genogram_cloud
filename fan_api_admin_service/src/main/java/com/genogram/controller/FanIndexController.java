package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanIndexSlidePic;
import com.genogram.entityvo.FanIndexInfoVo;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanIndexSlidePicService;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;


/**
 *    联谊会后台
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexInfoService iFanIndexInfoService;

    @Autowired
    private IFanNewsUploadFileService iFanNewsUploadFileService;

    @Autowired
    private IFanIndexFamilySummarysService iFanIndexFamilySummarysService;

    @Autowired
    private IFanIndexSlidePicService iFanIndexSlidePicService;

    @RequestMapping(value = "uploadPic", method = RequestMethod.POST)
    public String uploadPic(@RequestParam("file") MultipartFile file) {

        try {

           // ClientGlobal.init(new ClassPathResource("fastDFS.properties").getFile().getAbsolutePath());
//
//            FastdfsClient fastdfsClient = new FastdfsClient(SITE_FAST_DFS);
//
//            //获取到要上传文件对象的原始文件名(Original：原始的)
//            String oldName = file.getOriginalFilename();
//
//            //获取原始文件名中的扩展名(a.b.jpg)
//            String extName = oldName.substring(oldName.lastIndexOf(".") + 1);
//
//            //上传到fastDFS文件服务器
//            String path = fastdfsClient.uploadFile(file.getBytes(), extName);
//
//            //获取fastDFS文件服务器路径
//            path = IP_FAST_DFS + path.substring(path.lastIndexOf("/"));
//
//            System.out.println(path);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *      轮播图
     * @param siteId  网站ID
     * @return
     */
    @RequestMapping(value = "getFanIndexSlidePicList", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@RequestParam(value = "siteId") Integer siteId) {

        List list = new ArrayList();
        //1:正常;2:草稿
        list.add(1);
        list.add(2);

        List<FanIndexSlidePic> fanIndexSlidePicList = iFanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, list);

        return ResponseUtlis.success(fanIndexSlidePicList);
    }

    /**
     * 新增/修改  轮播图
     * @param fanIndexSlidePic
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexSlidePic", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Boolean result = iFanIndexSlidePicService.insertOrUpdateFanIndexSlidePic(fanIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 逻辑删除  轮播图
     * @param fanIndexSlidePic
     * @return
     */
    @RequestMapping(value = "deleteFanIndexSlidePic", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> deleteFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Boolean result = iFanIndexSlidePicService.deleteFanIndexSlidePic(fanIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     *        联谊会简介,宣言
     * @param siteId   网站ID
     * @return
     */
    @RequestMapping(value = "getFanIndexInfo",method = RequestMethod.GET)
    public Response<FanIndexInfoVo> getFanIndexInfo(@RequestParam("siteId") Integer siteId) {

        FanIndexInfoVo fanIndexInfoVo = iFanIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(fanIndexInfoVo);
    }

    /**
     *        /新增或修改    联谊会简介,宣言
     * @param fanIndexInfo   实体类
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Boolean result = iFanIndexInfoService.insertOrUpdateFanIndexInfo(fanIndexInfo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     *  删除   联谊会简介,宣言
     * @param fanIndexInfo
     * @return
     */
    @RequestMapping(value = "deleteFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> deleteFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Boolean result = iFanIndexInfoService.deleteFanIndexInfo(fanIndexInfo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     *           联谊堂
     * @param siteId    网站ID
     * @param pageNo    当前页
     * @param pageSize  每页记录数
     * @return
     */
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@RequestParam(value = "siteId") Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List list = new ArrayList();
        //  1-正常    2-草稿
        list.add(1);
        list.add(2);
        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = iFanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     *      联谊堂详情
     * @param id
     * @return
     */
    @RequestMapping(value = "getFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarys(@RequestParam(value = "id") Integer id) {

        FanIndexFamilySummarys fanIndexFamilySummarys = iFanIndexFamilySummarysService.getFanIndexFamilySummarys(id);

        return ResponseUtlis.success(fanIndexFamilySummarys);
    }

    /**
     *       新增或修改    联谊堂
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(1);
        Boolean result = iFanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 联谊堂 草稿
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarysDrft", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarysDrft(FanIndexFamilySummarys fanIndexFamilySummarys) {

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(2);
        Boolean result = iFanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
    /**
     *   逻辑删除 联谊堂
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "deleteFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> deleteFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        Boolean result = iFanIndexFamilySummarysService.deleteFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

}
