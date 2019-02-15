package com.genogram.service.impl;

import com.genogram.entity.ChiNewsUploadFile;
import com.genogram.mapper.ChiNewsUploadFileMapper;
import com.genogram.service.IChiNewsUploadFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级网文章附件上传表(不包括视频文件) 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiNewsUploadFileServiceImpl extends ServiceImpl<ChiNewsUploadFileMapper, ChiNewsUploadFile> implements IChiNewsUploadFileService {

}
