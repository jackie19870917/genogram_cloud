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
     * 保存联谊会文件
     * @Author: wang,wei
     * @Date: 2018-11-11
     * @Time: 23:13
     * @param fileName 文件列表
     * @param filePath 文件路径
     * @param showId
     * @param newsId
     * @return:
     * @Description:
     *
     */
    boolean storageFanFile(String fileName, String filePath, Integer newsId, Integer showId);


    /**
     * 保存联谊会文件
     * @Author: wang,wei
     * @Date: 2018-11-11
     * @Time: 23:13
     * @param fileName 文件列表
     * @param filePath 文件路径
     * @param showId
     * @param newsId
     * @return:
     * @Description:
     *
     */
    boolean storageFanVedio(String fileName, String filePath, Integer newsId, Integer showId);

    /**
     * 保存联谊会文件
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
    boolean storageFanFiles(String fileNames, Integer showId, Integer newsId);

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
