package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-记录家族-家族动态,家族通告文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamilyRecordServiceImpl extends ServiceImpl<FanNewsFamilyRecordMapper, FanNewsFamilyRecord> implements IFanNewsFamilyRecordService {

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 记录家族详情
     * @param id  主键
     * @return
     */
    @Override
    public NewsDetailVo getFamilyRecord(Integer id) {
        //根据Id查出记录家族详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);

        if(fanNewsFamilyRecord==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamilyRecord.getShowId());
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(null);
        AllUserLogin updateUser = allUserLoginService.selectById(null);

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo=new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamilyRecord,newsDetailVo);
        //存储图片list集合
        newsDetailVo.setFanNewsUploadFileList(files);
        //存储作者名称时间
        newsDetailVo.setUpdateTimeLong(fanNewsFamilyRecord.getUpdateTime().getTime());
        newsDetailVo.setCreateTimeLong(fanNewsFamilyRecord.getCreateTime().getTime());
        newsDetailVo.setCreateUserName(null);
        newsDetailVo.setCreateUserName(null);
        return newsDetailVo;
    }
}
