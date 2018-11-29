package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProFanNewsFamilyRecordServiceImpl extends ServiceImpl<FanNewsFamilyRecordMapper, FanNewsFamilyRecord> implements IProFanNewsFamilyRecordService {

    @Autowired
    private IProFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IProSysRecommendService proSysRecommendService;
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

    /**
     *联谊会记录家族前台增加查看数
     *@Author: yuzhou
     *@Date: 2018-11-12
     *@Time: 13:49
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsFamilyRecord fanNewsFamilyRecord = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsFamilyRecord.getVisitNum()+1;
        fanNewsFamilyRecord.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsFamilyRecord);
        int a = 200;
        if(visitNum >a || visitNum==a){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsFamilyRecord.getShowId());
            fanSysRecommend.setNewsId(fanNewsFamilyRecord.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
}
