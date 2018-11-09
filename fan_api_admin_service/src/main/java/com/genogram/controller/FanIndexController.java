package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entityvo.FanIndexInfoVo;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.genogram.config.Constants.IP_FAST_DFS;
import static com.genogram.config.Constants.SITE_FAST_DFS;

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

    @RequestMapping(value = "uploadPic", method = RequestMethod.POST)
    public String uploadPic(@RequestParam("file") MultipartFile file) {

        try {
            FastdfsClient fastdfsClient = new FastdfsClient(SITE_FAST_DFS);

            //获取到要上传文件对象的原始文件名(Original：原始的)
            String oldName = file.getOriginalFilename();

            //获取原始文件名中的扩展名(a.b.jpg)
            String extName = oldName.substring(oldName.lastIndexOf(".") + 1);

            //上传到fastDFS文件服务器
            String path = fastdfsClient.uploadFile(file.getBytes(), extName);

            //获取fastDFS文件服务器路径
            path = IP_FAST_DFS + path.substring(path.lastIndexOf("/"));

            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

        Boolean aBoolean = iFanIndexInfoService.insertOrUpdateFanIndexInfo(fanIndexInfo);

        if (aBoolean) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     *        新增或修改    联谊堂
     * @param siteId    网站ID
     * @param pageNo    当前页
     * @param pageSize  每页记录数
     * @return
     */
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@RequestParam(value = "siteId") Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Integer status = 1;
        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = iFanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, status, pageNo, pageSize);

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
     *       /新增或修改    联谊堂
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        Boolean aBoolean = iFanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (aBoolean) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
}
