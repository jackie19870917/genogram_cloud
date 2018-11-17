package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsUploadFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联谊网附件上传表(不包括视频文件) 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsUploadFileServiceImpl extends ServiceImpl<FanNewsUploadFileMapper, FanNewsUploadFile> implements IFanNewsUploadFileService {

}
