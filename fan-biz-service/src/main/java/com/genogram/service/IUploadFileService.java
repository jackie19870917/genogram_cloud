package com.genogram.service;

/**
 * Uploadfile to DB
 * @Author: wang,wei
 * @Date: 2018-11-11
 * @Time: 23:12
 * @return:
 * @Description:
 *
 */
public interface IUploadFileService {
    /**
     * 保存文件
     * @Author: wang,wei
     * @Date: 2018-11-11
     * @Time: 23:13
     * @param fileNames 文件列表，已分号分隔，或者单个文件
     * @param showId
     * @param newsId
     * @return:
     * @Description:
     *
     */
    boolean storageFanFile(String fileNames, Integer showId, Integer newsId);

    /**
     * 通过ids删除文件
     * @Author: wang,wei
     * @Date: 2018-11-11
     * @Time: 23:20
     * @param ids
     * @return:
     * @Description:
     *
     */
    boolean deleteFanFilesByIds(String ids);
}
