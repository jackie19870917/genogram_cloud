package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.mapper.FanNewsFamilyRecordMapper;
import com.genogram.mapper.FanNewsFamousPersonMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsFamousPersonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamousPersonServiceImpl extends ServiceImpl<FanNewsFamousPersonMapper, FanNewsFamousPerson> implements IFanNewsFamousPersonService {
    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;
    @Autowired
    private FanNewsFamousPersonMapper fanNewsFamousPersonMapper;
    @Override
    public Page<FamilyPersonVo> getFamilyPersionPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyPersonVo> familyPersonVoList=new ArrayList<>();

        Wrapper<FanNewsFamousPerson> entity = new EntityWrapper<FanNewsFamousPerson>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        //分页查询文章主表
        Page<FanNewsFamousPerson> fanNewsFamousPerson =this.selectPage(new Page<FanNewsFamousPerson>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsFamousPerson> list = fanNewsFamousPerson.getRecords();
        if(list.size()==0){
            return null;
        }

        //得到所有文章id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", showId);
        uploadentity.eq("status", status);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileMapper.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyPersonVo familyPersonVo=new FamilyPersonVo();
            familyPersonVo.setId(news.getId());
            familyPersonVo.setShowId(news.getShowId());
            familyPersonVo.setPersonName(news.getPersonName());
            familyPersonVo.setPersonSummary(news.getPersonSummary());
            familyPersonVo.setVisitNum(news.getVisitNum());
            familyPersonVo.setStatus(news.getStatus());
            familyPersonVo.setCreateTime(news.getCreateTime());
            familyPersonVo.setCreateUser(news.getCreateUser());
            familyPersonVo.setUpdateTime(news.getUpdateTime());
            familyPersonVo.setUpdateUser(news.getUpdateUser());


            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId()==data.getNewsId()){
                    fanNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyPersonVo.setFanNewsUploadFileList(fanNewsUploadFile);


            //存储到新的集合中
            familyPersonVoList.add(familyPersonVo);

        });
        //重新设置page对象
        Page<FamilyPersonVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyPersonVoList);
        mapPage.setSize(fanNewsFamousPerson.getSize());
        mapPage.setTotal(fanNewsFamousPerson.getTotal());
        return mapPage;
    }
}
