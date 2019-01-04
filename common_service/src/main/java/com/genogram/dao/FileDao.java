package com.genogram.dao;

import com.genogram.entity.FileModel;

import java.io.InputStream;
import java.util.List;

/**
 * Created by chicheng on 2017/12/27.
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
     * @param pathId
     * @return FileModel
     * @Description: 删除缓存map中的数据, 并非真正的删除文件
     */
    FileModel removeFromMap(String pathId);

    /**
     * @param fileModel
     * @return int
     * @Description: 回滚remove
     */
    int rollbackFromMap(FileModel fileModel);

    /**
     * @param fileModel
     * @return int
     * @Description: 删除该filemodel所有先关数据
     */
    int delete(FileModel fileModel);

    List<String> getImageFilesOfPPT(String rootPath);
}
