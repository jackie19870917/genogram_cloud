package com.genogram.service.impl;

import com.genogram.dao.FileDao;
import com.genogram.entity.FileModel;
import com.genogram.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chicheng on 2017/12/27.
 */
@Component
public class FileServiceImpl implements FileService {


    @Autowired
    private FileDao fileDao;

    @Override
    public FileModel findFileModelByHashCode(String hashCode) {
        return fileDao.findByHashCode(hashCode);
    }

    @Override
    public List<String> findAllKeys() {
        return this.fileDao.findAllKeys();
    }

    @Override
    public List<String> getImageFilesOfPPT(String pathId) {
        return this.fileDao.getImageFilesOfPPT(pathId);
    }


}
