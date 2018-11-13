package com.genogram.service.impl;

import com.genogram.entity.ProNewsUploadFile;
import com.genogram.mapper.ProNewsUploadFileMapper;
import com.genogram.service.IProNewsUploadFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级网文章附件上传表(不包括视频文件) 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsUploadFileServiceImpl extends ServiceImpl<ProNewsUploadFileMapper, ProNewsUploadFile> implements IProNewsUploadFileService {

}
