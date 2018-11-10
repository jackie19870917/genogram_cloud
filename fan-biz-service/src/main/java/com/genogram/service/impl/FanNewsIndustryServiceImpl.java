package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.FanNewsIndustryMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsIndustryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 联谊会-家族产业 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsIndustryServiceImpl extends ServiceImpl<FanNewsIndustryMapper, FanNewsIndustry> implements IFanNewsIndustryService {

    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;

    @Autowired
    private AllUserLoginMapper allUserLoginMapper;

    /**
     *联谊会家族产业查询
     * @param entity 分页查询产业文章主表查询条件
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     *
     */
    @Override
    public Page<FamilyIndustryVo> getFamilyIndustryPage(Wrapper<FanNewsIndustry> entity, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyIndustryVo> familyIndustryVoList=new ArrayList<>();


        //分页查询产业文章主表
        Page<FanNewsIndustry> fanNewsCultureNews =this.selectPage(new Page<FanNewsIndustry>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsIndustry> list = fanNewsCultureNews.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if(list.size()==0){
            return null;
        }

        //得到所有产业文章id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", list.get(0).getShowId());
        //  1 表示图片为显示状态
        uploadentity.eq("status", 1);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileMapper.selectList(uploadentity);

        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyIndustryVo familyIndustryVo=new FamilyIndustryVo();
            //调用方法封装集合
            BeanUtils.copyProperties(news,familyIndustryVo);
            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    fanNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyIndustryVo.setFanNewsUploadFileList(fanNewsUploadFile);


            //存储到新的集合中
            familyIndustryVoList.add(familyIndustryVo);
        });

        //重新设置page对象
        Page<FamilyIndustryVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyIndustryVoList);
        mapPage.setSize(fanNewsCultureNews.getSize());
        mapPage.setTotal(fanNewsCultureNews.getTotal());
        return mapPage;
    }

    /**
     *联谊会家族产业各个产业的详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public NewsDetailVo getFamilyIndustryDetail(Integer id) {
        //根据Id查出产业详情
        FanNewsIndustry fanNewsIndustry = this.selectById(id);

        if(fanNewsIndustry==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsIndustry.getShowId());
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileMapper.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginMapper.selectById(fanNewsIndustry.getCreateUser());
        AllUserLogin updateUser = allUserLoginMapper.selectById(fanNewsIndustry.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetail=new NewsDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsIndustry,newsDetail);
        //存储图片list集合
        newsDetail.setFanNewsUploadFileList(files);
        //存储作者名称
        newsDetail.setCreateUserName(createUser.getRealName());
        newsDetail.setCreateUserName(updateUser.getRealName());
        return newsDetail;
    }

    /**
     *联谊会家族产业后台新增
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public boolean addNews(FanNewsIndustry fanNewsIndustry, String urls) {
        //生成时间
        Timestamp format = DateUtil.format(new Date());
        if(fanNewsIndustry.getId()==null){
            //存入创建时间
            fanNewsIndustry.setCreateTime(format);
        }else{
            //存入修改时间
            fanNewsIndustry.setUpdateTime(format);
        }
/*        //存储图片
        if(insert){
            iFanNewsUploadFileService.storagePicture(fanNewsCultureNews.getId(),fanNewsCultureNews.getShowId(),pictures);
        }*/
        return this.insert(fanNewsIndustry);
    }
}
