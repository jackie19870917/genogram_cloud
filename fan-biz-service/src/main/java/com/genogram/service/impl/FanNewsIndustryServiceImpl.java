package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.mapper.FanNewsIndustryMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * 联谊会-家族产业 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsIndustryServiceImpl extends ServiceImpl<FanNewsIndustryMapper, FanNewsIndustry> implements IFanNewsIndustryService {

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

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
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

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

            //转换时间为long
            familyIndustryVo.setCreateTimeLong(news.getCreateTime().getTime());
            familyIndustryVo.setUpdateTimeLong(news.getUpdateTime().getTime());

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
    public IndustryDetailVo getFamilyIndustryDetail(Integer id) {
        //根据Id查出产业详情
        FanNewsIndustry fanNewsIndustry = this.selectById(id);

        if(fanNewsIndustry==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsIndustry.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsIndustry.getCreateUser());
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsIndustry.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        IndustryDetailVo industryDetailVo=new IndustryDetailVo();
        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsIndustry,industryDetailVo);
        //存储图片list集合
        industryDetailVo.setFanNewsUploadFileList(files);
        //存储作者名称时间
        industryDetailVo.setUpdateTimeLong(fanNewsIndustry.getUpdateTime().getTime());
        industryDetailVo.setCreateTimeLong(fanNewsIndustry.getCreateTime().getTime());
        industryDetailVo.setCreateUserName(null);
        industryDetailVo.setCreateUserName(null);
        return industryDetailVo;
    }

    /**
     *联谊会家族产业后台新增 修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public boolean addOrUpdateIndustry(FanNewsIndustry fanNewsIndustry, String fileName,String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(fanNewsIndustry.getId()==null){
            //查看数 默认为0
            fanNewsIndustry.setVisitNum(0);
            //存入创建时间
            fanNewsIndustry.setCreateTime(format);
            fanNewsIndustry.setCreateUser(null);
            //插入修改时间
            fanNewsIndustry.setUpdateTime(format);
            fanNewsIndustry.setUpdateUser(null);
        }else{
            //存入修改时间
            fanNewsIndustry.setUpdateTime(format);
            fanNewsIndustry.setUpdateUser(null);
        }
        boolean result = this.insertOrUpdate(fanNewsIndustry);
        //存储图片
        if(result && StringsUtils.isNotEmpty(filePath)){
            uploadFileService.storageFanFile(fileName,filePath,fanNewsIndustry.getId(),fanNewsIndustry.getShowId());
        }
        return result;
    }

    /**
     *联谊会家族产业后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:22
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteIndustryById(Integer id, int status) {
        FanNewsIndustry fanNewsIndustry = this.selectById(id);
        fanNewsIndustry.setStatus(status);
        fanNewsIndustry.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人 待写
        boolean result = this.updateAllColumnById(fanNewsIndustry);
        return result;
    }

    /**
     *联谊会家族产业前台增加查看数
     *@Author: yuzhou
     *@Date: 2018-11-12
     *@Time: 13:56
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsIndustry fanNewsIndustry = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsIndustry.getVisitNum()+1;
        fanNewsIndustry.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsIndustry);
        if(visitNum >200 || visitNum==200){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsIndustry.getShowId());
            fanSysRecommend.setNewsId(fanNewsIndustry.getId());
            fanSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
}
