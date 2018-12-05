package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.entity.ProNewsUploadTreeFile;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-03
 */
public interface IProNewsUploadTreeFileService extends IService<ProNewsUploadTreeFile> {

    /**
     * 查询省级和县级电子谱
     * @param regionCode
     * @param fileName
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page getProNewsUploadTreeFile(String regionCode,String fileName, List list, Integer pageNo, Integer pageSize);

    /**
     * 查询省级电子谱
     * @param fileName
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ProNewsUploadTreeFile> getProNewsUploadTreeFilePage(String fileName, List list, Integer pageNo, Integer pageSize);
    /**
     * 新增电子谱
     * @param proNewsUploadTreeFile
     * @return
     */
    Boolean insertProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile);

    /**
     * 单一查询
     * @param id
     * @return
     */
    ProNewsUploadTreeFile getProNewsUploadTreeFile(Integer id);

    /**
     * 单一查询
     * @param id
     * @return
     */
    FanNewsUploadTreeFile getFanNewsUploadTreeFile(Integer id);
    /**
     * 修改电子谱
     * @param proNewsUploadTreeFile
     * @return
     */
    Boolean updateProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile);

    /**
     * 删除电子谱
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteProNewsUploadTreeFile(Integer id, Integer userId);
}
