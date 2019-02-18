package com.genogram.dao;


import com.genogram.model.FileModel;

import java.io.InputStream;
import java.util.List;

/**
 * @author chicheng
 * @date 2017/12/27
 */
public interface FileDao {
    /**
     * 查询所有的key
     *
     * @return List
     */
    List<String> findAllKeys();

    /**
     * 查询key对应的FileModel
     *
     * @param hashCode
     * @return FileModel
     */
    FileModel findByHashCode(String hashCode);

    /**
     * 保存文件
     *
     * @param in
     * @param fileModel
     */
    void saveFile(InputStream in, FileModel fileModel);

    /**
     * 删除缓存map中的数据, 并非真正的删除文件
     *
     * @param pathId
     * @return
     */
    FileModel removeFromMap(String pathId);

    /**
     * 回滚remove
     *
     * @param fileModel
     * @return
     */
    int rollbackFromMap(FileModel fileModel);

    /**
     * 删除该filemodel所有先关数据
     *
     * @param fileModel
     * @return
     */
    int delete(FileModel fileModel);

    /**
     * 集合
     *
     * @param rootPath
     * @return
     */
    List<String> getImageFilesOfPPT(String rootPath);
}
