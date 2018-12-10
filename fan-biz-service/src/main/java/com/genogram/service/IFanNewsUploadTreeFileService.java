package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-04
 */
public interface IFanNewsUploadTreeFileService extends IService<FanNewsUploadTreeFile> {

    /**
     * 查询电子谱
     *
     * @param fileName
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanNewsUploadTreeFile> getFanNewsUploadTreeFile(String fileName, List list, Integer pageNo, Integer pageSize);

    /**
     * 新增/修改 电子谱
     *
     * @param fanNewsUploadTreeFile
     * @return
     */
    Boolean insertOrUpdateFanNewsUploadTreeFile(FanNewsUploadTreeFile fanNewsUploadTreeFile);

    /**
     * 单一查询
     *
     * @param id
     * @return
     */
    FanNewsUploadTreeFile getFanNewsUploadTreeFile(Integer id);

    /**
     * 删除电子谱
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteFanNewsUploadTreeFile(Integer id, Integer userId);
}
